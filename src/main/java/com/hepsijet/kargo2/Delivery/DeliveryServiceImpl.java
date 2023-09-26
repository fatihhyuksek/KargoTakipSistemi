package com.hepsijet.kargo2.Delivery;

import com.hepsijet.kargo2.Company.CompanyService;
import com.hepsijet.kargo2.Customer.CustomerService;
import com.hepsijet.kargo2.Delivery.Models.Domain.DeliveryEntity;
import com.hepsijet.kargo2.Employee.EmployeeService;
import com.hepsijet.kargo2.JWT.JwtService;
import com.hepsijet.kargo2.Kargo2Application;
import com.hepsijet.kargo2.Transaction.DeliveryTransactionService;
import com.hepsijet.kargo2.Xdock.XdockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    public String [] Status = {"Order Created","Collecting","Transfering","Dispatching","Delivered","Undelivered"};

    @Autowired
    private DeliveryRepo deliveryRepo;
    private final JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(Kargo2Application.class);
    private final DeliveryTransactionService transactionService;

    private final CustomerService customerService;

    private final XdockService xdockService;

    private final EmployeeService employeeService;

    private final CompanyService companyService;

    public DeliveryServiceImpl(JwtService jwtService,  DeliveryTransactionService transactionService, CustomerService customerService, XdockService xdockService, EmployeeService employeeService, CompanyService companyService) {
        this.jwtService = jwtService;

        this.transactionService = transactionService;
        this.customerService = customerService;
        this.xdockService = xdockService;
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    @Override
    public ResponseEntity<?> addOrder(DeliveryEntity deliveryEntity) {
        if (companyService.getCompanyName(employeeService.checkCompanyId()) != null){
            String company_name=companyService.getCompanyName(employeeService.checkCompanyId());
            int number = generateBarcode();
            deliveryEntity.setBarcode((long) number);
            String status=Status[0];
            deliveryEntity.setStatus(status);
            deliveryEntity.setSender_company(company_name);
            customerService.getSetCustomerFeatures(deliveryEntity);
            //TRANSACTİON
            transactionService.copyDeliveryEntity(deliveryEntity);
            return ResponseEntity.ok(deliveryRepo.save(deliveryEntity));
        }
        else if ( companyService.getCompanyName(employeeService.checkCompanyId()) == null){
            return ResponseEntity.ok("Sisteme kayıtlı firma yok.");
        }
        return null;

    }
    public static int generateBarcode() {
        Random random = new Random();
        int generatedBarcode = random.nextInt(90000000) + 10000000;
        return generatedBarcode;
    }

    public List<DeliveryEntity> getAllOrder(){
        return deliveryRepo.findAll();
    }

    @Override
    public String updateStatusCollecting(Long barcode, String status) {

        DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);
        String sender_company=deliveryEntity.getSender_company();
        if (deliveryEntity.getStatus().equals(Status[0])
                && employeeService.checkWorkingXdock().equals(companyService.getCompanyTransferCenter(sender_company))){
            status = Status[1];
            deliveryEntity.setStatus(status);
            deliveryRepo.updateStatusByBarcode(barcode, status);
            //Transaction
            transactionService.copyDeliveryEntity(deliveryEntity);
            //Transaction exit.
            return deliveryEntity.getBarcode() + " numaralı teslimat zimmete alındı.";
        }
        else if (!employeeService.checkWorkingXdock().equals(companyService.getCompanyTransferCenter(sender_company))){
            return "Çalıştığınız yere atanmış böyle bir kargo bulunmamaktadır.";
        }
        return "Once siparis olustrun ya da siparis varsa zaten zimmete alınmış olabilir";
    }
    @Override
    public String deliveryInTransferCenter(Long barcode) {
        DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);
        if (deliveryEntity.getStatus().equals(Status[1])) {
            String district = deliveryEntity.getCustomer_district();
            String transfer_center = xdockService.getXdockTransferCenterWithDistrict(district);
            deliveryEntity.setCurrent_xdock(transfer_center);
            deliveryRepo.updateCurrentXdockByBarcode(transfer_center, barcode);
            //Transaction
            transactionService.copyDeliveryEntity(deliveryEntity);

            return barcode + " numaralı teslimat transfer merkezinde ";
        }
        else {
            return barcode + " Numaralı teslimata önce xdock noktası atayın";
        }
    }

    @Override
    public String assingXdock(Long barcode) {
        DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);
        if (deliveryEntity.getStatus().equals(Status[1])) {
            String district = deliveryEntity.getCustomer_district();
            String xdock_number = xdockService.getXdockWithDistrict(district);
            deliveryEntity.setReceiver_xdock(xdock_number);
            deliveryRepo.assingXdockByBarcode(xdock_number, barcode, district);
            //Transaction
            transactionService.copyDeliveryEntity(deliveryEntity);

            return deliveryEntity.getBarcode() + " numaralı teslimata xdock noktası atandı.";

        }
        else {
            return deliveryEntity.getBarcode() + " numaralı teslimatı önce collecting aşamasına götürün";
        }

    }
    @Override
    public String updateStatusTransfering(Long barcode, String status) {
       DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);

        if (deliveryEntity.getReceiver_xdock() == null) {
            return "once xdock numarası ata";
        }
            status = Status[2]; //Transfering
            deliveryEntity.setStatus(status);
            deliveryRepo.updateStatusByBarcode(barcode, status);
            //Transaction
            transactionService.copyDeliveryEntity(deliveryEntity);
            //Transaction exit.

            return barcode + " numaralı sipariş transferinge alındı";



    }

    public boolean isDeliveryAtTransferCenter(Long barcode) {
        DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);
        if (deliveryEntity.getCurrent_xdock() == null) {
            return false;
        } else {
            return true;
        }
    }
    @Override
    public String deliveryInXdock(Long barcode) {
        DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);
        if (deliveryEntity.getReceiver_xdock() != null
                && isDeliveryAtTransferCenter(barcode)) {
            String district = deliveryEntity.getCustomer_district();
            String xdock_number = xdockService.getXdockWithDistrict(district);
            deliveryEntity.setCurrent_xdock(xdock_number);
            deliveryRepo.updateCurrentXdockByBarcode(xdock_number, barcode);
            //Transaction
            transactionService.copyDeliveryEntity(deliveryEntity);
            return barcode + " numaralı teslimat xdock noktasında. ";
        }
        else {
            return barcode + " Numaralı teslimatı önce transfer merkezine yollayın.";
        }
    }


    public boolean isDeliveryXdock(Long barcode) {
        DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);
        if (deliveryEntity.getCurrent_xdock() == null && !isDeliveryAtTransferCenter(barcode)) {
            return false;
        } else {
            return true;
        }

    }
    @Override
    public String assingEmployee(Long barcode) {
        if (isDeliveryXdock(barcode)) {
            DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);
            String xdock_number = deliveryEntity.getReceiver_xdock();
            Long employee_id = employeeService.getCourierWithXdockNumber(xdock_number);
            deliveryEntity.setEmployee_id(employee_id);
            deliveryRepo.setEmployeeId(employee_id, barcode);

            //Transaction
            transactionService.copyDeliveryEntity(deliveryEntity);

            return barcode + " numaralı teslimat " + deliveryEntity.getEmployee_id() + " id li kuryeye atanmıştır.";
        }
        else {
            return barcode + " numaralı teslimatı  önce xdock noktasına ulaştırın.";
        }

    }
    @Override
    public String updateStatusDispatching(Long barcode, String status) {
        DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);
        if ( employeeService.checkWorkingXdock().equals(deliveryEntity.getCurrent_xdock())) {
            status=Status[3]; //Dispatching
            deliveryEntity.setStatus(status);
            deliveryEntity.setCurrent_xdock(null);
            deliveryRepo.updateStatusByBarcode(barcode, status);
            //Transaction
            transactionService.copyDeliveryEntity(deliveryEntity);
            return barcode + " numaralı sipariş dispatchinge alındı";

        }
       else if (!employeeService.checkWorkingXdock().equals(deliveryEntity.getCurrent_xdock())) {
            return "Bu sipariş size atamamış bu sipariş "+deliveryEntity.getEmployee_id()+" id li kuryede. ";
        } else if (deliveryEntity.getEmployee_id() == null){
            return "once teslimatı kuryeye ata";

        }
        return null;
    }

    @Override
    public String updateStatusDeliveredOrUndelivered(Long barcode, boolean isOrderDelivered, String status) {
        DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);
        if (deliveryEntity.getStatus().equals(Status[3])
                && isOrderDelivered == true) {
            status = Status[4]; //Delivered
            deliveryEntity.setStatus(status);
            deliveryRepo.updateStatusByBarcode(barcode, status);
            //Transaction
            transactionService.copyDeliveryEntity(deliveryEntity);
            return deliveryEntity.getBarcode() + " Numaralı teslimat teslim edildi.";
        } else if (deliveryEntity.getStatus().equals(Status[3]) &&
                isOrderDelivered == false) {
            status = Status[5];//Undelivered
            deliveryEntity.setStatus(status);
            deliveryRepo.updateStatusByBarcode(barcode, status);
            transactionService.copyDeliveryEntity(deliveryEntity);
            return deliveryEntity.getBarcode() + " Numaralı teslimat teslim edilemedi";
        }
        else {
            return deliveryEntity.getBarcode() + " Numaralı teslimatı önce dispatching yapın";
        }
    }

    public List<String> getDeliveryByEmployeeId(Long employeeId){
        return deliveryRepo.getDeliveryByEmployeeId(employeeId);
    }

    public ResponseEntity<?> getDeliveryWithOperationWorkingXdock() {
        String working_xdock = employeeService.checkWorkingXdock();
        return ResponseEntity.ok(deliveryRepo.getDeliveryWithOperationWorkingXdock(working_xdock));
    }
    @Override
    public List<DeliveryEntity> getAssignedCourierDelivery(Long id) {
        id=employeeService.getEmployeeId();
        return deliveryRepo.getAssignedCourierDelivery(id);
    }




}

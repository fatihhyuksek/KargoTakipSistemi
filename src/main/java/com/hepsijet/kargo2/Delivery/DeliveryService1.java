package com.hepsijet.kargo2.Delivery;

import com.hepsijet.kargo2.Customer.CustomerRepo;
import com.hepsijet.kargo2.Delivery.Models.Domain.DeliveryEntity;
import com.hepsijet.kargo2.Employee.Repositorys.EmployeeRepo;
import com.hepsijet.kargo2.Transaction.DeliveryTransactionEntity;
import com.hepsijet.kargo2.Transaction.DeliveryTransactionRepo;
import com.hepsijet.kargo2.Xdock.XdockRepo;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DeliveryService1 {

    public String [] Status = {"Order Created","Collecting","Transfering","Dispatching","Delivered","Undelivered"};

    private final DeliveryRepo deliveryRepo;
    private final CustomerRepo customerRepo;
    private final XdockRepo xdockRepo;

    private final EmployeeRepo employeeRepo;

    private final DeliveryTransactionRepo deliveryTransactionRepo;


    public DeliveryService1(DeliveryRepo deliveryRepo, CustomerRepo customerRepo, XdockRepo xdockRepo, EmployeeRepo employeeRepo, DeliveryTransactionRepo deliveryTransactionRepo) {
        this.deliveryRepo = deliveryRepo;
        this.customerRepo = customerRepo;
        this.xdockRepo = xdockRepo;
        this.employeeRepo = employeeRepo;
        this.deliveryTransactionRepo = deliveryTransactionRepo;
    }




    public static int generateBarcode() {
        Random random = new Random();
        int generatedBarcode = random.nextInt(90000000) + 10000000;
        return generatedBarcode;
    }


    public String updateStatusCollecting(Long barcode, String status) {
        DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);

        if (deliveryEntity.getStatus().equals(Status[0])){
            status = Status[1];
            deliveryEntity.setStatus(status);
            deliveryRepo.updateStatusByBarcode(barcode, status);

            //Transaction
            DeliveryTransactionEntity transactionEntity =new DeliveryTransactionEntity();
            transactionEntity.setBarcode(deliveryEntity.getBarcode());
            transactionEntity.setCustomer_tc(deliveryEntity.getCustomer_tc());
            transactionEntity.setStatus(deliveryEntity.getStatus());
            transactionEntity.setSender_company(deliveryEntity.getSender_company());
            transactionEntity.setCustomer_name(deliveryEntity.getCustomer_name());
            transactionEntity.setCustomer_surname(deliveryEntity.getCustomer_surname());
            transactionEntity.setCustomer_address(deliveryEntity.getCustomer_address());
            transactionEntity.setCustomer_city(deliveryEntity.getCustomer_city());
            transactionEntity.setCustomer_district(deliveryEntity.getCustomer_district());
            deliveryTransactionRepo.save(transactionEntity);

            //Transaction exit.

            return deliveryEntity.getBarcode() + " numaralı teslimat zimmete alındı.";
        }

        return "Once siparis olustrun ya da siparis varsa zaten zimmete alınmış olabilir";
    }

    public String assingXdock(Long barcode) {
        DeliveryEntity deliveryEntity = new DeliveryEntity();
        deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);
        if (deliveryEntity.getStatus().equals(Status[1])) {
            String district = deliveryEntity.getCustomer_district();
            String xdock_number = xdockRepo.getXdockWithDistrict(district);
            deliveryRepo.assingXdockByBarcode(xdock_number, barcode, district);

            //Transaction
            DeliveryTransactionEntity transactionEntity=new DeliveryTransactionEntity();
            transactionEntity.setBarcode(deliveryEntity.getBarcode());
            transactionEntity.setCustomer_tc(deliveryEntity.getCustomer_tc());
            transactionEntity.setStatus(deliveryEntity.getStatus());
            transactionEntity.setSender_company(deliveryEntity.getSender_company());
            transactionEntity.setCustomer_name(deliveryEntity.getCustomer_name());
            transactionEntity.setCustomer_surname(deliveryEntity.getCustomer_surname());
            transactionEntity.setCustomer_address(deliveryEntity.getCustomer_address());
            transactionEntity.setCustomer_city(deliveryEntity.getCustomer_city());
            transactionEntity.setCustomer_district(deliveryEntity.getCustomer_district());
            transactionEntity.setReceiver_xdock(String.valueOf(xdock_number));
            deliveryTransactionRepo.save(transactionEntity);


            return deliveryEntity.getBarcode() + " numaralı teslimata xdock noktası atandı.";


        } else {
            return deliveryEntity.getBarcode() + " numaralı teslimatı önce collecting aşamasına götürün";
        }


    }


    public String updateStatusTransfering(Long barcode, String status) {
        DeliveryEntity deliveryEntity = new DeliveryEntity();
        deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);
        if (deliveryEntity.getReceiver_xdock() == null) {
            return "once xdock numarası ata";
        } else {
            status = Status[2]; //Transfering
            deliveryEntity.setStatus(status);
            deliveryRepo.updateStatusByBarcode(barcode, status);

            //Transaction

            DeliveryTransactionEntity transactionEntity=new DeliveryTransactionEntity();
            transactionEntity.setBarcode(deliveryEntity.getBarcode());
            transactionEntity.setCustomer_tc(deliveryEntity.getCustomer_tc());
            transactionEntity.setStatus(deliveryEntity.getStatus());
            transactionEntity.setSender_company(deliveryEntity.getSender_company());
            transactionEntity.setCustomer_name(deliveryEntity.getCustomer_name());
            transactionEntity.setCustomer_surname(deliveryEntity.getCustomer_surname());
            transactionEntity.setCustomer_address(deliveryEntity.getCustomer_address());
            transactionEntity.setCustomer_city(deliveryEntity.getCustomer_city());
            transactionEntity.setCustomer_district(deliveryEntity.getCustomer_district());
            transactionEntity.setReceiver_xdock(deliveryEntity.getReceiver_xdock());
            deliveryTransactionRepo.save(transactionEntity);

            //Transaction exit.


            return barcode + " numaralı sipariş transferinge alındı";

        }
    }

    public String deliveryInTransferCenter(Long barcode) {
        DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);
        if (deliveryEntity.getReceiver_xdock() != null && deliveryEntity.getStatus().equals(Status[2])) {
            String district = deliveryEntity.getCustomer_district();
            String transfer_center = String.valueOf(xdockRepo.getXdockTransferCenterWithDistrict(district));
            deliveryEntity.setCurrent_xdock(transfer_center);
            deliveryRepo.updateCurrentXdockByBarcode(transfer_center, barcode);

            //Transaction


            DeliveryTransactionEntity transactionEntity=new DeliveryTransactionEntity();
            transactionEntity.setBarcode(deliveryEntity.getBarcode());
            transactionEntity.setCustomer_tc(deliveryEntity.getCustomer_tc());
            transactionEntity.setStatus(deliveryEntity.getStatus());
            transactionEntity.setSender_company(deliveryEntity.getSender_company());
            transactionEntity.setCustomer_name(deliveryEntity.getCustomer_name());
            transactionEntity.setCustomer_surname(deliveryEntity.getCustomer_surname());
            transactionEntity.setCustomer_address(deliveryEntity.getCustomer_address());
            transactionEntity.setCustomer_city(deliveryEntity.getCustomer_city());
            transactionEntity.setCustomer_district(deliveryEntity.getCustomer_district());
            transactionEntity.setReceiver_xdock(deliveryEntity.getReceiver_xdock());
            transactionEntity.setCurrent_xdock(deliveryEntity.getCurrent_xdock());
            deliveryTransactionRepo.save(transactionEntity);

            //Transaction exit.


            return barcode + " numaralı teslimat transfer merkezinde ";
        } else {
            return barcode + " Numaralı teslimata önce xdock noktası atayın";
        }
    }

    public boolean isDeliveryAtTransferCenter(Long barcode) {
        DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);
        if (deliveryEntity.getCurrent_xdock() == null) {
            return false;
        } else {
            return true;
        }

    }

    public String deliveryInXdock(Long barcode) {
        DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);
        if (deliveryEntity.getReceiver_xdock() != null && isDeliveryAtTransferCenter(barcode)) {
            String district = deliveryEntity.getCustomer_district();
            String xdock_number = xdockRepo.getXdock_numberByBarcode(district);
            deliveryEntity.setCurrent_xdock(xdock_number);
            deliveryRepo.updateCurrentXdockByBarcode(xdock_number, barcode);

            //Transaction


            DeliveryTransactionEntity transactionEntity=new DeliveryTransactionEntity();
            transactionEntity.setBarcode(deliveryEntity.getBarcode());
            transactionEntity.setCustomer_tc(deliveryEntity.getCustomer_tc());
            transactionEntity.setStatus(deliveryEntity.getStatus());
            transactionEntity.setSender_company(deliveryEntity.getSender_company());
            transactionEntity.setCustomer_name(deliveryEntity.getCustomer_name());
            transactionEntity.setCustomer_surname(deliveryEntity.getCustomer_surname());
            transactionEntity.setCustomer_address(deliveryEntity.getCustomer_address());
            transactionEntity.setCustomer_city(deliveryEntity.getCustomer_city());
            transactionEntity.setCustomer_district(deliveryEntity.getCustomer_district());
            transactionEntity.setReceiver_xdock(deliveryEntity.getReceiver_xdock());
            transactionEntity.setCurrent_xdock(deliveryEntity.getCurrent_xdock());
            deliveryTransactionRepo.save(transactionEntity);

            //Transaction exit.


            return barcode + " numaralı teslimat xdock noktasında. ";
        } else {
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

    /*public String assingEmployee(Long barcode) {
        if (isDeliveryXdock(barcode)) {
            DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);
            Long xdock_number = Long.valueOf(deliveryEntity.getReceiver_xdock());
            Long employee_id = employeeRepo.getEmployeeİd(xdock_number);
            deliveryEntity.setEmployee_id(employee_id);
            deliveryRepo.setEmployeeId(employee_id, barcode);

            //Transaction


            DeliveryTransactionEntity transactionEntity=new DeliveryTransactionEntity();
            transactionEntity.setBarcode(deliveryEntity.getBarcode());
            transactionEntity.setCustomer_tc(deliveryEntity.getCustomer_tc());
            transactionEntity.setStatus(deliveryEntity.getStatus());
            transactionEntity.setSender_company(deliveryEntity.getSender_company());
            transactionEntity.setCustomer_name(deliveryEntity.getCustomer_name());
            transactionEntity.setCustomer_surname(deliveryEntity.getCustomer_surname());
            transactionEntity.setCustomer_address(deliveryEntity.getCustomer_address());
            transactionEntity.setCustomer_city(deliveryEntity.getCustomer_city());
            transactionEntity.setCustomer_district(deliveryEntity.getCustomer_district());
            transactionEntity.setReceiver_xdock(deliveryEntity.getReceiver_xdock());
            transactionEntity.setCurrent_xdock(deliveryEntity.getCurrent_xdock());
            transactionEntity.setEmployee_id(deliveryEntity.getEmployee_id());
            deliveryTransactionRepo.save(transactionEntity);

            //Transaction exit.


            return barcode + " numaralı teslimat " + deliveryEntity.getEmployee_id() + " id li kuryeye atanmıştır.";
        } else {
            return barcode + " numaralı teslimatı  önce xdock noktasına ulaştırın.";
        }


    }*/

    public String updateStatusDispatching(Long barcode, String status) {
        DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);
        if (deliveryEntity.getEmployee_id() == null) {
            return "once teslimatı kuryeye ata";
        } else {
            status=Status[3]; //Dispatching
            deliveryEntity.setStatus(status);
            deliveryEntity.setCurrent_xdock(null);
            deliveryRepo.updateStatusByBarcode(barcode, status);

            //Transaction


            DeliveryTransactionEntity transactionEntity=new DeliveryTransactionEntity();
            transactionEntity.setBarcode(deliveryEntity.getBarcode());
            transactionEntity.setCustomer_tc(deliveryEntity.getCustomer_tc());
            transactionEntity.setStatus(deliveryEntity.getStatus());
            transactionEntity.setSender_company(deliveryEntity.getSender_company());
            transactionEntity.setCustomer_name(deliveryEntity.getCustomer_name());
            transactionEntity.setCustomer_surname(deliveryEntity.getCustomer_surname());
            transactionEntity.setCustomer_address(deliveryEntity.getCustomer_address());
            transactionEntity.setCustomer_city(deliveryEntity.getCustomer_city());
            transactionEntity.setCustomer_district(deliveryEntity.getCustomer_district());
            transactionEntity.setReceiver_xdock(deliveryEntity.getReceiver_xdock());
            transactionEntity.setCurrent_xdock(deliveryEntity.getCurrent_xdock());
            transactionEntity.setEmployee_id(deliveryEntity.getEmployee_id());
            deliveryTransactionRepo.save(transactionEntity);

            //Transaction exit.


            return barcode + " numaralı sipariş dispatchinge alındı";

        }
    }


    public String updateStatusDeliveredOrUndelivered(Long barcode, boolean isOrderDelivered, String status) {
        DeliveryEntity deliveryEntity = deliveryRepo.getDeliveryWithBarcode(barcode);

        if (deliveryEntity.getStatus().equals(Status[3]) && isOrderDelivered == true) {
            status = Status[4]; //Delivered
            deliveryEntity.setStatus(status);
            deliveryRepo.updateStatusByBarcode(barcode, status);

            //Transaction


            DeliveryTransactionEntity transactionEntity=new DeliveryTransactionEntity();
            transactionEntity.setBarcode(deliveryEntity.getBarcode());
            transactionEntity.setCustomer_tc(deliveryEntity.getCustomer_tc());
            transactionEntity.setStatus(deliveryEntity.getStatus());
            transactionEntity.setSender_company(deliveryEntity.getSender_company());
            transactionEntity.setCustomer_name(deliveryEntity.getCustomer_name());
            transactionEntity.setCustomer_surname(deliveryEntity.getCustomer_surname());
            transactionEntity.setCustomer_address(deliveryEntity.getCustomer_address());
            transactionEntity.setCustomer_city(deliveryEntity.getCustomer_city());
            transactionEntity.setCustomer_district(deliveryEntity.getCustomer_district());
            transactionEntity.setReceiver_xdock(deliveryEntity.getReceiver_xdock());
            transactionEntity.setCurrent_xdock(deliveryEntity.getCurrent_xdock());
            transactionEntity.setEmployee_id(deliveryEntity.getEmployee_id());
            deliveryTransactionRepo.save(transactionEntity);

            //Transaction exit.



            return deliveryEntity.getBarcode() + " Numaralı teslimat teslim edildi.";

        } else if (deliveryEntity.getStatus().equals(Status[3]) && isOrderDelivered == false) {
            status = Status[5];//Undelivered
            deliveryEntity.setStatus(status);
            deliveryRepo.updateStatusByBarcode(barcode, status);


            //Transaction


            DeliveryTransactionEntity transactionEntity=new DeliveryTransactionEntity();
            transactionEntity.setBarcode(deliveryEntity.getBarcode());
            transactionEntity.setCustomer_tc(deliveryEntity.getCustomer_tc());
            transactionEntity.setStatus(deliveryEntity.getStatus());
            transactionEntity.setSender_company(deliveryEntity.getSender_company());
            transactionEntity.setCustomer_name(deliveryEntity.getCustomer_name());
            transactionEntity.setCustomer_surname(deliveryEntity.getCustomer_surname());
            transactionEntity.setCustomer_address(deliveryEntity.getCustomer_address());
            transactionEntity.setCustomer_city(deliveryEntity.getCustomer_city());
            transactionEntity.setCustomer_district(deliveryEntity.getCustomer_district());
            transactionEntity.setReceiver_xdock(deliveryEntity.getReceiver_xdock());
            transactionEntity.setCurrent_xdock(deliveryEntity.getCurrent_xdock());
            transactionEntity.setEmployee_id(deliveryEntity.getEmployee_id());
            deliveryTransactionRepo.save(transactionEntity);

            //Transaction exit.


            return deliveryEntity.getBarcode() + " Numaralı teslimat teslim edilemedi";
        } else {
            return deliveryEntity.getBarcode() + " Numaralı teslimatı önce dispatching yapın";
        }

    }
}
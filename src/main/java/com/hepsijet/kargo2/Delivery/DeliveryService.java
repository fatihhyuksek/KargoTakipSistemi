package com.hepsijet.kargo2.Delivery;

import com.hepsijet.kargo2.Delivery.Models.Domain.DeliveryEntity;
import org.springframework.http.ResponseEntity;

import javax.swing.text.html.parser.Entity;
import java.util.List;


public interface DeliveryService {



    ResponseEntity<?> addOrder(DeliveryEntity deliveryEntity);

    public List<DeliveryEntity> getAllOrder();

    String updateStatusCollecting(Long barcode, String status);

    public String assingXdock(Long barcode);

    String updateStatusTransfering(Long barcode, String status);

    String deliveryInTransferCenter(Long barcode);

    String deliveryInXdock(Long barcode);

    String assingEmployee(Long barcode);

    String updateStatusDispatching(Long barcode, String status);

    String updateStatusDeliveredOrUndelivered(Long barcode, boolean isOrderDelivered, String status);

    public List<String> getDeliveryByEmployeeId(Long employeeId);

    public ResponseEntity<?> getDeliveryWithOperationWorkingXdock();


    public List<DeliveryEntity> getAssignedCourierDelivery(Long id);



}

package com.hepsijet.kargo2.Transaction;

import com.hepsijet.kargo2.Delivery.Models.Domain.DeliveryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryTransactionServiceImpl implements DeliveryTransactionService {

    @Autowired
    private DeliveryTransactionRepo deliveryTransactionRepo;
    @Override
    public DeliveryTransactionEntity copyDeliveryEntity(DeliveryEntity deliveryEntity){
        DeliveryTransactionEntity deliveryTransactionEntity =new DeliveryTransactionEntity();
        deliveryTransactionEntity.setBarcode(deliveryEntity.getBarcode());
        deliveryTransactionEntity.setCustomer_tc(deliveryEntity.getCustomer_tc());
        deliveryTransactionEntity.setStatus(deliveryEntity.getStatus());
        deliveryTransactionEntity.setSender_company(deliveryEntity.getSender_company());
        deliveryTransactionEntity.setCustomer_name(deliveryEntity.getCustomer_name());
        deliveryTransactionEntity.setCustomer_surname(deliveryEntity.getCustomer_surname());
        deliveryTransactionEntity.setCustomer_address(deliveryEntity.getCustomer_address());
        deliveryTransactionEntity.setCustomer_city(deliveryEntity.getCustomer_city());
        deliveryTransactionEntity.setCustomer_district(deliveryEntity.getCustomer_district());
        deliveryTransactionEntity.setReceiver_xdock(deliveryEntity.getReceiver_xdock());
        deliveryTransactionEntity.setEmployee_id(deliveryEntity.getEmployee_id());
        deliveryTransactionEntity.setCurrent_xdock(deliveryEntity.getCurrent_xdock());
        deliveryTransactionRepo.save(deliveryTransactionEntity);
        System.out.println(deliveryTransactionEntity.getBarcode() + " " + deliveryTransactionEntity.getReceiver_xdock());

        return deliveryTransactionEntity;
    }


}
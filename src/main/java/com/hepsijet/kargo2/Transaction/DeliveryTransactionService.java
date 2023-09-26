package com.hepsijet.kargo2.Transaction;

import com.hepsijet.kargo2.Delivery.Models.Domain.DeliveryEntity;

public interface DeliveryTransactionService {
    DeliveryTransactionEntity copyDeliveryEntity(DeliveryEntity deliveryEntity);


}

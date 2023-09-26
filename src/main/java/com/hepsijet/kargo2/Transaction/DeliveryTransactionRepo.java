package com.hepsijet.kargo2.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryTransactionRepo extends JpaRepository<DeliveryTransactionEntity,Long> {

    @Query(value ="SELECT * FROM delivery_transaction where barcode = :barcode",nativeQuery = true)
    public DeliveryTransactionDto getDeliveryTransactionByBarcode(Long barcode);



}

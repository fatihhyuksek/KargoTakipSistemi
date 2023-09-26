package com.hepsijet.kargo2.Delivery;

import com.hepsijet.kargo2.Delivery.Models.Domain.DeliveryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliveryRepo extends JpaRepository<DeliveryEntity,Long> {


    @Override
    @Query(value = "select * from delivery",nativeQuery = true)
    List<DeliveryEntity> findAll();

    @Query(value ="SELECT * FROM delivery where barcode = :barcode",nativeQuery = true)
    public DeliveryEntity getDeliveryWithBarcode(Long barcode);
    @Transactional
    @Modifying
    @Query(value =" UPDATE  delivery SET status = :status where barcode = :barcode" ,nativeQuery = true)
    public void updateStatusByBarcode(Long barcode, String status);


    @Transactional
    @Modifying
    @Query(value =" UPDATE delivery SET receiver_xdock = :xdock_number WHERE barcode = :barcode AND " +
            "customer_district = :customer_district",nativeQuery = true)
    public void assingXdockByBarcode (String xdock_number,Long barcode ,String customer_district);

    @Transactional
    @Modifying
    @Query(value =" UPDATE  delivery SET current_xdock = :current_xdock where barcode = :barcode" ,nativeQuery = true)
    public void updateCurrentXdockByBarcode(String current_xdock,Long barcode);


    @Transactional
    @Modifying
    @Query(value =" UPDATE  delivery SET employee_id = :employee_id where barcode = :barcode" ,nativeQuery = true)
    public void setEmployeeId(Long  employee_id,Long barcode );

    @Query(value = "SELECT barcode,customer_name,customer_surname,customer_address FROM delivery WHERE employee_id = :employeeId",nativeQuery = true)
    public List <String> getDeliveryByEmployeeId(Long employeeId);

    @Query(value = "SELECT * FROM delivery where current_xdock = :working_xdock",nativeQuery = true)
    public List<DeliveryEntity> getDeliveryWithOperationWorkingXdock(String working_xdock);

    @Query(value = "select * from delivery where  employee_id = :id ",nativeQuery = true)
    public List<DeliveryEntity> getAssignedCourierDelivery(Long id);

}

package com.hepsijet.kargo2.Customer;

import com.hepsijet.kargo2.Customer.Models.Domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository <CustomerEntity,Long> {


  @Query(value = "SELECT * FROM customers", nativeQuery = true)
  List <CustomerEntity> findAll(CustomerEntity customerEntity);

  @Query(value ="SELECT * FROM customers where customer_tc = :customer_tc",nativeQuery = true)
  public  CustomerEntity getCustomerWithCustomerTc(String customer_tc);

}

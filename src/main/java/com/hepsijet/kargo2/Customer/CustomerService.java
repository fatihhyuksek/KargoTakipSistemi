package com.hepsijet.kargo2.Customer;

import com.hepsijet.kargo2.Customer.Models.Domain.CustomerEntity;
import com.hepsijet.kargo2.Delivery.Models.Domain.DeliveryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    public CustomerEntity addCustomer(CustomerEntity customerEntity) ;

    public List<CustomerEntity> getCustomers(CustomerEntity customerEntity) ;
    public CustomerEntity getCustomerWithCustomerTc(String customer_tc) ;

    public void getSetCustomerFeatures(DeliveryEntity deliveryEntity);





    }
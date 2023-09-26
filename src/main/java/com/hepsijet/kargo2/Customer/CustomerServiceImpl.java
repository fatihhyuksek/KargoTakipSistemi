package com.hepsijet.kargo2.Customer;

import com.hepsijet.kargo2.Customer.Models.Domain.CustomerEntity;
import com.hepsijet.kargo2.Delivery.Models.Domain.DeliveryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class CustomerServiceImpl implements CustomerService{


    @Autowired
    private CustomerRepo customerRepo;

    public CustomerEntity addCustomer(CustomerEntity customerEntity) {
        return customerRepo.save(customerEntity);
    }

    public List<CustomerEntity> getCustomers(CustomerEntity customerEntity) {
        return customerRepo.findAll(customerEntity);
    }

    public CustomerEntity getCustomerWithCustomerTc(String customer_tc) {
        return customerRepo.getCustomerWithCustomerTc(customer_tc);
    }

    @Override
    public void getSetCustomerFeatures(DeliveryEntity deliveryEntity){
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity = customerRepo.getCustomerWithCustomerTc(deliveryEntity.getCustomer_tc());
        deliveryEntity.setCustomer_name(customerEntity.getCustomer_name());
        deliveryEntity.setCustomer_surname(customerEntity.getCustomer_surname());
        deliveryEntity.setCustomer_address(customerEntity.getCustomer_address());
        deliveryEntity.setCustomer_city(customerEntity.getCustomer_city());
        deliveryEntity.setCustomer_district(customerEntity.getCustomer_district());
    }
}

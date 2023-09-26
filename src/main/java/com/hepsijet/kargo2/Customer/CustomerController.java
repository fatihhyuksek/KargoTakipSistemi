package com.hepsijet.kargo2.Customer;

import com.hepsijet.kargo2.Customer.Models.Domain.CustomerEntity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/customer")
@Hidden
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping(value = "/add")
    public CustomerEntity addCustomer(CustomerEntity customerEntity){

        return customerService.addCustomer(customerEntity);
    }
    @GetMapping(value = "/get")
    public List<CustomerEntity> getCustomers(CustomerEntity customerEntity){
        return  customerService.getCustomers(customerEntity);
    }
    @GetMapping(value = "/getTc")
    public CustomerEntity getCustomerWithCustomerTc(String customer_tc){
        return customerService.getCustomerWithCustomerTc(customer_tc);
    }
}

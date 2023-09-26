package com.hepsijet.kargo2.Delivery;

import com.hepsijet.kargo2.Company.CompanyService;
import com.hepsijet.kargo2.Delivery.Models.Domain.DeliveryEntity;
import com.hepsijet.kargo2.Employee.EmployeeService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/delivery")
@SecurityRequirement(name = "bearerAuth")
@Hidden
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final CompanyService companyService;
    private final EmployeeService employeeService;

    public DeliveryController(DeliveryService1 deliveryService, DeliveryService deliveryService1, CompanyService companyService, EmployeeService employeeService) {
        this.deliveryService = deliveryService1;
        this.companyService = companyService;
        this.employeeService = employeeService;
    }


    @GetMapping("/get/allOrder")
    public List<DeliveryEntity> getAllOrder(){
        return deliveryService.getAllOrder();
    }




    @GetMapping(value = "/get/byEmployeeId")
    public List<String> getDeliveryByEmployeeId(Long employeeId){
        return  deliveryService.getDeliveryByEmployeeId(employeeId);
    }




}

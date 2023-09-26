package com.hepsijet.kargo2.demo;

import com.hepsijet.kargo2.Delivery.DeliveryRepo;
import com.hepsijet.kargo2.Delivery.DeliveryService;
import com.hepsijet.kargo2.Delivery.Models.Domain.DeliveryEntity;
import com.hepsijet.kargo2.Employee.EmployeeService;
import com.hepsijet.kargo2.Employee.Models.EmployeeEntity;
import com.hepsijet.kargo2.Employee.Repositorys.EmployeeRepo;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Courier-controller")

public class CourierController {
    private final EmployeeRepo employeeRepo;

    private final DeliveryRepo deliveryRepo;
    private final DeliveryService deliveryService;

    private final EmployeeService employeeService;



    public CourierController(EmployeeRepo employeeRepo, DeliveryRepo deliveryRepo, DeliveryService deliveryService, EmployeeService employeeService) {
        this.employeeRepo = employeeRepo;
        this.deliveryRepo = deliveryRepo;
        this.deliveryService = deliveryService;
        this.employeeService = employeeService;
    }


    @GetMapping("/login")
    @Hidden
    @PreAuthorize("hasRole('COURIER')")
    public ResponseEntity<String> sayHello(){
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.isAuthenticated());
        if  (auth.getAuthorities().toString().equals("[COURIER]")){

            return ResponseEntity.ok("Hello from secured endpoint Courier.");
        }

        System.out.println(auth.getAuthorities().toString());
        return ResponseEntity.ok(String.valueOf(HttpStatus.resolve(403)));

    }
    @Operation(description = "get delivery endpoint for courier ",
            summary = "COURIER rolündeki kullanıcıların üzerine atılan kargoları görüntüler.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200"
                    )

            })
    @GetMapping("/getMyDelivery")
    public List<DeliveryEntity> getMyDelivery(Long id){
        return deliveryService.getAssignedCourierDelivery(id);

    }

    @Operation(description = "Patch delivery endpoint for couirer ",
            summary = "COURIER rolündeki kullanıcıların üzerindeki kargoları dağıtıma çıkardığını belirtmeye yarar.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200"
                    )

            })
    @PatchMapping(value = "/delivery/status/dispatching")
    public String updateStatusDispatching( Long barcode, String status) {
            return deliveryService.updateStatusDispatching(barcode, status);
    }


    @Operation(description = "Patch delivery endpoint for couirer ",
            summary = "COURIER rolündeki kullanıcıların teslimata çıkardığı kargoyu teslim edip etmediğini belirtir.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200"
                    )

            })
    @PatchMapping(value = "/delivery/status/deliveredOrUndelivered")
    public String updateStatusDeliveredOrUndelivered(Long barcode,boolean isOrderDelivered,String status){
            return deliveryService.updateStatusDeliveredOrUndelivered(barcode,isOrderDelivered,status);

    }



    @GetMapping("/get/employee")
    @Hidden
    public Long getEmp(String username, Authentication authentication){
        return employeeRepo.getEmployeeIdByUsername(username);
    }


}

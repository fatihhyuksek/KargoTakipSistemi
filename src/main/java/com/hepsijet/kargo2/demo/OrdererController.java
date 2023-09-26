package com.hepsijet.kargo2.demo;

import com.hepsijet.kargo2.Delivery.DeliveryService;
import com.hepsijet.kargo2.Delivery.Models.Domain.DeliveryEntity;
import com.hepsijet.kargo2.Employee.EmployeeService;
import com.hepsijet.kargo2.JWT.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/Orderer-controller")
@RequiredArgsConstructor()
public class OrdererController {

    private final DeliveryService deliveryService;
    private final AuthenticationService authenticationService;
    private final EmployeeService employeeService;


    @Operation(description = "Post delivery endpoint for orderer ",
            summary = "Orderer rolündeki kullanıcıların sipariş girmelerine yarar.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )

            })
    @PostMapping(value = "/orders/add")
    public ResponseEntity<?> addOrder(@RequestBody DeliveryEntity deliveryEntity) {
        return ResponseEntity.ok(deliveryService.addOrder(deliveryEntity));

    }

}

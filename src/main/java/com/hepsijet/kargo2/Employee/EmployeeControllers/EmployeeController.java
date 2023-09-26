package com.hepsijet.kargo2.Employee.EmployeeControllers;

import com.hepsijet.kargo2.Company.CompanyService;
import com.hepsijet.kargo2.Employee.Models.EmployeeEntity;
import com.hepsijet.kargo2.Employee.EmployeeService;
import com.hepsijet.kargo2.Employee.Models.LoginRequest;
import com.hepsijet.kargo2.JWT.auth.AuthenticationResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final CompanyService companyService;


    @Hidden
    @PostMapping("/add")
    public EmployeeEntity addEmployee(EmployeeEntity employeeEntity) {
        return employeeService.addEmployee(employeeEntity);
    }

    @Hidden
    @GetMapping(value = "/get/user")
    public EmployeeEntity getEmployee(String username, String password) {
        return employeeService.getEmployee(username,password);
    }

    @PostMapping("/login")
    public ResponseEntity <AuthenticationResponse> loginEmployee(@RequestBody LoginRequest request){
        return employeeService.loginEmployee(request);
    }




}

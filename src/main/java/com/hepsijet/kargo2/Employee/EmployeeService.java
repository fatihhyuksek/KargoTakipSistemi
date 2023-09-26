package com.hepsijet.kargo2.Employee;

import com.hepsijet.kargo2.Employee.Models.EmployeeEntity;
import com.hepsijet.kargo2.Employee.Models.LoginRequest;
import com.hepsijet.kargo2.JWT.auth.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

    public EmployeeEntity addEmployee(EmployeeEntity employeeEntity);
    public Long getCourierWithXdockNumber(String xdock_number);

    public EmployeeEntity getEmployee(String username, String password);

    public boolean checkExpired();
    public String checkRole();
    ResponseEntity<AuthenticationResponse> loginEmployee(LoginRequest request);
    public Long checkCompanyId();
    public String checkWorkingXdock();

    public Long getEmployeeId();

        //public String employeeLogin(String username,String password);

   // public List<String> getEmployeeAssignedCargo (String authorizationHeader);

    //public String getUsernameByToken(String token);


    }

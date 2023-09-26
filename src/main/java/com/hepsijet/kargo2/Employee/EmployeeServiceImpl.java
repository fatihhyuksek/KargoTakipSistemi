package com.hepsijet.kargo2.Employee;


import com.hepsijet.kargo2.Employee.Models.LoginRequest;
import com.hepsijet.kargo2.Employee.Repositorys.EmployeeRepo;
import com.hepsijet.kargo2.Delivery.DeliveryRepo;

import com.hepsijet.kargo2.Employee.Models.EmployeeEntity;
import com.hepsijet.kargo2.JWT.JwtService;
import com.hepsijet.kargo2.JWT.auth.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepo employeeRepo;

    private final DeliveryRepo deliveryRepo;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;





    public EmployeeServiceImpl(EmployeeRepo employeeRepo, DeliveryRepo deliveryRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.employeeRepo = employeeRepo;
        this.deliveryRepo = deliveryRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public EmployeeEntity addEmployee(EmployeeEntity employeeEntity) {
        return employeeRepo.save(employeeEntity);
    }

    public Long getCourierWithXdockNumber(String xdock_number) {
        return employeeRepo.getCourier( xdock_number);
    }

    public EmployeeEntity getEmployee(String username, String password) {
        return employeeRepo.getUserByUsernameAndPassword(username, password);
    }

    @Override
    public ResponseEntity<AuthenticationResponse> loginEmployee(LoginRequest request) {
        var user = employeeRepo.findByUsername(request.getUsername()).orElseThrow();
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getEmployee_password())) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            var jwtToken = jwtService.generateToken(user);
            return ResponseEntity.ok(AuthenticationResponse.
                    builder().
                    token(jwtToken).
                    build());

        }
        return null;
    }
    public String checkRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeEntity employeeEntity = (EmployeeEntity) authentication.getPrincipal();
        return employeeEntity.getRole().toString() ;

    }
    public String checkWorkingXdock(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeEntity employeeEntity = (EmployeeEntity) authentication.getPrincipal();
        return employeeEntity.getWorking_xdock();
    }

    public Long checkCompanyId(){
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EmployeeEntity employeeEntity= (EmployeeEntity) auth.getPrincipal();
        Long company_id= Long.valueOf(employeeEntity.getWorking_company_id());
        return company_id;
    }

    public boolean checkExpired(){
        EmployeeEntity employeeEntity = new EmployeeEntity();
        if (employeeEntity.isAccountNonExpired())
        {
            return true;
        }
        return false;
    }
    public Long getEmployeeId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeEntity employeeEntity = (EmployeeEntity) authentication.getPrincipal();
        return employeeEntity.getEmployee_id() ;

    }







}




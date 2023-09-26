package com.hepsijet.kargo2.JWT.auth;

import com.hepsijet.kargo2.Employee.Models.EmployeeEntity;
import com.hepsijet.kargo2.Employee.Models.Role;
import com.hepsijet.kargo2.Employee.Repositorys.EmployeeRepo;
import com.hepsijet.kargo2.JWT.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final EmployeeRepo employeeRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = EmployeeEntity.builder()
                .employee_name(request.getFirstname())
                .employee_surname(request.getLastname())
                .working_xdock(request.getWorking_xdock())
                .employee_username(request.getUsername())
                .employee_password(passwordEncoder.encode(request.getPassword()))
                .role(Role.COURIER)
                .build();
        employeeRepo.save(user);
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        var user = EmployeeEntity.builder()
                .employee_name(request.getFirstname())
                .employee_surname(request.getLastname())
                .working_xdock(request.getWorking_xdock())
                .employee_username(request.getUsername())
                .employee_password(passwordEncoder.encode(request.getPassword()))
                .role(Role.OPERATION)
                .build();
        employeeRepo.save(user);
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse registerCollector(RegisterRequest request){
        var user = EmployeeEntity.builder()
                .employee_name(request.getFirstname())
                .employee_surname(request.getLastname())
                .working_xdock(request.getWorking_xdock())
                .employee_username(request.getUsername())
                .employee_password(passwordEncoder.encode(request.getPassword()))
                .role(Role.COLLECTOR)
                .build();
        employeeRepo.save(user);
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse registerOrderer(RegisterRequest request){
        var user = EmployeeEntity.builder()
                .employee_name(request.getFirstname())
                .employee_surname(request.getLastname())
                .working_xdock(request.getWorking_xdock())
                .employee_username(request.getUsername())
                .employee_password(passwordEncoder.encode(request.getPassword()))
                .working_company_id(request.getWorking_company_id())
                .role(Role.ORDERER)
                .build();
        employeeRepo.save(user);
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }










}
/*
 public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = employeeRepo.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
 */

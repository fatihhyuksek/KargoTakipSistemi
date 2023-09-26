package com.hepsijet.kargo2.JWT.auth;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Hidden
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/hello")
    public String sayHello(){
        return "hello";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse>register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse>registerAdmin(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.registerAdmin(request));
    }
    @PostMapping("/register/collector")
    public ResponseEntity<AuthenticationResponse> registerCollector(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.registerCollector(request));
    }
    @PostMapping(value = "/register/orderer")
    public ResponseEntity<AuthenticationResponse> registerCompanyEmployee(
            @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(authenticationService.registerOrderer(request));
    }
  /*
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse>authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));

    }
   */



}

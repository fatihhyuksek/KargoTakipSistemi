package com.hepsijet.kargo2.Company.Controller;

import com.hepsijet.kargo2.Company.CompanyService;
import com.hepsijet.kargo2.JWT.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/Company")
public class CompanyController {

    private final CompanyService companyService;
    private final AuthenticationService authenticationService;


    @GetMapping
    public String sayHello(){
        return "hello";
    }

}

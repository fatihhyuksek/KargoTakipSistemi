package com.hepsijet.kargo2.Company;

import com.hepsijet.kargo2.Company.Repository.CompanyRepo;
import com.hepsijet.kargo2.JWT.JwtService;
import com.hepsijet.kargo2.JWT.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements  CompanyService{
    private final CompanyRepo companyRepo;
    public boolean checkCompany(Long id){
        if (companyRepo.findCompanyWithCompanyId(id) != null){
            return true;
        }
        else{
            return false;
        }

    }
    public String getCompanyName(Long id){
        return companyRepo.getCompanyNameWithId(id);
    }

    public String getCompanyDistrict(Long id){
        return companyRepo.getCompanyDistrictWithId(id);
    }

    public String getCompanyTransferCenter(String company){
        return companyRepo.getTransferCenterWithCompanyName(company);
    }


}

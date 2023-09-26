package com.hepsijet.kargo2.Company;

import org.springframework.stereotype.Service;

@Service
public interface CompanyService {

    public boolean checkCompany(Long id);

    public String getCompanyName(Long id);
    public String getCompanyDistrict(Long id);

    public String getCompanyTransferCenter(String company);
    }

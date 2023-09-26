package com.hepsijet.kargo2.Company.Repository;

import com.hepsijet.kargo2.Company.Models.CompanyEntity;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<CompanyEntity,Long> {

    @Query(value = "select * from companys where company_id = :id",nativeQuery = true)
    public CompanyEntity findCompanyWithCompanyId(Long id);
    @Query(value = "select company_name from companys  where  company_id = :id",nativeQuery = true)
    public String getCompanyNameWithId(Long id);

    @Query(value = "select company_district from companys where company_id= :id",nativeQuery = true)
    String getCompanyDistrictWithId(Long id);

    @Query(value = "select transfer_center from companys where company_name = :company",nativeQuery = true)
    String getTransferCenterWithCompanyName(String company);
}

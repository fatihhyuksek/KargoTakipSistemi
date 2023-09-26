package com.hepsijet.kargo2.Company.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "companys")
public class CompanyEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long company_id;

    private String company_name;

    private String company_address;

    private String company_city;

    private String company_district;

    private String transfer_center;


}

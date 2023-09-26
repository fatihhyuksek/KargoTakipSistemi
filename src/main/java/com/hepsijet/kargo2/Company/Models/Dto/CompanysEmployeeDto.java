package com.hepsijet.kargo2.Company.Models.Dto;

import com.hepsijet.kargo2.Employee.Models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanysEmployeeDto {

    private String name;

    private String surname;

    private Long working_company_id;

    private String username;

    private String password;


}

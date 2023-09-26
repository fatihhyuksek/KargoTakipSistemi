package com.hepsijet.kargo2.Customer.Models;

import lombok.Data;

@Data
public class CustomerDto {
    private String customer_tc;
    private String customer_name;
    private String customer_surname;
    private String customer_address;
    private String customer_city;
    private String customer_district;

}

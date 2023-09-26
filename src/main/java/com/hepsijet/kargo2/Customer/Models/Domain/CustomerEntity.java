package com.hepsijet.kargo2.Customer.Models.Domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "customers")
public class CustomerEntity {
    @Column(name = "customer_tc")
    private String customer_tc;

    @Column(name = "customer_name")
    private String customer_name;

    @Column(name = "customer_surname")
    private String customer_surname;

    @Column(name = "customer_address")
    private String customer_address;

    @Column(name = "customer_city")
    private String customer_city;

    @Column(name = "customer_district")
    private String customer_district;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
}

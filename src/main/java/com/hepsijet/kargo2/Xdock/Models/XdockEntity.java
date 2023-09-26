package com.hepsijet.kargo2.Xdock.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "xdock")
public class XdockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "xdock_number")
    private String xdock_number;

    @Column(name = "transfer_center")
    private String transfer_center;
}

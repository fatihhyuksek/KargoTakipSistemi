package com.hepsijet.kargo2.Transaction;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "delivery_transaction")
public class DeliveryTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "barcode")
    private Long barcode;

    @Column(name = "customer_tc" )
    @Size(min = 11,max = 11)
    private String customer_tc;

    @Column(name = "status")
    private String status;

    @Column(name = "sender_company")
    private String sender_company;

    @Column(name = "customer_name")
    private String customer_name;

    @Column(name = "customer_surname")
    private String customer_surname;

    @Column(name = "customer_address")
    private String customer_address;

    @Column(name = "customer_city")
    private String customer_city;

    @Column (name = "customer_district")
    private String customer_district;

    @Column(name = "receiver_xdock")
    private String receiver_xdock;

    @Column (name = "employee_id")
    private  Long employee_id;

    @Column(name = "current_xdock")
    private String current_xdock;

    @Column(name = "event_time")
    private Date tarih =new Date();
}

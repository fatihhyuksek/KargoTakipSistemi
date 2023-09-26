package com.hepsijet.kargo2.Delivery.Models;

import lombok.Data;

@Data
public class DeliveryDto {
    private String customer_tc;

    private Long barcode;

    private String status;

    private String sender_company;

    private String customer_name;

    private String customer_surname;

    private String customer_address;

    private String customer_city;

    private String customer_district;


    private String receiver_xdock;

    private  Long employee_id;

    private String current_xdock;

}

package com.hepsijet.kargo2.JWT.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private  String firstname;

    private String lastname;

    private String working_xdock;

    private String working_company_id;

    private String username;

    private String password;
}

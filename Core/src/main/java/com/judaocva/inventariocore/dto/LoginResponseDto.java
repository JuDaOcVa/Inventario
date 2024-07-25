package com.judaocva.inventariocore.dto;

import lombok.Data;

@Data
public class LoginResponseDto {

    private String name;
    private String email;
    private String phone;
    private String token;
}

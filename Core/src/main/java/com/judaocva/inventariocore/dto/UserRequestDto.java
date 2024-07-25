package com.judaocva.inventariocore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequestDto {

    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private int status;
}

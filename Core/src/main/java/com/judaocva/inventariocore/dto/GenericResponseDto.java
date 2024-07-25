package com.judaocva.inventariocore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponseDto {
    private String message;
    private Object data;
    private int status;
}

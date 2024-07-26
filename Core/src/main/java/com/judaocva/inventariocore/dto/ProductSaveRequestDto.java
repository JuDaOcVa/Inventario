package com.judaocva.inventariocore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductSaveRequestDto {

    int id;
    @SerializedName("id_user")
    int idUser;
    String token;
    @SerializedName("product_name")
    String productName;
    int quantity;
    int status;
}

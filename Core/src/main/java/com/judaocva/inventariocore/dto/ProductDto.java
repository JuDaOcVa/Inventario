package com.judaocva.inventariocore.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ProductDto {

    private int id;
    @SerializedName("id_user")
    private int idUser;
    @SerializedName("product_name")
    private String productName;
    private int quantity;
    private int status;
}

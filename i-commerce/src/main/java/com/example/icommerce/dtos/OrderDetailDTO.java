package com.example.icommerce.dtos;

import lombok.Data;

@Data
public class OrderDetailDTO {

    private ProductDTO product;
    private int        quantity;
}

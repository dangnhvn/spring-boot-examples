package com.example.icommerce.dtos;

import lombok.Data;

@Data
public class UserActivityDTO {

    private String api;
    private String method;
    private String query;


}

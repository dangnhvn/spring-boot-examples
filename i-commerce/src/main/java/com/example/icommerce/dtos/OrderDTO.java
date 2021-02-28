package com.example.icommerce.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class OrderDTO {

    private String               orderNumber;
    private String               status;
    private List<OrderDetailDTO> details = new ArrayList<>();
    private BigDecimal           total;
}

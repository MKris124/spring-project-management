package com.szoftverhazi.projektmanagement.dto;

import com.szoftverhazi.projektmanagement.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long customerId;
    private String details;
    private OrderStatus status;
}

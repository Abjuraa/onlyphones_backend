package com.onlyphones.onlyphones.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String model;
    private String capacity;
    private String color;
    private BigDecimal price;
    private Integer discount;
    private Boolean hasDiscount;
    private String categoryId;
}

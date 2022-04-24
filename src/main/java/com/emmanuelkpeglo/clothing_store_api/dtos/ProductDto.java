package com.emmanuelkpeglo.clothing_store_api.dtos;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String unit;
    private double price;
}

package com.emmanuelkpeglo.clothing_store_api.dtos;

import lombok.Data;

@Data
public class CustomerDto {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String postalCode;
    private String country;
}

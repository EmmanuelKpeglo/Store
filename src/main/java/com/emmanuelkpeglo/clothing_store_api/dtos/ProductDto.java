package com.emmanuelkpeglo.clothing_store_api.dtos;

import com.emmanuelkpeglo.clothing_store_api.dtos.base.BaseEntityDto;
import com.emmanuelkpeglo.clothing_store_api.dtos.base.DTOBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class ProductDto{
    private Long id;
    private String name;
    private String unit;
    private double price;
}

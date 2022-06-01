package com.emmanuelkpeglo.clothing_store_api.controllers;

import com.emmanuelkpeglo.clothing_store_api.controllers.generic.implementations.GenericControllerImpl;
import com.emmanuelkpeglo.clothing_store_api.dtos.ProductDto;
import com.emmanuelkpeglo.clothing_store_api.models.Product;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController extends GenericControllerImpl<ProductDto, Product> {
    public ProductController() {
        super(ProductDto.class, Product.class);
    }
}

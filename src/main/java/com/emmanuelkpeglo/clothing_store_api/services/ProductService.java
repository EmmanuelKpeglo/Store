package com.emmanuelkpeglo.clothing_store_api.services;

import com.emmanuelkpeglo.clothing_store_api.models.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product);
    Product updateProduct(long id, Product product);
    List<Product> getProducts();
    Product getProductById(long id);
    void removeProduct(long id);
}

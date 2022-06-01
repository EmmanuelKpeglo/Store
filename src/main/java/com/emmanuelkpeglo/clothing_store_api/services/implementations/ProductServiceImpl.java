package com.emmanuelkpeglo.clothing_store_api.services.implementations;

import com.emmanuelkpeglo.clothing_store_api.exceptions.ResourceNotFoundException;
import com.emmanuelkpeglo.clothing_store_api.models.Product;
import com.emmanuelkpeglo.clothing_store_api.services.ProductService;
import com.emmanuelkpeglo.clothing_store_api.services.generic.implementations.GenericServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ProductServiceImpl extends GenericServiceImpl<Product> implements ProductService {

    @Override
    public Product update(Long id, Product productRequest) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource with id: " + id + " not found!"));
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setUnit(productRequest.getUnit());
        return repository.save(product);
    }
}

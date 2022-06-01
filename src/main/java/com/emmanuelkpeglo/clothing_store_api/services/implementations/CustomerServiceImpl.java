package com.emmanuelkpeglo.clothing_store_api.services.implementations;

import com.emmanuelkpeglo.clothing_store_api.exceptions.ResourceNotFoundException;
import com.emmanuelkpeglo.clothing_store_api.models.Customer;
import com.emmanuelkpeglo.clothing_store_api.services.CustomerService;
import com.emmanuelkpeglo.clothing_store_api.services.generic.implementations.GenericServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Primary
@Service
public class CustomerServiceImpl extends GenericServiceImpl<Customer> implements CustomerService{

    @Override
    public Customer update(Long id, Customer customerRequest) {
        Customer customer = repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Resource with id: " + id + " not found!"));
        customer.setAddress(customerRequest.getAddress());
        customer.setCity(customerRequest.getCity());
        customer.setCountry(customerRequest.getCountry());
        customer.setName(customerRequest.getName());
        customer.setPostalCode(customerRequest.getPostalCode());

        return repository.save(customer);
    }
}

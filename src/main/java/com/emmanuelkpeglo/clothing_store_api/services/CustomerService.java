package com.emmanuelkpeglo.clothing_store_api.services;

import com.emmanuelkpeglo.clothing_store_api.models.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customer);
    List<Customer> getCustomers();
    Customer getCustomerById(Long id);
    void removeCustomer(Long id);
}

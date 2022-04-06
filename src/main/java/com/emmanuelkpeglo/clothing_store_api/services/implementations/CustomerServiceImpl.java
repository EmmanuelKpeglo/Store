package com.emmanuelkpeglo.clothing_store_api.services.implementations;

import com.emmanuelkpeglo.clothing_store_api.dao.CustomerRepository;
import com.emmanuelkpeglo.clothing_store_api.exceptions.ResourceNotFoundException;
import com.emmanuelkpeglo.clothing_store_api.models.Customer;
import com.emmanuelkpeglo.clothing_store_api.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id: " + id + " not found!"));
    }

    @Override
    public void removeCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer with id: " + id + " not found!"));
        customerRepository.delete(customer);
    }
    @Override
    public Customer updateCustomer(Long id, Customer customerRequest) {
        Customer customer = customerRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer with id: " + id + " not found!"));
        customer.setAddress(customerRequest.getAddress());
        customer.setCity(customerRequest.getCity());
        customer.setCountry(customerRequest.getCountry());
        customer.setName(customerRequest.getName());
        customer.setPostalCode(customerRequest.getPostalCode());
        return customerRepository.save(customer);
    }
}

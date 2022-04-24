package com.emmanuelkpeglo.clothing_store_api.controllers;

import com.emmanuelkpeglo.clothing_store_api.dtos.CustomerDto;
import com.emmanuelkpeglo.clothing_store_api.models.Customer;
import com.emmanuelkpeglo.clothing_store_api.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private ModelMapper modelMapper;
    private CustomerService customerService;

    public CustomerController(ModelMapper modelMapper, CustomerService customerService) {
        this.modelMapper = modelMapper;
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customerDtos = customerService.getCustomers()
                .stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> getCustomerByID(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);

        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        Customer customerRequest = modelMapper.map(customerDto, Customer.class);
        Customer newCustomer = customerService.createCustomer(customerRequest);

        CustomerDto newCustomerDto = modelMapper.map(newCustomer, CustomerDto.class);

        return new ResponseEntity<CustomerDto>(newCustomerDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        Customer customerRequest = modelMapper.map(customerDto, Customer.class);
        Customer updatedCustomer = customerService.updateCustomer(id, customerRequest);
        CustomerDto updatedCustomerDto = modelMapper.map(updatedCustomer, CustomerDto.class);

        return new ResponseEntity<CustomerDto>(updatedCustomerDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.removeCustomer(id);

        return new ResponseEntity<String>("Customer with id: " + id + " removed!", HttpStatus.OK);
    }
}

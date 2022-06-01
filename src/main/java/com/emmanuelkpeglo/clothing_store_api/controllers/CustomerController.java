package com.emmanuelkpeglo.clothing_store_api.controllers;

import com.emmanuelkpeglo.clothing_store_api.controllers.generic.implementations.GenericControllerImpl;
import com.emmanuelkpeglo.clothing_store_api.dtos.CustomerDto;
import com.emmanuelkpeglo.clothing_store_api.models.Customer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController extends GenericControllerImpl<CustomerDto, Customer> {
    public CustomerController() {
        super(CustomerDto.class, Customer.class);
    }
}

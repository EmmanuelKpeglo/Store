package com.emmanuelkpeglo.clothing_store_api.services;

import com.emmanuelkpeglo.clothing_store_api.models.Customer;
import com.emmanuelkpeglo.clothing_store_api.services.generic.GenericService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface CustomerService extends GenericService<Customer> {

}

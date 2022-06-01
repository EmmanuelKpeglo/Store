package com.emmanuelkpeglo.clothing_store_api.dao;

import com.emmanuelkpeglo.clothing_store_api.dao.generic.GenericRepository;
import com.emmanuelkpeglo.clothing_store_api.models.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends GenericRepository<Customer> {

}

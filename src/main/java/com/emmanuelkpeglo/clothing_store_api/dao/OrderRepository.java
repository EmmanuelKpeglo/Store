package com.emmanuelkpeglo.clothing_store_api.dao;

import com.emmanuelkpeglo.clothing_store_api.dao.generic.GenericRepository;
import com.emmanuelkpeglo.clothing_store_api.models.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends GenericRepository<Order> {
    List<Order> findByCustomerId(long id);
}

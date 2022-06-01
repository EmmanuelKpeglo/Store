package com.emmanuelkpeglo.clothing_store_api.dao;

import com.emmanuelkpeglo.clothing_store_api.dao.generic.GenericRepository;
import com.emmanuelkpeglo.clothing_store_api.models.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends GenericRepository<Order> {
}

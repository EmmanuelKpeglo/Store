package com.emmanuelkpeglo.clothing_store_api.services.implementations;

import com.emmanuelkpeglo.clothing_store_api.dao.OrderRepository;
import com.emmanuelkpeglo.clothing_store_api.models.Order;
import com.emmanuelkpeglo.clothing_store_api.services.OrderService;
import com.emmanuelkpeglo.clothing_store_api.services.generic.implementations.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class OrderServiceImpl extends GenericServiceImpl<Order> implements OrderService {

    @Override
    public Order update(Long id, Order entity) {
        return null;
    }

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getByCustomerId(long id) {
        return orderRepository.findByCustomerId(id);
    }
}

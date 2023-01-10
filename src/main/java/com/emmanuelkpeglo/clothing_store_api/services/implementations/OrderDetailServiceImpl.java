package com.emmanuelkpeglo.clothing_store_api.services.implementations;

import com.emmanuelkpeglo.clothing_store_api.dao.OrderDetailRepository;
import com.emmanuelkpeglo.clothing_store_api.models.OrderDetail;
import com.emmanuelkpeglo.clothing_store_api.services.OrderDetailService;
import com.emmanuelkpeglo.clothing_store_api.services.generic.implementations.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class OrderDetailServiceImpl extends GenericServiceImpl<OrderDetail> implements OrderDetailService {
    @Override
    public OrderDetail update(Long id, OrderDetail entity) {
        return null;
    }

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getByOrderId(long id) {
        return orderDetailRepository.findByOrderId(id);
    }

    @Override
    public List<OrderDetail> getByProductId(long id) {
        return orderDetailRepository.findByProductId(id);
    }

    @Override
    public void deleteOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.delete(orderDetail);
    }
}

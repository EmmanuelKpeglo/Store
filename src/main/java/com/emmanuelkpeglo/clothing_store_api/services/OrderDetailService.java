package com.emmanuelkpeglo.clothing_store_api.services;

import com.emmanuelkpeglo.clothing_store_api.models.OrderDetail;
import com.emmanuelkpeglo.clothing_store_api.services.generic.GenericService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderDetailService extends GenericService<OrderDetail> {
    List<OrderDetail> getByOrderId(long id);
    List<OrderDetail> getByProductId(long id);
    void deleteOrderDetail(OrderDetail orderDetail);
}

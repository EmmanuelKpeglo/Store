package com.emmanuelkpeglo.clothing_store_api.services;

import com.emmanuelkpeglo.clothing_store_api.models.Order;
import com.emmanuelkpeglo.clothing_store_api.services.generic.GenericService;
import org.springframework.stereotype.Component;

@Component
public interface OrderService extends GenericService<Order> {
}

package com.emmanuelkpeglo.clothing_store_api.dao;

import com.emmanuelkpeglo.clothing_store_api.dao.generic.GenericRepository;
import com.emmanuelkpeglo.clothing_store_api.models.OrderDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends GenericRepository<OrderDetail> {
    List<OrderDetail> findByOrderId(long id);

    List<OrderDetail> findByProductId(long id);
}

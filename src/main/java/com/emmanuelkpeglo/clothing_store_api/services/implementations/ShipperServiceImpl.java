package com.emmanuelkpeglo.clothing_store_api.services.implementations;

import com.emmanuelkpeglo.clothing_store_api.exceptions.ResourceNotFoundException;
import com.emmanuelkpeglo.clothing_store_api.models.Shipper;
import com.emmanuelkpeglo.clothing_store_api.services.ShipperService;
import com.emmanuelkpeglo.clothing_store_api.services.generic.implementations.GenericServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ShipperServiceImpl extends GenericServiceImpl<Shipper> implements ShipperService {

    @Override
    public Shipper update(Long id, Shipper shipperRequest) {
        Shipper shipper = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource with id: " + id + " not found!"));
        shipper.setId(shipperRequest.getId());
        shipper.setName(shipperRequest.getName());
        shipper.setPhone(shipperRequest.getPhone());
        return repository.save(shipper);
    }
}

package com.emmanuelkpeglo.clothing_store_api.services.implementations;

import com.emmanuelkpeglo.clothing_store_api.dao.ShipperRepository;
import com.emmanuelkpeglo.clothing_store_api.exceptions.ResourceNotFoundException;
import com.emmanuelkpeglo.clothing_store_api.models.Shipper;
import com.emmanuelkpeglo.clothing_store_api.services.ShipperService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Primary
public class ShipperServiceImpl implements ShipperService {
    private ShipperRepository shipperRepository;

    @Override
    public Shipper createShipper(Shipper shipper) {
        return shipperRepository.save(shipper);
    }

    @Override
    public Shipper updateShipper(long id, Shipper shipperRequest) {
        Shipper shipper = shipperRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipper with id: " + id + " not found!"));
        shipper.setId(shipperRequest.getId());
        shipper.setName(shipperRequest.getName());
        shipper.setPhone(shipperRequest.getPhone());
        return shipperRepository.save(shipper);
    }

    @Override
    public List<Shipper> getShippers() {
        return shipperRepository.findAll();
    }

    @Override
    public Shipper getShipperById(long id) {
        return shipperRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipper with id: " + id + " not found!"));
    }

    @Override
    public void removeShipper(long id) {
        Shipper shipper = shipperRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipper with id: " + id + " not found!"));
        shipperRepository.delete(shipper);
    }
}

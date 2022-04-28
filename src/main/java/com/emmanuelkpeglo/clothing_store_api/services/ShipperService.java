package com.emmanuelkpeglo.clothing_store_api.services;

import com.emmanuelkpeglo.clothing_store_api.models.Shipper;

import java.util.List;

public interface ShipperService {
    Shipper createShipper(Shipper shipper);
    Shipper updateShipper(long id, Shipper shipper);
    List<Shipper> getShippers();
    Shipper getShipperById(long id);
    void removeShipper(long id);
}

package com.emmanuelkpeglo.clothing_store_api.controllers;

import com.emmanuelkpeglo.clothing_store_api.controllers.generic.implementations.GenericControllerImpl;
import com.emmanuelkpeglo.clothing_store_api.dtos.ShipperDto;
import com.emmanuelkpeglo.clothing_store_api.models.Shipper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shippers")
public class ShipperController extends GenericControllerImpl<ShipperDto, Shipper> {
    public ShipperController() {
        super(ShipperDto.class, Shipper.class);
    }
}

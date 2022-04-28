package com.emmanuelkpeglo.clothing_store_api.services.implementations;

import com.emmanuelkpeglo.clothing_store_api.dao.ShipperRepository;
import com.emmanuelkpeglo.clothing_store_api.exceptions.ResourceNotFoundException;
import com.emmanuelkpeglo.clothing_store_api.models.Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShipperServiceImplTest {
    List<Shipper> shipperList;
    Shipper shipper;

    @Mock
    ShipperRepository shipperRepository;

    @Autowired
    @InjectMocks
    ShipperServiceImpl shipperService;

    @BeforeEach
    void setUp() {
        shipperList = List.of(
                new Shipper(1L, "lee", "054"),
                new Shipper(2L, "yoshi", "020")
        );

        shipper = new Shipper(3L, "abu", "233");
    }

    @Test
    @DisplayName("creates a new shipper")
    void createShipper() {
        when(shipperRepository.save(any(Shipper.class))).thenReturn(shipper);

        assertThat(shipperService.createShipper(shipper)).returns("abu", Shipper::getName);
        assertThat(shipperService.createShipper(shipper)).returns(3L, Shipper::getId);
    }

    @Nested
    @DisplayName("updates a shipper")
    class UpdateShipper {
        @Test
        @DisplayName("updates a shipper if found")
        void shouldUpdateShipper() {
            long id = 2;
            when(shipperRepository.findById(anyLong())).thenReturn(Optional.of(shipperList.get(1)));
            when(shipperRepository.save(any(Shipper.class))).thenReturn(shipperList.get(1));

            Shipper shipperUpdate = shipperList.get(1);
            shipperUpdate.setName("lalo");
            shipperUpdate.setPhone("123");

            assertThat(shipperService.updateShipper(id, shipperUpdate)).returns(id, Shipper::getId);
            assertThat(shipperService.updateShipper(id, shipperUpdate)).returns("lalo", Shipper::getName);
        }

        @Test
        @DisplayName("throws an error given that shipper does not exist")
        void shouldThrowAnError() {
            long id = 4;
            when(shipperRepository.findById(anyLong())).thenReturn(Optional.empty());

            Shipper shipperUpdate = new Shipper();
            shipperUpdate.setName("lalo");
            shipperUpdate.setPhone("123");

            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
                    () -> shipperService.updateShipper(id, shipperUpdate));

            assertThat(thrown.getMessage()).isEqualTo("Shipper with id: " + id + " not found!");
        }

    }

    @Nested
    @DisplayName("gets a list of shippers")
    class GetShippers {
        @Test
        @DisplayName("returns no shipper")
        void shouldReturnZeroShippers() {
            when(shipperRepository.findAll()).thenReturn(new ArrayList<Shipper>());
            List<Shipper> shippers = shipperService.getShippers();

            assertThat(shippers.size()).isZero();
        }

        @Test
        @DisplayName("returns a list of 2 shippers")
        void shouldReturnTwoShippers() {
            when(shipperRepository.findAll()).thenReturn(shipperList);
            List<Shipper> shippers = shipperService.getShippers();

            assertThat(shippers.size()).isEqualTo(2);
            assertThat(shippers.get(0).getName()).isEqualTo("lee");
        }
    }

    @Nested
    @DisplayName("gets a shipper by id")
    class GetShipperById {
        @Test
        @DisplayName("returns a shipper ")
        void shouldReturnAShipper() {
            long id = 2;
            when(shipperRepository.findById(anyLong())).thenReturn(Optional.of(shipperList.get(1)));

            assertThat(shipperService.getShipperById(id)).returns("yoshi", Shipper::getName);
            assertThat(shipperService.getShipperById(id)).returns(2L, Shipper::getId);
        }

        @Test
        @DisplayName("throws an exception given that shipper is not found")
        void shouldThrowAnException() {
            long id = 4;
            when(shipperRepository.findById(anyLong())).thenReturn(Optional.empty());

            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
                    () -> shipperService.getShipperById(id));

            assertThat(thrown.getMessage()).isEqualTo("Shipper with id: " + id + " not found!");
        }
    }

    @Nested
    @DisplayName("removes a shipper")
    class RemoveShipper {
        @Test
        @DisplayName("removes shipper if found")
        void shouldRemoveShipper() {
            long id = 3;
            when(shipperRepository.findById(anyLong())).thenReturn(Optional.of(shipper));
            shipperService.removeShipper(id);
            shipperService.removeShipper(id);
            shipperService.removeShipper(id);

            verify(shipperRepository, times(3)).delete(shipper);
        }

        @Test
        @DisplayName("throws an exception given that shipper does not exist")
        void shouldThrowAnException() {
            long id = 4;
            when(shipperRepository.findById(anyLong())).thenReturn(Optional.empty());

            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
                    () -> shipperService.removeShipper(id));

            assertThat(thrown.getMessage()).isEqualTo("Shipper with id: " + id + " not found!");

        }
    }
}
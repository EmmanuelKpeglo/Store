package com.emmanuelkpeglo.clothing_store_api.controllers;

import com.emmanuelkpeglo.clothing_store_api.dtos.ShipperDto;
import com.emmanuelkpeglo.clothing_store_api.exceptions.ResourceNotFoundException;
import com.emmanuelkpeglo.clothing_store_api.models.Shipper;
import com.emmanuelkpeglo.clothing_store_api.models.Shipper;
import com.emmanuelkpeglo.clothing_store_api.services.generic.GenericService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShipperController.class)
class ShipperControllerTest {
    List<Shipper> shipperList;
    Shipper shipper;
    ShipperDto shipperDto;
    String shipper_base_url;

    @MockBean
    private GenericService<Shipper> shipperService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        shipper_base_url = "/api/v1/shippers";
        shipperList = List.of(
                new Shipper(1L, "kal", "024"),
                new Shipper(2L, "ralph", "244")
        );
        shipper = new Shipper(3L, "sol", "504");
        shipperDto = modelMapper.map(shipper, ShipperDto.class);
    }

    @Nested
    @DisplayName("gets shippers")
    class GetShippers {
        @Test
        @DisplayName("returns two shippers")
        void shouldGetShippers() throws Exception {
            when(shipperService.findAll()).thenReturn(shipperList);

            mockMvc.perform(
                            get(shipper_base_url)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()", equalTo(2)))
                    .andExpect(jsonPath("$[0].name", equalTo("kal")));
        }

        @Test
        @DisplayName("returns an empty list of shippers")
        void shouldGetZeroShippers() throws Exception {
            when(shipperService.findAll()).thenReturn(new ArrayList<Shipper>());

            mockMvc.perform(
                            get(shipper_base_url)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()", equalTo(0)));
        }
    }

    @Nested
    @DisplayName("gets a shipper by id")
    class GetAShipper {
        @Test
        @DisplayName("returns a shipper if exists")
        void shouldGetShipperIfExists() throws Exception {
            long id = 2;
            when(shipperService.findById(anyLong())).thenReturn(shipperList.get(1));

            mockMvc.perform(
                            get(shipper_base_url + "/" + id)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", equalTo(2)))
                    .andExpect(jsonPath("$.phone", equalTo("244")));
        }

        @Test
        @DisplayName("throws an exception if shipper does not exist")
        void shouldThrowAnException() throws Exception {
            long id = 4;
            when(shipperService.findById(anyLong()))
                    .thenThrow(new ResourceNotFoundException("Shipper with id: " + id + " not found!"));

            mockMvc.perform(
                            get(shipper_base_url + "/" + id)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message", equalTo("Shipper with id: " + id + " not found!")));
        }
    }

    @Test
    @DisplayName("adds a shipper")
    void shouldAddAShipper() throws Exception {
        when(shipperService.save(any(Shipper.class))).thenReturn(shipper);

        mockMvc.perform(
                        post(shipper_base_url + "/add")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(shipperDto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("sol")));
    }

    @Nested
    @DisplayName("updates a shipper")
    class UpdateShipper {
        @Test
        @DisplayName("update a shipper given that it exists")
        void shouldUpdateAShipper() throws Exception {
            long id = 1L;
            ShipperDto oldShipperDto = modelMapper.map(shipperList.get(0), ShipperDto.class);
            oldShipperDto.setName("leo");
            oldShipperDto.setPhone("654");
            Shipper updatedShipper = modelMapper.map(oldShipperDto, Shipper.class);
            when(shipperService.update(anyLong(), any(Shipper.class))).thenReturn(updatedShipper);

            mockMvc.perform(
                            put(shipper_base_url + "/" + id)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(oldShipperDto))
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", equalTo("leo")))
                    .andExpect(jsonPath("$.phone", equalTo("654")));
        }

        @Test
        @DisplayName("throws an exception given that shipper does not exist")
        void shouldThrowAnException() throws Exception {
            long id = 5;
            when(shipperService.update(anyLong(), any(Shipper.class)))
                    .thenThrow(new ResourceNotFoundException("Shipper with id: " + id + " not found!"));

            mockMvc.perform(
                            put(shipper_base_url + "/" + id)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(shipperDto))
                    )
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message", equalTo("Shipper with id: " + id + " not found!")));
        }
    }

    @Nested
    @DisplayName("removes a shipper")
    class RemoveShipper {
        @Test
        @DisplayName("removes shipper given it exists")
        void shouldRemoveShipper() throws Exception {
            long id = 2;
            doNothing().when(shipperService).delete(anyLong());

            mockMvc.perform(
                            delete(shipper_base_url + "/" + id)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("throws an exception given that shipper does not exits")
        void shouldThrowAnException() throws Exception {
            long id = 4;
            doThrow(new ResourceNotFoundException("Shipper with id: " + id + " not found!"))
                    .when(shipperService).delete(anyLong());

            mockMvc.perform(
                            delete(shipper_base_url + "/" + id)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message", equalTo("Shipper with id: " + id + " not found!")));
        }
    }
}
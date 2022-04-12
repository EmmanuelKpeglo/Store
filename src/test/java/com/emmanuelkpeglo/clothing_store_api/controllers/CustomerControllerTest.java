package com.emmanuelkpeglo.clothing_store_api.controllers;

import com.emmanuelkpeglo.clothing_store_api.dtos.CustomerDto;
import com.emmanuelkpeglo.clothing_store_api.exceptions.ResourceNotFoundException;
import com.emmanuelkpeglo.clothing_store_api.models.Customer;
import com.emmanuelkpeglo.clothing_store_api.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.equalTo;

@WebMvcTest(controllers = {CustomerController.class})
class CustomerControllerTest {
    List<Customer> customers;
    Customer customer;
    CustomerDto customerDto;
    String customer_base_url;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        customer_base_url = "/api/v1/customers";
        customers = Arrays.asList(
                new Customer(1L, "yoshi", "142", "yoyo", "abq", "yoshiland"),
                new Customer(2L, "lee", "ca345", "lala", "44to", "lalaland")
        );

        customer = new Customer(3L, "zonda", "hul345", "dond", "44to", "kuvikiland");

        customerDto = modelMapper.map(customer, CustomerDto.class);
    }

    @Test
    @DisplayName("creates a new customer")
    void shouldCreateACustomer() throws Exception{
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(
                post(customer_base_url + "/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("zonda")));
    }

    @Nested
    @DisplayName("updates a customer")
    class UpdateCustomer {
        @Test
        @DisplayName("updates a customer if customer exists")
        void shouldUpdateCustomerIfExists() throws Exception{
            long id = 2L;
            CustomerDto customerRequest = modelMapper.map(customers.get(1), CustomerDto.class);
            Customer updatedCustomer = customers.get(1);
            updatedCustomer.setName("onini");
            updatedCustomer.setCountry("timbuktu");
            when(customerService.updateCustomer(anyLong(), any(Customer.class))).thenReturn(updatedCustomer);

            mockMvc.perform(
                    put(customer_base_url + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(customerRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", equalTo("onini")))
                    .andExpect(jsonPath("$.country", equalTo("timbuktu")))
                    .andExpect(jsonPath("$.city", equalTo("lala")));
        }

        @Test
        @DisplayName("throws an exception if customer is not found")
        void shouldThrowAnExceptionIfCustomerIsNotFound() throws Exception{
            long id = 6L;
            when(customerService.updateCustomer(anyLong(), any(Customer.class)))
                    .thenThrow(new ResourceNotFoundException("Customer with id: " + id + " not found!"));
            mockMvc.perform(
                    put(customer_base_url + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(customer)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message", equalTo("Customer with id: " + id + " not found!")));

        }
    }

    @Nested
    @DisplayName("gets all customers")
    class GetCustomers {
        @Test
        @DisplayName("returns two customers")
        void shouldGetTwoCustomers() throws Exception{
            when(customerService.getCustomers()).thenReturn(customers);

            mockMvc.perform(
                    get(customer_base_url)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()", equalTo(2)))
                    .andExpect(jsonPath("$[0].name", equalTo("yoshi")));
        }

        @Test
        @DisplayName("returns an empty list assuming there are no customers in the database")
        void shouldReturnAnEmptyListOfCustomers() throws Exception {
            when(customerService.getCustomers()).thenReturn(new ArrayList<Customer>());

            mockMvc.perform(
                    get(customer_base_url)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()", equalTo(0)));
        }
    }

    @Nested
    @DisplayName("gets a customer by id")
    class GetACustomer {
        @Test
        @DisplayName("returns a customer if the customer exists")
        void shouldGetCustomerIfExists() throws Exception{
            long id = 2L;
            when(customerService.getCustomerById(anyLong())).thenReturn(customers.get(1));

            mockMvc.perform(
                    get(customer_base_url + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", equalTo(2)))
                    .andExpect(jsonPath("$.name", equalTo("lee")));
        }

        @Test
        @DisplayName("throws an exception assuming customer does not exist")
        void shouldThrowAnExceptionIfCustomerDoesNotExist() throws Exception {
            long id = 6L;
            when(customerService.getCustomerById(anyLong()))
                    .thenThrow(new ResourceNotFoundException("Customer with id: " + id + " not found!"));

            mockMvc.perform(
                    get(customer_base_url + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message", equalTo("Customer with id: " + id + " not found!")));
        }
    }

    @Nested
    @DisplayName("removes a customer")
    class RemoveCustomer {
        @Test
        @DisplayName("deletes a customer assuming customer exists")
        void shouldDeleteCustomerIfExists() throws Exception{
            long id = 1;
            doNothing().when(customerService).removeCustomer(id);

            mockMvc.perform(
                    delete(customer_base_url + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("throws an exception if customer does not exist")
        void shouldThrowAnExceptionIfCustomerDoesNotExist () throws Exception {
            long id = 1;
            doThrow(new ResourceNotFoundException("Customer with id: " + id + " not found!"))
                    .when(customerService).removeCustomer(id);

            mockMvc.perform(
                            delete(customer_base_url + "/" + id)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }
}
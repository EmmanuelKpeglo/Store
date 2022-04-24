package com.emmanuelkpeglo.clothing_store_api.controllers;

import com.emmanuelkpeglo.clothing_store_api.dtos.ProductDto;
import com.emmanuelkpeglo.clothing_store_api.exceptions.ResourceNotFoundException;
import com.emmanuelkpeglo.clothing_store_api.models.Product;
import com.emmanuelkpeglo.clothing_store_api.services.ProductService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {
    List<Product> productList;
    Product product;
    ProductDto productDto;
    String product_base_url;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        product_base_url = "/api/v1/products";
        productList = List.of(
                new Product(1L, "T -shirt", "3 pieces", 8.7),
                new Product(2L, "Socks", "2 pairs", 3.6)
        );
        product = new Product(3L, "Wrist Watch", "1 piece", 4.5);
        productDto = modelMapper.map(product, ProductDto.class);
    }

    @Nested
    @DisplayName("gets products")
    class GetProducts {
        @Test
        @DisplayName("returns two products")
        void shouldGetProducts() throws Exception {
            when(productService.getProducts()).thenReturn(productList);

            mockMvc.perform(
                    get(product_base_url)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()", equalTo(2)))
                    .andExpect(jsonPath("$[0].price", equalTo(8.7)));
        }

        @Test
        @DisplayName("returns an empty list of products")
        void shouldGetZeroProducts() throws Exception {
            when(productService.getProducts()).thenReturn(new ArrayList<Product>());

            mockMvc.perform(
                    get(product_base_url)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()", equalTo(0)));
        }
    }

    @Nested
    @DisplayName("gets a product by id")
    class GetAProduct {
        @Test
        @DisplayName("returns a product if exists")
        void shouldGetProductIfExists() throws Exception {
            long id = 2;
            when(productService.getProductById(anyLong())).thenReturn(productList.get(1));

            mockMvc.perform(
                    get(product_base_url + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", equalTo(2)))
                    .andExpect(jsonPath("$.price", equalTo(3.6)));
        }

        @Test
        @DisplayName("throws an exception if product does not exist")
        void shouldThrowAnException() throws Exception {
            long id = 4;
            when(productService.getProductById(anyLong()))
                    .thenThrow(new ResourceNotFoundException("Product with id: " + id + " not found!"));

            mockMvc.perform(
                    get(product_base_url + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message", equalTo("Product with id: " + id + " not found!")));
        }
    }

    @Test
    @DisplayName("adds a product")
    void shouldAddAProduct() throws Exception {
        when(productService.addProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(
                post(product_base_url + "/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.price", equalTo(4.5)));
    }

    @Nested
    @DisplayName("updates a product")
    class UpdateProduct {
        @Test
        @DisplayName("update a product given that it exists")
        void shouldUpdateAProduct() throws Exception {
            long id = 1L;
            ProductDto oldProductDto = modelMapper.map(productList.get(0), ProductDto.class);
            oldProductDto.setUnit("6 pieces");
            oldProductDto.setPrice(21.9);
            Product updatedProduct = modelMapper.map(oldProductDto, Product.class);
            when(productService.updateProduct(anyLong(), any(Product.class))).thenReturn(updatedProduct);

            mockMvc.perform(
                    put(product_base_url + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(oldProductDto))
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.price", equalTo(21.9)))
                    .andExpect(jsonPath("$.unit", equalTo("6 pieces")));
        }

        @Test
        @DisplayName("throws an exception given that product does not exist")
        void shouldThrowAnException() throws Exception {
            long id = 5;
            when(productService.updateProduct(anyLong(), any(Product.class)))
                    .thenThrow(new ResourceNotFoundException("Product with id: " + id + " not found!"));

            mockMvc.perform(
                    put(product_base_url + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(productDto))
            )
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message", equalTo("Product with id: " + id + " not found!")));
        }
    }

    @Nested
    @DisplayName("removes a product")
    class RemoveProduct {
        @Test
        @DisplayName("removes product given it exists")
        void shouldRemoveProduct() throws Exception {
            long id = 2;
            doNothing().when(productService).removeProduct(anyLong());

            mockMvc.perform(
                    delete(product_base_url + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("throws an exception given that product does not exits")
        void shouldThrowAnException() throws Exception {
            long id = 4;
            doThrow(new ResourceNotFoundException("Product with id: " + id + " not found!"))
                    .when(productService).removeProduct(anyLong());

            mockMvc.perform(
                    delete(product_base_url + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message", equalTo("Product with id: " + id + " not found!")));
        }
    }
}
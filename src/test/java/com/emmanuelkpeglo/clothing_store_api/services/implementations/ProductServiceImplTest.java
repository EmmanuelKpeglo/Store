package com.emmanuelkpeglo.clothing_store_api.services.implementations;

import com.emmanuelkpeglo.clothing_store_api.dao.ProductRepository;
import com.emmanuelkpeglo.clothing_store_api.dao.generic.GenericRepository;
import com.emmanuelkpeglo.clothing_store_api.exceptions.ResourceNotFoundException;
import com.emmanuelkpeglo.clothing_store_api.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    List<Product> productList;
    Product product;

    @Mock
    GenericRepository<Product> productRepository;

    @Autowired
    @InjectMocks
    ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productList = List.of(
                new Product(1L, "T -shirt", "3 pieces", 8.7),
                new Product(2L, "Socks", "2 pairs", 3.6)
        );

        product = new Product(3L, "Wrist Watch", "1 piece", 4.5);
    }

    @Test
    @DisplayName("adds a new product")
    void shouldAddAProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        assertThat(productService.save(product)).returns(3L, Product::getId);
        assertThat(productService.save(product)).returns(4.5, Product::getPrice);
    }

    @Nested
    @DisplayName("update a Product")
    class UpdateProduct {
        @Test
        @DisplayName("updates a product given it exists")
        void shouldUpdateProductIfExists() {
            long id = 2;
            when(productRepository.findById(anyLong())).thenReturn(Optional.of(productList.get(1)));
            when(productRepository.save(any(Product.class))).thenReturn(productList.get(1));

            Product productUpdate = new Product();
            productUpdate.setUnit("1 pair");
            productUpdate.setPrice(2.5);

            assertThat(productService.update(id, productUpdate)).returns(id, Product::getId);
            assertThat(productService.update(id, productUpdate)).returns("1 pair", Product::getUnit);
        }

        @Test
        @DisplayName("throws an exception given that product does not exist")
        void shouldThrowAnException() {
            long id = 4;
            when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

            Product product = new Product();
            product.setUnit("1 pair");
            product.setPrice(2.5);

            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
                    () -> productService.update(id, product));
            assertThat(thrown.getMessage()).isEqualTo("Resource with id: " + id + " not found!");
        }
    }

    @Nested
    @DisplayName("gets all products")
    class GetProducts {
        @Test
        @DisplayName("returns an empty list given that there's no product")
        void shouldReturnAnEmptyListOfProducts() {
            when(productRepository.findAll()).thenReturn(new ArrayList<Product>());
            List<Product> products = productService.findAll();

            assertThat(products.size()).isZero();
        }

        @Test
        @DisplayName("returns a list of two products")
        void shouldReturnAListOfTwoProducts() {
            when(productRepository.findAll()).thenReturn(productList);
            List<Product> products = productService.findAll();

            assertThat(products.size()).isEqualTo(2);
            assertThat(products).isEqualTo(productList);
            assertThat(products.get(1)).returns(3.6, Product::getPrice);
        }
    }

    @Nested
    @DisplayName("gets a product by id")
    class GetProductById {
        @Test
        @DisplayName("returns a product given that it exists")
        void shouldReturnAProductIfItExists() {
            long id = 1;
            when(productRepository.findById(anyLong())).thenReturn(Optional.of(productList.get(0)));

            assertThat(productService.findById(id)).isEqualTo(productList.get(0));
            assertThat(productService.findById(id)).returns(8.7, Product::getPrice);
        }

        @Test
        @DisplayName("throws an exception if product does not exist")
        void shouldThrowAnExceptionIfProductDoesNotExist() {
            long id = 5;
            when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
                    () -> productService.findById(id));

            assertThat(thrown.getMessage()).isEqualTo("Resource with id: " + id + " not found!");
        }
    }

    @Nested
    @DisplayName("removes a product")
    class RemoveProduct {
        @Test
        @DisplayName("removes a product if product exists")
        void shouldRemoveProductIfExists() {
            long id = 3;
            when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
            productService.delete(id);
            productService.delete(id);
            productService.delete(id);

            verify(productRepository, times(3)).delete(product);
        }

        @Test
        @DisplayName("throws an exception given that product does not exist")
        void shouldThrowAnExceptionIfProductDoesNotExist() {
            long id = 7;
            when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
                    () -> productService.delete(id));

            assertThat(thrown.getMessage()).isEqualTo("Resource with id: " + id + " not found!");
        }
    }
}
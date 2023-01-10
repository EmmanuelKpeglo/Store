package com.emmanuelkpeglo.clothing_store_api.services.implementations;

import com.emmanuelkpeglo.clothing_store_api.dao.OrderDetailRepository;
import com.emmanuelkpeglo.clothing_store_api.exceptions.ResourceNotFoundException;
import com.emmanuelkpeglo.clothing_store_api.models.Customer;
import com.emmanuelkpeglo.clothing_store_api.models.Order;
import com.emmanuelkpeglo.clothing_store_api.models.OrderDetail;
import com.emmanuelkpeglo.clothing_store_api.models.Product;
import com.emmanuelkpeglo.clothing_store_api.models.base.BaseEntity;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderDetailDetailServiceImplTest {
    List<OrderDetail> orderDetailList;
    OrderDetail orderDetail;

    @Mock
    OrderDetailRepository orderDetailRepository;

    @Autowired
    @InjectMocks
    OrderDetailServiceImpl orderDetailService;

    Order order1;
    Order order2;
    Order order3;

    Product product1;
    Product product2;
    Product product3;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product1.setId(1L);

        product2 = new Product();
        product2.setId(2L);

        product3 = new Product();
        product3.setId(3L);

        order1 = new Order();
        order1.setId(1L);

        order2 = new Order();
        order2.setId(2L);

        order3 = new Order();
        order3.setId(3L);

        orderDetailList = List.of(
                new OrderDetail(1L, 1, order1, product1),
                new OrderDetail(2L, 2, order2, product2)
        );

        orderDetail = new OrderDetail(3L, 3, order3, product3);
    }

    @Test
    @DisplayName("adds a new orderDetail")
    void shouldAddAOrderDetail() {
        when(orderDetailRepository.save(any(OrderDetail.class))).thenReturn(orderDetail);

        assertThat(orderDetailService.save(orderDetail)).returns(3L, OrderDetail::getId);
        assertThat(orderDetailService.save(orderDetail)).returns(3L, anOrderDetail -> anOrderDetail.getOrder().getId());
        assertThat(orderDetailService.save(orderDetail)).returns(3L, anOrderDetail -> anOrderDetail.getProduct().getId());
        assertThat(orderDetailService.save(orderDetail)).returns(3, OrderDetail::getQuantity);
    }

    @Nested
    @DisplayName("gets all orderDetails")
    class GetOrderDetails {
        @Test
        @DisplayName("returns an empty list given that there's no orderDetail")
        void shouldReturnAnEmptyListOfOrderDetails() {
            when(orderDetailRepository.findAll()).thenReturn(new ArrayList<OrderDetail>());
            List<OrderDetail> orderDetails = orderDetailService.findAll();

            assertThat(orderDetails.size()).isZero();
        }

        @Test
        @DisplayName("returns a list of two orderDetails")
        void shouldReturnAListOfTwoOrderDetails() {
            when(orderDetailRepository.findAll()).thenReturn(orderDetailList);
            List<OrderDetail> orderDetails = orderDetailService.findAll();

            assertThat(orderDetails.size()).isEqualTo(2);
            assertThat(orderDetails).isEqualTo(orderDetailList);
            assertThat(orderDetails.get(1)).returns(2L, anOrderDetail -> anOrderDetail.getOrder().getId());
            assertThat(orderDetails.get(0)).returns(1L, anOrderDetail -> anOrderDetail.getProduct().getId());
            assertThat(orderDetails.get(0)).returns(1, OrderDetail::getQuantity);
        }
    }

    @Nested
    @DisplayName("gets a orderDetail by id")
    class GetOrderDetailById {
        @Test
        @DisplayName("returns a orderDetail given that it exists")
        void shouldReturnAOrderDetailIfItExists() {
            long id = 1;
            when(orderDetailRepository.findById(anyLong())).thenReturn(Optional.of(orderDetailList.get(0)));

            assertThat(orderDetailService.findById(id)).isEqualTo(orderDetailList.get(0));
            assertThat(orderDetailService.findById(id)).returns(1L, anOrderDetail -> anOrderDetail.getOrder().getId());
            assertThat(orderDetailService.findById(id)).returns(1L, anOrderDetail -> anOrderDetail.getProduct().getId());
            assertThat(orderDetailService.findById(id)).returns(1, OrderDetail::getQuantity);
        }

        @Test
        @DisplayName("throws an exception if orderDetail does not exist")
        void shouldThrowAnExceptionIfOrderDetailDoesNotExist() {
            long id = 5;
            when(orderDetailRepository.findById(anyLong())).thenReturn(Optional.empty());

            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
                    () -> orderDetailService.findById(id));

            assertThat(thrown.getMessage()).isEqualTo("Resource with id: " + id + " not found!");
        }
    }

    @Nested
    @DisplayName("removes a orderDetail")
    class RemoveOrderDetail {
        @Test
        @DisplayName("removes a orderDetail if orderDetail exists")
        void shouldRemoveOrderDetailIfExists() {
            long id = 3;
            when(orderDetailRepository.findById(anyLong())).thenReturn(Optional.of(orderDetail));
            orderDetailService.delete(id);
            orderDetailService.delete(id);
            orderDetailService.delete(id);

            verify(orderDetailRepository, times(3)).delete(orderDetail);
        }

        @Test
        @DisplayName("throws an exception given that orderDetail does not exist")
        void shouldThrowAnExceptionIfOrderDetailDoesNotExist() {
            long id = 7;
            when(orderDetailRepository.findById(anyLong())).thenReturn(Optional.empty());

            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
                    () -> orderDetailService.delete(id));

            assertThat(thrown.getMessage()).isEqualTo("Resource with id: " + id + " not found!");
        }
    }

    @Test
    @DisplayName("returns a list of 3 orderDetails depending on product ID")
    void shouldReturnOrderDetailsByProductID() {
        List<OrderDetail> orderDetails = List.of(
                new OrderDetail(1L, 1, order1, product1),
                new OrderDetail(1L, 3, order2, product1),
                new OrderDetail(1L, 9, order3, product1)
        );

        when(orderDetailRepository.findByProductId(anyLong())).thenReturn(orderDetails);

        assertThat(orderDetailService.getByProductId(1)).size().isEqualTo(3);
    }

    @Test
    @DisplayName("returns a list of 3 orderDetails depending on order ID")
    void shouldReturnOrderDetailsByOrderID() {
        List<OrderDetail> orderDetails = List.of(
                new OrderDetail(1L, 1, order1, product1),
                new OrderDetail(1L, 3, order1, product2),
                new OrderDetail(1L, 9, order1, product3)
        );

        when(orderDetailRepository.findByOrderId(anyLong())).thenReturn(orderDetails);

        assertThat(orderDetailService.getByOrderId(1)).size().isEqualTo(3);
    }
}
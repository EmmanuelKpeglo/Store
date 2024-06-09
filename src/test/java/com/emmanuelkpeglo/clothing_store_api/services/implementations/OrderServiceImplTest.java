package com.emmanuelkpeglo.clothing_store_api.services.implementations;

import com.emmanuelkpeglo.clothing_store_api.dao.OrderRepository;
import com.emmanuelkpeglo.clothing_store_api.dao.generic.GenericRepository;
import com.emmanuelkpeglo.clothing_store_api.exceptions.ResourceNotFoundException;
import com.emmanuelkpeglo.clothing_store_api.models.Customer;
import com.emmanuelkpeglo.clothing_store_api.models.Order;
import com.emmanuelkpeglo.clothing_store_api.models.Order;
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

import java.time.LocalDateTime;
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
class OrderServiceImplTest {
    List<Order> orderList;
    Order order;

    @Mock
    OrderRepository orderRepository;

    @Autowired
    @InjectMocks
    OrderServiceImpl orderService;

    Customer customer1;
    Customer customer2;
    Customer customer3;

    @BeforeEach
    void setUp() {
        customer1 = new Customer();
        customer1.setId(3L);

        customer2 = new Customer();
        customer2.setId(6L);

        customer3 = new Customer();
        customer3.setId(9L);

        orderList = List.of(
                new Order(1L, customer1),
                new Order(2L, customer2)
        );

        order = new Order(3L, customer3);
    }

    @Test
    @DisplayName("adds a new order")
    void shouldAddAOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        assertThat(orderService.save(order)).returns(3L, Order::getId);
        assertThat(orderService.save(order)).returns(9L, anOrder -> anOrder.getCustomer().getId());
    }

    @Nested
    @DisplayName("gets all orders")
    class GetOrders {
        @Test
        @DisplayName("returns an empty list given that there's no order")
        void shouldReturnAnEmptyListOfOrders() {
            when(orderRepository.findAll()).thenReturn(new ArrayList<Order>());
            List<Order> orders = orderService.findAll();

            assertThat(orders.size()).isZero();
        }

        @Test
        @DisplayName("returns a list of two orders")
        void shouldReturnAListOfTwoOrders() {
            when(orderRepository.findAll()).thenReturn(orderList);
            List<Order> orders = orderService.findAll();

            assertThat(orders.size()).isEqualTo(2);
            assertThat(orders).isEqualTo(orderList);
            assertThat(orders.get(1)).returns(6L, anOrder -> anOrder.getCustomer().getId());
        }
    }

    @Nested
    @DisplayName("gets a order by id")
    class GetOrderById {
        @Test
        @DisplayName("returns a order given that it exists")
        void shouldReturnAOrderIfItExists() {
            long id = 1;
            when(orderRepository.findById(anyLong())).thenReturn(Optional.of(orderList.get(0)));

            assertThat(orderService.findById(id)).isEqualTo(orderList.get(0));
            assertThat(orderService.findById(id)).returns(3L, anOrder -> anOrder.getCustomer().getId());
        }

        @Test
        @DisplayName("throws an exception if order does not exist")
        void shouldThrowAnExceptionIfOrderDoesNotExist() {
            long id = 5;
            when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
                    () -> orderService.findById(id));

            assertThat(thrown.getMessage()).isEqualTo("Resource with id: " + id + " not found!");
        }
    }

    @Nested
    @DisplayName("removes a order")
    class RemoveOrder {
        @Test
        @DisplayName("removes a order if order exists")
        void shouldRemoveOrderIfExists() {
            long id = 3;
            when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
            orderService.delete(id);
            orderService.delete(id);
            orderService.delete(id);

            verify(orderRepository, times(3)).delete(order);
        }

        @Test
        @DisplayName("throws an exception given that order does not exist")
        void shouldThrowAnExceptionIfOrderDoesNotExist() {
            long id = 7;
            when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

            ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
                    () -> orderService.delete(id));

            assertThat(thrown.getMessage()).isEqualTo("Resource with id: " + id + " not found!");
        }
    }

    @Test
    @DisplayName("returns a list of 3 orders depending on customer ID")
    void shouldReturnOrdersByCustomerID() {
        List<Order> orders = List.of(
                new Order(4L, customer3),
                new Order(8L, customer3),
                new Order(6L, customer3)
        );

        when(orderRepository.findByCustomerId(anyLong())).thenReturn(orders);

        assertThat(orderService.getByCustomerId(9)).size().isEqualTo(3);
    }
}
package com.emmanuelkpeglo.clothing_store_api.controllers;

import com.emmanuelkpeglo.clothing_store_api.dao.CustomerRepository;
import com.emmanuelkpeglo.clothing_store_api.dtos.OrderProduct;
import com.emmanuelkpeglo.clothing_store_api.dtos.OrderRequest;
import com.emmanuelkpeglo.clothing_store_api.models.Customer;
import com.emmanuelkpeglo.clothing_store_api.models.Order;
import com.emmanuelkpeglo.clothing_store_api.models.OrderDetail;
import com.emmanuelkpeglo.clothing_store_api.models.Product;
import com.emmanuelkpeglo.clothing_store_api.services.CustomerService;
import com.emmanuelkpeglo.clothing_store_api.services.OrderDetailService;
import com.emmanuelkpeglo.clothing_store_api.services.OrderService;
import com.emmanuelkpeglo.clothing_store_api.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private final ProductService productService;

    public OrderController(OrderService orderService, OrderDetailService orderDetailService, CustomerService customerService, ProductService productService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
        this.customerService = customerService;
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<List<OrderDetail>> addOrder(@RequestBody OrderRequest orderRequest) {
        Customer customer = customerService.findById(orderRequest.getCustomerID());

        Order order = orderService.save(new Order(customer));

        List<OrderProduct> productList = orderRequest.getProductList();

        List<OrderDetail> orderDetails = productList.stream()
                .map(orderProduct -> {
                    Product product = productService.findById(orderProduct.getProductId());
                    return orderDetailService.save(new OrderDetail(order, product, orderProduct.getQuantity()));
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderDetail>> getOrder(@PathVariable long id) {
        List<OrderDetail> orderDetails = orderDetailService.getByOrderId(id);

        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable long id) {
        List<OrderDetail> orderDetails = orderDetailService.getByOrderId(id);

        orderDetails.forEach(orderDetailService::deleteOrderDetail);

        orderService.delete(id);

        return new ResponseEntity<>("Resource with id: " + id + " removed!", HttpStatus.OK);
    }
}

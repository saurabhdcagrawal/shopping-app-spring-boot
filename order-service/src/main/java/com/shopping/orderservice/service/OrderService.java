package com.shopping.orderservice.service;

import com.shopping.orderservice.dto.OrderRequest;
import com.shopping.orderservice.model.Order;

import java.util.UUID;
//58.20
public class OrderService {
    public void placeOrder(OrderRequest orderRequest) {
        Order order= new Order();
        order.setOrderNumber(UUID.randomUUID().toString());


    }

}

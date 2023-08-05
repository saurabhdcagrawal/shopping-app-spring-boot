package com.shopping.orderservice.service;

import com.shopping.orderservice.dto.OrderLineItemsDto;
import com.shopping.orderservice.dto.OrderRequest;
import com.shopping.orderservice.model.Order;
import com.shopping.orderservice.model.OrderLineItems;
import com.shopping.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;
@RequiredArgsConstructor
//58.20
@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest) {
        Order order= new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItemsList(orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToModel).collect(Collectors.toList()));
        orderRepository.save(order);
    }
    public OrderLineItems mapToModel(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems= OrderLineItems.builder().price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity()).price(orderLineItemsDto.getPrice()).build();
        return  orderLineItems;

    }

}

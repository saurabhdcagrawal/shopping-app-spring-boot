package com.shopping.orderservice.service;

import com.shopping.orderservice.dto.InventoryResponse;
import com.shopping.orderservice.dto.OrderLineItemsDto;
import com.shopping.orderservice.dto.OrderRequest;
import com.shopping.orderservice.event.OrderPlacedEvent;
import com.shopping.orderservice.model.Order;
import com.shopping.orderservice.model.OrderLineItems;
import com.shopping.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@RequiredArgsConstructor
//58.20
@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;
    public String placeOrder(OrderRequest orderRequest) {
        Order order= new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItemsList(orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToModel).collect(Collectors.toList()));
        List<String> skuCodes=order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).collect(Collectors.toList());
        //check if product exists in inventory
        InventoryResponse[] inventoryResponses=webClientBuilder.build().get().uri("http://inventory-service/api/inventory",
                uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve().bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductsInStock=Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
        if(allProductsInStock) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));
            return "Order Placed Successfully";
        }
        else
            throw new IllegalArgumentException("Product is not in stock, please try again later");
    }
    public OrderLineItems mapToModel(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems= OrderLineItems.builder().price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity()).skuCode(orderLineItemsDto.getSkuCode()).build();
        return  orderLineItems;

    }

}

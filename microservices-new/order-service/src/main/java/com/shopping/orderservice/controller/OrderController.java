package com.shopping.orderservice.controller;

import com.shopping.orderservice.dto.OrderRequest;
import com.shopping.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/order")
public class OrderController {
    @Autowired
    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name="inventory", fallbackMethod ="fallbackMethod" )
    //@TimeLimiter(name="inventory")
    @Retry(name="inventory")
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        return orderService.placeOrder (orderRequest);
    }
    public String fallbackMethod(OrderRequest orderRequest, RuntimeException exception){
        return "Oops something went wrong, please run after sometime";
    }
    /*public CompletableFuture<String>  placeOrder(@RequestBody OrderRequest orderRequest){
         return CompletableFuture.supplyAsync(()->orderService.placeOrder (orderRequest));
    }
    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException exception){
        return CompletableFuture.supplyAsync(()->"Oops something went wrong, please run after sometime");
    }*/
}

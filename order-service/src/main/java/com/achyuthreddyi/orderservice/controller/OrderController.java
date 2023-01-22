package com.achyuthreddyi.orderservice.controller;

import com.achyuthreddyi.orderservice.dto.OrderRequestDto;
import com.achyuthreddyi.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequestDto orderRequestDto){
        orderService.placeOrder(orderRequestDto);
        return "Order Places successfully";
    }

    @GetMapping
    public String getAllOrders(){
        return  "Will return all the orders";
    }
}

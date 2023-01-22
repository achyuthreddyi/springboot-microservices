package com.achyuthreddyi.orderservice.service;

import com.achyuthreddyi.orderservice.dto.InventoryResponse;
import com.achyuthreddyi.orderservice.dto.OrderLineItemsDto;
import com.achyuthreddyi.orderservice.dto.OrderRequestDto;
import com.achyuthreddyi.orderservice.model.Order;
import com.achyuthreddyi.orderservice.model.OrderLineItems;
import com.achyuthreddyi.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequestDto orderRequestDto){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequestDto.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(orderLineItem -> orderLineItem.getSkuCode())
                .toList();

        // Call inventory service if product is in stock
        // In order to read the data from the webclient response
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://localhost:8082/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock){
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock");
        }


    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();

        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;
    }
}

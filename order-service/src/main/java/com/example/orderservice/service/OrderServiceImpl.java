package com.example.orderservice.service;

import com.example.orderservice.exception.CustomException;
import com.example.orderservice.entity.Order;
import com.example.orderservice.model.OrderRequest;
import com.example.orderservice.model.OrderResponse;
import com.example.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;

    @Override
    public List<OrderResponse> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(order -> {
                    return mappingOrderToOrderResponse(order);
                }).collect(Collectors.toList());
    }

    @Override
    public OrderResponse getById(long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new CustomException(
                "Order with given id " + id + " Not Found.",
                "ORDER_NOT_FOUND",
                404));
        return mappingOrderToOrderResponse(order);
    }

    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        // Check available product => Quantity >= Product Quantity
        // Place Order
        // Payment service -> Create payment
            // Reduce Quantity -> succes
            // Failed payment
        Order order = Order.builder()
                .amount(orderRequest.getAmount())
                .quantity(orderRequest.getQuantity())
                .productId(orderRequest.getProductId())
                .status("CREATED")
                .date(Instant.now())
                .build();
        Order res = orderRepository.save(order);
        return mappingOrderToOrderResponse(res);
    }

    public OrderResponse mappingOrderToOrderResponse(Order order){
        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(order,orderResponse);
        return orderResponse;
    }
}

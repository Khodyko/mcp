package com.holic.java.mcp.service;

import com.holic.java.mcp.dto.OrderDto;
import java.util.List;

/**
 * Сервис для работы с заказами (интерфейс, реализация позже).
 */
public interface OrderService {
    OrderDto createOrder(OrderDto order);
    OrderDto getOrderById(Long id);
    List<OrderDto> getOrdersByCustomerId(Long customerId);
    OrderDto updateOrder(Long id, OrderDto order);
}



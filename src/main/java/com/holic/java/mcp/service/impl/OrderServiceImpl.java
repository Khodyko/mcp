package com.holic.java.mcp.service.impl;

import com.holic.java.mcp.dto.OrderDto;
import com.holic.java.mcp.mapper.OrderMapper;
import com.holic.java.mcp.model.OrderEntity;
import com.holic.java.mcp.repository.OrderRepository;
import com.holic.java.mcp.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto createOrder(OrderDto order) {
        OrderEntity entity = orderMapper.toEntity(order);
        OrderEntity saved = orderRepository.save(entity);
        return orderMapper.toDto(saved);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        return orderRepository.findById(id).map(orderMapper::toDto).orElse(null);
    }

    @Override
    public List<OrderDto> getOrdersByCustomerId(Long customerId) {
        List<OrderEntity> entities = orderRepository.findByCustomerId(customerId);
        return entities.stream().map(orderMapper::toDto).collect(Collectors.toList());
    }
}

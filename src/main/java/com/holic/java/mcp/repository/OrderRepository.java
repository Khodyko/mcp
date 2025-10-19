package com.holic.java.mcp.repository;

import com.holic.java.mcp.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCustomerId(Long customerId);
    List<OrderEntity> findByProductId(Long productId);
    List<OrderEntity> findByFulfilled(Boolean fulfilled);
}

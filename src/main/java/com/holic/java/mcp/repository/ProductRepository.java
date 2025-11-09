package com.holic.java.mcp.repository;

import com.holic.java.mcp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product getFirstProductsByNameIgnoreCase(String productName);
}

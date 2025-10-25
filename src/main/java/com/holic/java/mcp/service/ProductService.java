package com.holic.java.mcp.service;

import com.holic.java.mcp.dto.ProductDto;
import java.util.List;

/**
 * Сервис для работы с продуктами (интерфейс, реализация позже).
 */
public interface ProductService {
    List<ProductDto> createProducts(List<ProductDto> products);
    ProductDto getProductById(Long id);
    void deleteProduct(Long id);
}



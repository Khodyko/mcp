package com.holic.java.mcp.service.impl;

import com.holic.java.mcp.dto.ProductDto;
import com.holic.java.mcp.mapper.ProductMapper;
import com.holic.java.mcp.model.Product;
import com.holic.java.mcp.repository.ProductRepository;
import com.holic.java.mcp.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        return productOpt.map(productMapper::toDto).orElse(null);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

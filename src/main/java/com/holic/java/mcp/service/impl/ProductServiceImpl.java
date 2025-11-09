package com.holic.java.mcp.service.impl;

import com.holic.java.mcp.dto.ProductDto;
import com.holic.java.mcp.dto.ProductVectorDto;
import com.holic.java.mcp.mapper.ProductMapper;
import com.holic.java.mcp.model.Product;
import com.holic.java.mcp.repository.ProductRepository;
import com.holic.java.mcp.repository.ProductVectorRepository;
import com.holic.java.mcp.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductVectorRepository productVectorRepository;

    @Override
    public List<ProductDto> createProducts(List<ProductDto> productDtos) {
        List<Product> products = productMapper.toEntity(productDtos);
        List<Product> saved = productRepository.saveAll(products);
        List<ProductVectorDto> vectorDtoList = productMapper.toVectorDtos(saved);
        productVectorRepository.saveVector(vectorDtoList);
        return productMapper.toDtos(saved);
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

    @Override
    public ProductDto getProductsByNameIgnoreCase(String productName) {
        Product product = productRepository.getFirstProductsByNameIgnoreCase(productName);
        return productMapper.toDto(product);
    }
}

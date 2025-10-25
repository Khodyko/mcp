package com.holic.java.mcp.agent.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.holic.java.mcp.dto.ProductDto;
import com.holic.java.mcp.model.Customer;
import com.holic.java.mcp.repository.CustomerRepository;
import com.holic.java.mcp.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * Агент для загрузки JSON-файлов из classpath и сохранения данных в базу.
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class DocumentLoaderAgent {

    private final ProductService productService;
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    @Tool(description = "load products to database using standard folder.", name = "productFromFolderLoader")
    public String loadProductsJsonFile() {
        Resource productsJson = new ClassPathResource("products-data.json");
        try (InputStream is = productsJson.getInputStream()) {
            List<ProductDto> products = objectMapper.readValue(is, new TypeReference<>() {
            });
            productService.createProducts(products);
            log.info("Loaded {} products from JSON", products.size());
            return "saved " + products.size() + " products";
        } catch (Exception e) {
            log.error("Failed to load products-data.json", e);
            return "error: " + e.getMessage();
        }
    }

    @Tool(description = "load customers to database using standard folder.", name = "customerFromFolderLoader")
    public String loadCustomerJsonFile() {
        Resource customerJson = new ClassPathResource("customer-data.json");
        try (InputStream is = customerJson.getInputStream()) {
            List<Customer> customers = objectMapper.readValue(is, new TypeReference<>() {
            });
            customerRepository.saveAll(customers);
            log.info("Loaded {} customers from JSON", customers.size());
            return "saved " + customers.size() + " customers";
        } catch (Exception e) {
            log.error("Failed to load customer-data.json", e);
            return "error: " + e.getMessage();
        }
    }

}

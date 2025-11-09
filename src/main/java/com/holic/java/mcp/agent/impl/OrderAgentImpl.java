package com.holic.java.mcp.agent.impl;

import com.holic.java.mcp.agent.OrderAgent;
import com.holic.java.mcp.dto.OrderDto;
import com.holic.java.mcp.dto.ProductDto;
import com.holic.java.mcp.service.OrderService;
import com.holic.java.mcp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.holic.java.mcp.constants.ToolsContextNames.CUSTOMER_ID;

/**
 * Маркерный класс агента, обрабатывающего запросы по заказам.
 */
@Service
@RequiredArgsConstructor
public class OrderAgentImpl implements OrderAgent {

    //Если имя одинаковое, то будет ошибка (по умолчанию имя метода)
    public static final String AGENT_NAME = "OrderAgent";

    private final OrderService orderService;
    private final ProductService productService;

    @Override
    @Tool(description = "creates product order by customer", name = AGENT_NAME)
    public String handle(@ToolParam(description = "product name") String productName,
                         @ToolParam(description = "count of products to order") Integer quantity,
                         ToolContext context) {
        Map<String, Object> map = context.getContext();
        ProductDto productDto = productService.getProductsByNameIgnoreCase(productName);
        if (productDto == null) {
            return "Product for creating order not found";
        }
        Integer customerId = (Integer) map.get(CUSTOMER_ID);
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(customerId);
        orderDto.setQuantity(quantity);
        orderDto.setProductId(productDto.getId());
        orderDto.setFulfilled(false);
        orderDto = orderService.createOrder(orderDto);
        return "Создан заказ: " + orderDto;
    }

    @Override
    public String getName() {
        return AGENT_NAME;
    }
}

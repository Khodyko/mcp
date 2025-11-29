package com.holic.java.mcp.agent.impl;

import com.holic.java.mcp.agent.SupportAgent;
import com.holic.java.mcp.repository.ProductVectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

/**
 * Маркерный класс агента, обрабатывающего запросы по продуктам.
 */
@Service
@RequiredArgsConstructor
public class ProductAgent implements SupportAgent {

    public static final String AGENT_NAME = "ProductAgent";
    private final ProductVectorRepository productVectorRepository;

    @Override
    @Tool(description = "returns products description by query", name = AGENT_NAME)
    public String handle(String query) {

        //здесь можно подготовить query (убрать всякие "дай, мне пожалуйста")
        return productVectorRepository.searchProductsByDescription(query, 2).toString();
    }

    @Override
    public String getName() {
        return AGENT_NAME;
    }
}

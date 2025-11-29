package com.holic.java.mcp.orchestrator.toolcallbackresolver;

import com.holic.java.mcp.agent.FallBackAgent;
import com.holic.java.mcp.agent.OrderAgent;
import com.holic.java.mcp.agent.impl.DocumentLoaderAgent;
import com.holic.java.mcp.agent.impl.ProductAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.ai.tool.metadata.ToolMetadata;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

import static com.holic.java.mcp.agent.impl.ProductAgent.AGENT_NAME;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimpleToolCallBackResolver {

    private final DocumentLoaderAgent documentLoaderAgent;
    private final ProductAgent productAgent;
    private final OrderAgent orderAgent;
    private final FallBackAgent fallBackAgent;

    public Object[] determineAllowedTools(String query) {
        String q = query.toLowerCase();
        if (q.contains("заказ") || q.contains("order") || q.contains("купить")) {
            // НИКОГДА не включаем загрузчики файлов в пользовательский заказ
            return Stream.of(orderAgent, productAgent).toArray();
        }
        if (q.contains("загру") || q.contains("импорт") || q.contains("load")) {
            return Stream.of(documentLoaderAgent).toArray();
        }
        // По умолчанию — только просмотр продуктов
        return Stream.of(productAgent).toArray();
    }

    //вызов по классу
    public Object[] getAllTools(String query) {
        return Stream.of(orderAgent, documentLoaderAgent, productAgent).toArray();
    }

    public ToolCallback[] getAllToolCallbackByClass(String query) {
        return ToolCallbacks.from(productAgent,  productAgent,  orderAgent);
    }

    public ToolCallback[] getToolCalBackByFunctionCall(String query) {
        ToolCallback toolCallback = FunctionToolCallback
                .builder(AGENT_NAME, productAgent::handle)
                .description("returns products description by query")
                .inputType(String.class)
                .toolMetadata(ToolMetadata.builder().returnDirect(true).build())
                .build();

        return ToolCallbacks.from(toolCallback);
    }


}

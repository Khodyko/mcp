package com.holic.java.mcp.orchestrator.toolcallbackresolver;

import com.holic.java.mcp.agent.FallBackAgent;
import com.holic.java.mcp.agent.OrderAgent;
import com.holic.java.mcp.agent.impl.DocumentLoaderAgent;
import com.holic.java.mcp.agent.impl.ProductAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class AiBasedToolCalBackResolver {

    @Qualifier("openAiChatClient")
    private final ChatClient openAiChatClient;
    private final String findToolCall = """
            Определи с чем связан запрос
            1. Заказ продукта пользователем
            2. Настройка базы данных
            3. Ничего
            Верни только цифру 1, 2, или 3.
            """;

    private final DocumentLoaderAgent documentLoaderAgent;
    private final ProductAgent productAgent;
    private final OrderAgent orderAgent;
    private final FallBackAgent fallBackAgent;

    public  Object[]  determineAllowedTools(String query) {
        String response = openAiChatClient
                .prompt(query)
                .user(findToolCall)
                .call()
                .content();

        if (response != null && response.contains("1")) {
            return Stream.of(orderAgent, productAgent).toArray();
        } else if (response != null && response.contains("2")) {
            return Stream.of(documentLoaderAgent).toArray();
        } else {
            return  Stream.empty().toArray();
        }
    }

}

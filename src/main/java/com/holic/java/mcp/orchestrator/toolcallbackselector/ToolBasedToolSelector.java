package com.holic.java.mcp.orchestrator.toolcallbackselector;

import com.holic.java.mcp.agent.FallBackAgent;
import com.holic.java.mcp.agent.OrderAgent;
import com.holic.java.mcp.agent.impl.DocumentLoaderAgent;
import com.holic.java.mcp.agent.impl.FindToolNameAgent;
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
public class ToolBasedToolSelector {

    @Qualifier("openAiChatClient")
    private final ChatClient openAiChatClient;
    private final FindToolNameAgent findToolNameAgent;
    private final DocumentLoaderAgent documentLoaderAgent;
    private final ProductAgent productAgent;
    private final OrderAgent orderAgent;
    private final FallBackAgent fallBackAgent;

    public  Object[]  determineAllowedTools(String query) {
        String response = openAiChatClient
                .prompt(query)
                .tools(findToolNameAgent)
                .user("Верни только ответ из агента")
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

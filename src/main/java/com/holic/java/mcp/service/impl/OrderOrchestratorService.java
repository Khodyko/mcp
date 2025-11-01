package com.holic.java.mcp.service.impl;

import com.holic.java.mcp.agent.OrderAgent;
import com.holic.java.mcp.agent.chain.ChainAgent;
import com.holic.java.mcp.agent.chain.impl.MainChainAgent;
import com.holic.java.mcp.agent.impl.DocumentLoaderAgent;
import com.holic.java.mcp.agent.impl.ProductAgent;
import com.holic.java.mcp.service.OrchestratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.holic.java.mcp.constants.ToolsContextNames.CUSTOMER_ID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderOrchestratorService implements OrchestratorService {

    private final DocumentLoaderAgent documentLoaderAgent;
    private final ProductAgent productAgent;
    private final OrderAgent orderAgent;
    @Qualifier("openAiChatClient")
    private final ChatClient openAiChatClient;

    private final MainChainAgent mainChainAgent;
    @Qualifier("chainYellowAgent")
    private final ChainAgent chainYellowAgent;
    @Qualifier("chainRedAgent")
    private final ChainAgent chainRedAgent;

    @Override
    public String orchestrate(Integer userId, String query) {
        Map<String, Object> toolContext = Map.of(CUSTOMER_ID, userId);

        ChatOptions chatOptions = ToolCallingChatOptions.builder()
                .internalToolExecutionEnabled(false)
                .toolContext(toolContext)
                .build();
       String query2= query.replace("{", "\\{").replace("}", "\\}");
        Prompt prompt = new Prompt(query2);
        prompt.augmentSystemMessage("вызывай tool только, если это явно указано в запросе.");

        String response = openAiChatClient
                .prompt(prompt)
//                .user(userId)
                .toolContext(toolContext)
                .tools(
                        documentLoaderAgent,
//                        productAgent,
                        orderAgent
                )
                .call()
                .content();

        log.debug("response from openAiChatClient: {}", response);

        return response;
    }



    @Override
    public String orchestrateChain(Integer userId, String query) {
        Map<String, Object> toolContext = Map.of(CUSTOMER_ID, userId);

        String query2= query.replace("{", "\\{").replace("}", "\\}");
        Prompt prompt = new Prompt(query2);
        prompt.augmentSystemMessage("вызывай tool только, если это явно указано в запросе.");

        String response = openAiChatClient
                .prompt(prompt)
//                .user(userId)
                .toolContext(toolContext)
                .tools(
                        mainChainAgent,
                        chainRedAgent,
                        chainYellowAgent
                )
                .call()
                .content();

        log.debug("response from openAiChatClient: {}", response);

        return response;
    }

}

package com.holic.java.mcp.orchestrator.impl;

import com.holic.java.mcp.agent.chain.divided.ChainAgent;
import com.holic.java.mcp.agent.chain.divided.impl.MainChainAgent;
import com.holic.java.mcp.orchestrator.ChainOrchestrator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.holic.java.mcp.constants.ToolsContextNames.CUSTOMER_ID;

@Service("colorChainOrchestrator")
@RequiredArgsConstructor
@Slf4j
public class ColorChainOrchestrator implements ChainOrchestrator {

    private final MainChainAgent mainChainAgent;
    private final ChatClient openAiChatClient;
    @Qualifier("chainYellowAgent")
    private final ChainAgent chainYellowAgent;
    @Qualifier("chainRedAgent")
    private final ChainAgent chainRedAgent;


    @Override
    public String orchestrateChain(Integer userId, String query) {
        Map<String, Object> toolContext = Map.of(CUSTOMER_ID, userId);

        String query2 = query.replace("{", "\\{").replace("}", "\\}");
        Prompt prompt = new Prompt(query2);
        prompt.augmentSystemMessage("вызывай tool только, если это явно указано в запросе.");

        String response = openAiChatClient
                .prompt(prompt)
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

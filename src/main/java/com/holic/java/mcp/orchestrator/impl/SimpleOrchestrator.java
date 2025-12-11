package com.holic.java.mcp.orchestrator.impl;

import com.holic.java.mcp.orchestrator.OrchestratorService;
import com.holic.java.mcp.orchestrator.toolcallbackselector.SimpleToolSelector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
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
public class SimpleOrchestrator implements OrchestratorService {


    @Qualifier("openAiChatClient")
    private final ChatClient openAiChatClient;
    private final SimpleToolSelector simpleToolSelector;

    @Override
    public String orchestrate(Integer userId, String query) {
        Map<String, Object> toolContext = Map.of(CUSTOMER_ID, userId);
        //проблема с кавычками
        String query2 = query.replace("{", "\\{")
                .replace("}", "\\}");
//        ChatOptions chatOptions = ToolCallingChatOptions.builder()
//                .internalToolExecutionEnabled(false)
//                .build();
//        Prompt prompt = new Prompt(query2, chatOptions);
        Prompt prompt = new Prompt(query2);
        prompt.augmentSystemMessage(
                "вызывай tool только, если это явно указано в запросе.");

        String response = openAiChatClient
                .prompt(prompt)
                .toolContext(toolContext)
                .tools(
                        //documentLoaderAgent,  productAgent,  orderAgent, fallBackAgent
                        simpleToolSelector.getAllTools(query)
                )
                .advisors(SimpleLoggerAdvisor.builder().order(-1).build())
                .call()
                .content();
        log.debug("response from openAiChatClient: {}", response);

        return response;
    }

    @Override
    public String orchestrateThroughtChatOptions(Integer userId, String query) {
        Map<String, Object> toolContext = Map.of(CUSTOMER_ID, userId);

        ChatOptions chatOptions = ToolCallingChatOptions.builder()
                .internalToolExecutionEnabled(false)
                    // takes list or array
                .toolCallbacks(simpleToolSelector.getAllToolCallbackByClass(query))
                .toolContext(toolContext)
                .build();

        String query2 = query.replace("{", "\\{").replace("}", "\\}");
        Prompt prompt = new Prompt(query2, chatOptions);
        prompt.augmentSystemMessage("вызывай tool только, если это явно указано в запросе.");

        String response = openAiChatClient
                .prompt(prompt)
                .user(String.valueOf(userId))
                .advisors(SimpleLoggerAdvisor.builder().order(-1).build())
                .call()
                .content();
        log.debug("response from openAiChatClient: {}", response);

        return response;
    }
}

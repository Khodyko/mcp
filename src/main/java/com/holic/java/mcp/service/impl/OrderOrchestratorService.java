package com.holic.java.mcp.service.impl;

import com.holic.java.mcp.agent.impl.DocumentLoaderAgent;
import com.holic.java.mcp.service.OrchestratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderOrchestratorService implements OrchestratorService {

    private final DocumentLoaderAgent documentLoaderAgent;
    @Qualifier("openAiChatClient")
    private final ChatClient openAiChatClient;

    @Override
    public String orchestrate(String chatId, String query) {

        String response = openAiChatClient
                .prompt(query)
                .user(chatId)
                .tools(documentLoaderAgent)
                .call()
                .content();

        log.debug("response from openAiChatClient: {}", response);

        return response;
    }
}

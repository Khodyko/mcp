package com.holic.java.mcp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Контроллер поддержки — проксирует запросы к ChatClient Spring AI и хранит
 * историю диалогов в JDBC-репозитории.
 */
@RestController
@RequestMapping("/ai/support")
@RequiredArgsConstructor
public class SupportAssistantController {

    @Qualifier("llamaChatClient")
    private final ChatClient llamaClient;

    @Qualifier("openAiChatClient")
    private final ChatClient openAiClient;
    private final Map<String, PromptChatMemoryAdvisor> advisorMap = new ConcurrentHashMap<>();

    private final String systemPrompt = """
            
            """;

    /**
     * Обрабатывает GET-запрос /{user}/inquire?question=... и возвращает ответ
     * от модели.
     *
     * @param user     идентификатор пользователя (используется для отделения памяти)
     * @param question текст запроса пользователя
     * @return ответ модели в виде строки
     */
    @GetMapping("/{user}/inquire")
    public String ask(@PathVariable String user,
                      @RequestParam String question) {


        ChatMemory chatMemory1 = MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(10)
                .build();

        var advisor = this.advisorMap.computeIfAbsent(user,
                _ -> PromptChatMemoryAdvisor.builder(chatMemory1).build());

        var response = llamaClient
                .prompt(question)
                .user(user)
                .advisors(advisor)
                .call()
                .content();

        var llamaResponse = openAiClient
                .prompt(question)
                .user(user)
                .call()
                .content();
        return response + "...........................\n"
                + llamaResponse;
    }
}

package com.holic.java.mcp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    private final JdbcChatMemoryRepository chatMemoryRepository;


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
//        ChatMemory chatMemory = MessageWindowChatMemory.builder()
//                .chatMemoryRepository(chatMemoryRepository)
//                .maxMessages(10)
//                .build();
//        var advisor = this.advisorMap.computeIfAbsent(user,
//                _ -> PromptChatMemoryAdvisor.builder(chatMemory).build());
        var response = llamaClient
                .prompt(question)
                .user(question)
//                .advisors(advisor)
                .call()
                .content();
        var llamaResponse = openAiClient
                .prompt(question)
                .user(question)
//                .advisors(advisor)
                .call()
                .content();
        return response + "..........................." + llamaResponse;
    }
}

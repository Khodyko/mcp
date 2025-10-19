package com.holic.java.mcp.controller;

import com.holic.java.mcp.service.OrchestratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер поддержки — проксирует запросы к ChatClient Spring AI и хранит
 * историю диалогов в JDBC-репозитории.
 */
@RestController
@RequestMapping("/ai/support")
@RequiredArgsConstructor
public class SupportAssistantController {

    private final OrchestratorService orchestratorService;

    @PostMapping("/{user}/ask")
    public String ask(@PathVariable String user, @RequestParam String question) {
        String agentResponse = orchestratorService.orchestrate(user, question);
        return agentResponse;
    }

    /**
     * Обрабатывает GET-запрос /{user}/inquire?question=... и возвращает ответ
     * от модели.
     *
     * @param user     идентификатор пользователя (используется для отделения памяти)
     * @param question текст запроса пользователя
     * @return ответ модели в виде строки
     */
//    @GetMapping("/{user}/inquire")
//    public String ask(@PathVariable String user,
//                      @RequestParam String question) {
//
//
//        ChatMemory chatMemory1 = MessageWindowChatMemory.builder()
//                .chatMemoryRepository(new InMemoryChatMemoryRepository())
//                .maxMessages(10)
//                .build();
//
//        var advisor = this.advisorMap.computeIfAbsent(user,
//                _ -> PromptChatMemoryAdvisor.builder(chatMemory1).build());
//
//        var response = llamaClient
//                .prompt(question)
//                .user(user)
//                .advisors(advisor)
//                .call()
//                .content();
//
//        var llamaResponse = openAiClient
//                .prompt(question)
//                .user(user)
//                .call()
//                .content();
//        return response + "...........................\n"
//                + llamaResponse;
//    }
}

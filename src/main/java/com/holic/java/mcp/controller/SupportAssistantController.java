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

    @GetMapping("/{user}/ask")
    public String ask(@PathVariable Integer user, @RequestParam String question) {
        String agentResponse = orchestratorService.orchestrate(user, question);
        return agentResponse;
    }

    @GetMapping("/{user}/chain/ask")
    public String askChain(@PathVariable Integer user, @RequestParam String question) {
        String agentResponse = orchestratorService.orchestrateChain(user, question);
        return agentResponse;
    }
}

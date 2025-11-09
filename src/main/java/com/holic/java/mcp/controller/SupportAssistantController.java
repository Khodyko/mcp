package com.holic.java.mcp.controller;

import com.holic.java.mcp.orchestrator.ChainOrchestrator;
import com.holic.java.mcp.orchestrator.OrchestratorService;
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
    private final ChainOrchestrator chainOrchestrator;

    @GetMapping("/{user}/ask")
    public String ask(@PathVariable Integer user, @RequestParam String question) {
        return orchestratorService.orchestrate(user, question);
    }

    @GetMapping("/{user}/chain/ask")
    public String askChain(@PathVariable Integer user, @RequestParam String question) {
        return chainOrchestrator.orchestrateChain(user, question);
    }
}

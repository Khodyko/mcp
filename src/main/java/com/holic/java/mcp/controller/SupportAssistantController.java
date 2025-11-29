package com.holic.java.mcp.controller;

import com.holic.java.mcp.agent.chain.simple.SimpleAiChain;
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
    private final SimpleAiChain simpleAiChain;

    @GetMapping("/{user}")
    public String ask(@PathVariable Integer user, @RequestParam String question) {
        return orchestratorService.orchestrate(user, question);
    }

    @GetMapping("/{user}/chain/simple")
    public String askChain(@PathVariable Integer user, @RequestParam String question) {
        return chainOrchestrator.orchestrateChain(user, question);
    }

    @GetMapping("/{user}/chain/seq")
    public String createStorySequentially(@PathVariable Integer user, @RequestParam String mainHero) {
        return simpleAiChain.handleSequentially(mainHero);
    }

    @GetMapping("/{user}/chain/par")
    public String createStoryParallel(@PathVariable Integer user, @RequestParam String mainHero) {
        return simpleAiChain.handleParallel(mainHero);
    }

    @GetMapping("/{user}/chain/rep")
    public String createStoryRepeated(@PathVariable Integer user, @RequestParam String mainHero) {
        return simpleAiChain.handleRepeated();
    }
}

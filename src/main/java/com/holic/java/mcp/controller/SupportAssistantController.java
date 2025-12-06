package com.holic.java.mcp.controller;

import com.holic.java.mcp.agent.chain.simple.SimpleAiChain;
import com.holic.java.mcp.orchestrator.ChainOrchestrator;
import com.holic.java.mcp.orchestrator.OrchestratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер поддержки — проксирует запросы к ChatClient Spring AI и хранит
 * историю диалогов в JDBC-репозитории.
 */
@RestController
@RequestMapping("/ai/support")
@RequiredArgsConstructor
public class SupportAssistantController {
    @Qualifier("simpleOrchestrator")
    private final OrchestratorService orchestratorService;
    @Qualifier("aiBasedOrchestrator")
    private final OrchestratorService aiBasedOrchestrator;
    @Qualifier("toolBasedOrchestrator")
    private final OrchestratorService toolBasedOrchestrator;
    private final ChainOrchestrator chainOrchestrator;
    private final SimpleAiChain simpleAiChain;

    @GetMapping("/{user}/orchestrator/simple")
    public String askOrchestrator(@PathVariable Integer user, @RequestParam String question) {
        return orchestratorService.orchestrate(user, question);
    }

    @GetMapping("/{user}/orchestrator/aibased")
    public String askAiBasedoOrchestrator(@PathVariable Integer user, @RequestParam String question) {
        return aiBasedOrchestrator.orchestrate(user, question);
    }

    @GetMapping("/{user}/orchestrator/toolbased")
    public String askToolBasedOrchestrator(@PathVariable Integer user, @RequestParam String question) {
        return toolBasedOrchestrator.orchestrate(user, question);
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

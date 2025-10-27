package com.holic.java.mcp.agent.impl;

import com.holic.java.mcp.agent.FallBackAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

/**
 * Маркерный класс агента-подстраховщика.
 */
@Service
@RequiredArgsConstructor
public class FallbackAgentImpl implements FallBackAgent {

    public static final String AGENT_NAME = "FallbackAgent";

    @Tool(description = "answer for not found tools", name = AGENT_NAME)
    @Override
    public String handle() {
        return "Не найдены агенты, которые могут помочь решить данный запрос.";
    }

    @Override
    public String getName() {
        return AGENT_NAME;
    }
}

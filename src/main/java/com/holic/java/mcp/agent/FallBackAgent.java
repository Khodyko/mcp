package com.holic.java.mcp.agent;

import com.holic.java.mcp.agent.impl.FallbackAgentImpl;
import org.springframework.ai.tool.annotation.Tool;

public interface FallBackAgent {
    @Tool(description = "answer for not found tools", name = FallbackAgentImpl.AGENT_NAME)
    String handle();

    String getName();
}

package com.holic.java.mcp.agent;

import org.springframework.ai.chat.model.ToolContext;

public interface OrderAgent {


    String handle(String productName,
                  Integer quantity,
                  ToolContext context);

    String getName();
}

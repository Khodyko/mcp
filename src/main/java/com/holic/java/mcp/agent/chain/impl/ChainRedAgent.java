package com.holic.java.mcp.agent.chain.impl;

import com.holic.java.mcp.agent.chain.ChainAgent;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service("chainRedAgent")
public class ChainRedAgent implements ChainAgent {


    @Tool(name = "chainRedAgent")
    @Override
    public String runChainAgent(@ToolParam(description = "word for generation") String chainArgument) {
        return "Tomato " + chainArgument;
    }
}

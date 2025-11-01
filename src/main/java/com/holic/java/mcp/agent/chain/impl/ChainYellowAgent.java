package com.holic.java.mcp.agent.chain.impl;

import com.holic.java.mcp.agent.chain.ChainAgent;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service("chainYellowAgent")
public class ChainYellowAgent  implements ChainAgent {

    @Tool(name = "chainYellowAgent")
    @Override
    public String runChainAgent(@ToolParam(description = "word for generation") String chainArgument) {
        return "Banana "+ chainArgument;
    }
}

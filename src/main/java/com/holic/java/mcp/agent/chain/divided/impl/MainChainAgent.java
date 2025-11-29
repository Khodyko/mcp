package com.holic.java.mcp.agent.chain.divided.impl;

import com.holic.java.mcp.agent.chain.divided.ChainAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MainChainAgent {

    private final Map<Integer, ChainAgent> chainAgents = new HashMap<Integer, ChainAgent>();
    private final String CHAIN_RESPONSE_TEMPLATE = "Call tool with name %s with word %s";

    @Tool(description = "creates specific word by num of inner agent and a word", name = "mainChainAgent")
    public String runAnotherAgentByParams(@ToolParam(description = "num of inner agent") Integer numberOfInnerAgent,
                                          @ToolParam(description = "word for generation") String chainArgument) {

        switch (numberOfInnerAgent) {
            case 1:
                return String.format(CHAIN_RESPONSE_TEMPLATE, "chainRedAgent", chainArgument);
            case 2:
                return String.format(CHAIN_RESPONSE_TEMPLATE, "chainYellowAgent", chainArgument);
            default:
                return "empty " + chainArgument;
        }
    }
}

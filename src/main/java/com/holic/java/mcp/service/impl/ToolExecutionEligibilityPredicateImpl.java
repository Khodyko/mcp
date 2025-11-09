package com.holic.java.mcp.service.impl;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolExecutionEligibilityPredicate;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ToolExecutionEligibilityPredicateImpl implements ToolExecutionEligibilityPredicate {

    @Override
    public boolean test(ChatOptions promptOptions, ChatResponse chatResponse) {
        //before tool calling
        return ToolCallingChatOptions.isInternalToolExecutionEnabled(promptOptions) && chatResponse != null && chatResponse.hasToolCalls();
    }

}

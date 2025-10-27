package com.holic.java.mcp.service.impl;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolExecutionEligibilityPredicate;

//@Service
//@Primary
public class ToolExecutionEligibilityPredicateImpl implements ToolExecutionEligibilityPredicate {
    @Override
    public boolean test(ChatOptions promptOptions, ChatResponse chatResponse) {
        //todo rewrite to check isService can handle
        return ToolCallingChatOptions.isInternalToolExecutionEnabled(promptOptions) && chatResponse != null && chatResponse.hasToolCalls();
    }
}

package com.holic.java.mcp.agent.impl;

import com.holic.java.mcp.agent.SupportAgent;

/**
 * Маркерный класс агента-подстраховщика.
 */
public class FallbackAgent implements SupportAgent {
    @Override
    public boolean canHandle(String query) {
        return false;
    }

    @Override
    public String handle(String query) {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }
}

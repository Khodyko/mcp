package com.holic.java.mcp.agent;

/**
 * Контракт агента поддержки.
 */
public interface SupportAgent {
    String handle(String query);
    String getName();
}

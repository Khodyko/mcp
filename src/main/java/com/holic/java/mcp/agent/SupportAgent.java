package com.holic.java.mcp.agent;

/**
 * Контракт агента поддержки.
 */
public interface SupportAgent {
    boolean canHandle(String query);
    String handle(String query);
    String getName();
}

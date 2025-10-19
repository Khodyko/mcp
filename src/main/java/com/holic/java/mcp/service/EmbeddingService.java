package com.holic.java.mcp.service;

/**
 * Сервис для генерации и управления эмбеддингами.
 */
public interface EmbeddingService {
    float[] embed(String text);
    void index(String id, String text, Object metadata);
}

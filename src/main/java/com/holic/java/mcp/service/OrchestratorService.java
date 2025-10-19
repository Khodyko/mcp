package com.holic.java.mcp.service;

public interface OrchestratorService {

    /**
     * Анализирует запрос и возвращает агрегированный ответ.
     * @param query входной текстовый запрос
     * @return агрегированный текстовый ответ
     */
    String orchestrate(String chatId, String query);

}

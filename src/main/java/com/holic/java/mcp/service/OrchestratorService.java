package com.holic.java.mcp.service;

public interface OrchestratorService {

    /**
     * Анализирует запрос и возвращает агрегированный ответ.
     * @param query входной текстовый запрос
     * @return агрегированный текстовый ответ
     */
    String orchestrate(Integer chatId, String query);

    String orchestrateChain(Integer userId, String query);
}

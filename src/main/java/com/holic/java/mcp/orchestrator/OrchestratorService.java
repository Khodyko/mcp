package com.holic.java.mcp.orchestrator;

public interface OrchestratorService {

    /**
     * Анализирует запрос и возвращает агрегированный ответ.
     * @param query входной текстовый запрос
     * @return агрегированный текстовый ответ
     */
    String orchestrate(Integer chatId, String query);

    String orchestrateThroughtChatOptions(Integer userId, String query);
}

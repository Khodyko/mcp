package com.holic.java.mcp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.tool.DefaultToolCallingManager;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionEligibilityPredicate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.tool.execution.DefaultToolExecutionExceptionProcessor;
import org.springframework.ai.tool.execution.ToolExecutionExceptionProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:env.properties")
@RequiredArgsConstructor
public class AIConfig {

    @Value("${spring.ai.openai.api-key}")
    private final String openAiApiKey;
    private final ToolExecutionEligibilityPredicate toolExecutionEligibilityPredicate;


    @Bean("openAiChatClient")
    public ChatClient chatClient(@Qualifier("openAiChatModel1") ChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }

    @Bean("openAiChatModel1")
    public ChatModel chatModel() {
        var openAiApi = OpenAiApi.builder().apiKey(openAiApiKey)
                .build();
        return OpenAiChatModel.builder()
                .toolExecutionEligibilityPredicate(toolExecutionEligibilityPredicate)
                .openAiApi(openAiApi)
                .build();
    }

    /**
     * Менеджер исполнения tool-calls в user-controlled режиме.
     */
    @Bean
    public ToolCallingManager toolCallingManager() {
        return DefaultToolCallingManager.builder().build();
    }

    /**
     * Политика обработки ошибок при вызове тулов.
     * true — пробрасывать исключения наружу; false — возвращать ошибку в модель.
     */
    @Bean
    public ToolExecutionExceptionProcessor toolExecutionExceptionProcessor() {
        return new DefaultToolExecutionExceptionProcessor(false);
    }

}

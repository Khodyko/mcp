package com.holic.java.mcp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.tool.ToolExecutionEligibilityPredicate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
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
        ChatClient openAiChatClient = ChatClient.builder(chatModel).build();
        return openAiChatClient;
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


//    @Bean("llamaModel")
//    @Primary
//    public ChatModel chatModelLlama() {
//        var ollamaApi = OllamaApi.builder().build();
//        return OllamaChatModel.builder()
//                .ollamaApi(ollamaApi)
//                .defaultOptions(
//                        OllamaOptions.builder()
//                                .model("llama3:8b")
//                                .temperature(0.9)
//                                .build())
//                .build();
//    }


//    @Bean("llamaChatClient")
//    @Primary
//    public ChatClient llamaChatClient(@Qualifier("llamaModel") ChatModel chatModel) {
//        ChatClient llamaChatClient = ChatClient.builder(chatModel).build();
//        return llamaChatClient;
//    }
}

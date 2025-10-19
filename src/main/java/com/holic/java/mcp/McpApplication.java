package com.holic.java.mcp;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.pgvector.autoconfigure.PgVectorStoreAutoConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = PgVectorStoreAutoConfiguration.class)
@RequiredArgsConstructor
@PropertySource("classpath:env.properties")
public class McpApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(McpApplication.class, args);
    }

    @Value("${spring.ai.openai.api-key}")
    private final String openAiApiKey;


    @Bean("openAiChatClient")
    public ChatClient chatClient(@Qualifier("openAiChatModel1") ChatModel chatModel) {
        ChatClient openAiChatClient = ChatClient.builder(chatModel).build();
        return openAiChatClient;
    }

    @Bean("openAiChatModel1")
    @Primary
    public ChatModel chatModel() {
        var openAiApi = OpenAiApi.builder().apiKey(openAiApiKey).build();
        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .build();
    }


    @Bean("llamaModel")
    public ChatModel chatModelLlama() {
        var ollamaApi = OllamaApi.builder().build();
        return OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(
                        OllamaOptions.builder()
                                .model("llama3:8b")
                                .temperature(0.9)
                                .build())
                .build();
    }


    @Bean("llamaChatClient")
    @Primary
    public ChatClient llamaChatClient(@Qualifier("llamaModel") ChatModel chatModel) {
        ChatClient llamaChatClient = ChatClient.builder(chatModel).build();
        return llamaChatClient;
    }
}

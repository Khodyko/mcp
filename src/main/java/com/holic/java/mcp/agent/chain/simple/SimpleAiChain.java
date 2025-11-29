package com.holic.java.mcp.agent.chain.simple;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
@Slf4j
public class SimpleAiChain {

    private final ChatClient chatClient;

    private final String story = """
            Однажды маленький котик заблудился в лесу. Он испугался и начал мяукать,
            надеясь, что его кто-то услышит. Вскоре его нашёл добрый мальчик 
            и вместе они отправились домой.
            """;

    private final String[] type = {
            "Рассказ веселый",
            "Рассказ грустный",
            "Рассказ страшный"
    };

    private final String[] place = {
            "Всё происходит на новогодней елке",
            "Всё происходит на вокзале",
            "Всё происходит в яблоневом саду"
    };

    private final String[] heroDoings = {
            "Герои истории любят петь",
            "Герои истории занимаются рыбалкой",
            "Герои истории мечтают об отпуске"
    };

    private String continuePromptTemplate = """
            Исправь короткую историю из 3 предложений пользуясь следующей информацией:
            Набросок истории: %s
            Рассказ должен учитывать что: %s
            """;

    private String systemPrompt = "Возвращай только ответ по заданию, без комментарией. " +
            "Ответ должен содержать только историю.";

    public String handleSequentially(String mainHero) {

        List<String> storyFacts = Stream.of(
                "Главный герой рассказа:" + mainHero,
                type[1],
                place[1],
                heroDoings[1]).toList();

        String result = story;
        String currentQuery;

        for (String fact : storyFacts) {
            currentQuery = String.format(continuePromptTemplate, result, fact);
            log.debug("currentQuery {}", currentQuery);
            result = chatClient
                    .prompt(currentQuery)
                    .system(systemPrompt)
                    .call()
                    .content();
            log.debug("result: {}", result);
        }

        return result;
    }

    public String handleParallel(String mainHero) {

        List<String> storyFacts = Stream.of(
                "Главный герой рассказа:" + mainHero,
                type[0],
                place[0],
                heroDoings[0]).toList();

        String result = story;

        List<String> results = storyFacts.stream()
                .parallel()
                .map(fact -> String.format(continuePromptTemplate, result, fact))
                .peek(query -> log.debug("currentQuery: {}", query))
                .map(query -> {
                    return chatClient
                            .prompt(query)
                            .system(systemPrompt)
                            .call()
                            .content();
                })
                .peek(s -> log.debug("result: {}", s))
                .toList();

        return results.toString();
    }


    public String handleRepeated() {
        String result = story;
        int counter = 0;
        while (true) {
            counter++;
            String currentQuery = String.format(continuePromptTemplate, result, "Добавь еще одного герой рассказа");
            log.debug("currentQuery {}", currentQuery);
            result = chatClient
                    .prompt(currentQuery)
                    .system(systemPrompt + "напиши в конце сколько героев рассказа получилось, фразой - Получилось ... героев.")
                    .call()
                    .content();
            log.debug("result: {}", result);
            if (result.contains("Получилось 5 героев")) {
                break;
            }
            if(counter>=12){
                throw new RuntimeException("Запрос зациклился");
            }
        }

        return result;
    }
}
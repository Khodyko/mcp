package com.holic.java.mcp;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@RequiredArgsConstructor
public class McpApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(McpApplication.class, args);
    }
}

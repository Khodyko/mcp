package com.holic.java.mcp.agent.impl;

import com.holic.java.mcp.enums.ToolEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class FindToolNameAgent {

    @Tool(description = "Находит имя агента для последующего вызова",
            name = "findToolAgent")
    public Integer findToolAgent(String query, ToolEnum toolEnum) {
       if(toolEnum==null){
           return 3;
       } else {
           return toolEnum.getNumber();
       }
    }
}

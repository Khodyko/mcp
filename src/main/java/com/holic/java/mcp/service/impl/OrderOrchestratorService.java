package com.holic.java.mcp.service.impl;

import com.holic.java.mcp.service.OrchestratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderOrchestratorService implements OrchestratorService {




    @Override
    public String orchestrate(String chatId, String query) {
        return "";
    }
}

package com.example.petstore.service;

import com.example.petstore.domain.CommandLog;
import com.example.petstore.dto.BuyCommandLogDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CommandLogService {
    void logCommandExecution(String name, Map<String, Object> additionalData);

    List<BuyCommandLogDTO> getBuyCommandLogs();

    List<CommandLog> getAll();
}

package com.example.petstore.service.impls;

import com.example.petstore.domain.CommandLog;
import com.example.petstore.dto.BuyCommandLogDTO;
import com.example.petstore.repository.CommandLogRepository;
import com.example.petstore.service.CommandLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommandLogServiceImpl implements CommandLogService {
    private final CommandLogRepository commandLogRepository;
    private final ObjectMapper objectMapper;

    public CommandLogServiceImpl(CommandLogRepository commandLogRepository, ObjectMapper objectMapper) {
        this.commandLogRepository = commandLogRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    @Modifying
    public void logCommandExecution(String commandName, Map<String, Object> additionalData) {
        try {
            String additionalDataJson = objectMapper.writeValueAsString(additionalData);
            commandLogRepository.saveWithNativeQuery(commandName, LocalDate.now(), additionalDataJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing additional data", e);
        }
    }

    @Override
    public List<BuyCommandLogDTO> getBuyCommandLogs() {
        List<CommandLog> commandLogs = commandLogRepository.findAllByCommandName("buy");
        return commandLogs.stream().map(this::deserializeBuyCommandLog).collect(Collectors.toList());
    }

    @Override
    public List<CommandLog> getAll() {
        return commandLogRepository.findAll();
    }

    private BuyCommandLogDTO deserializeBuyCommandLog(CommandLog log) {
        try {
            JsonNode jsonNode = objectMapper.readTree(log.getAdditionalData().toString());
            BuyCommandLogDTO dto = objectMapper.treeToValue(jsonNode, BuyCommandLogDTO.class);
            dto.setExecutionDate(log.getExecutionDate());
            return dto;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing buy command log", e);
        }
    }
}
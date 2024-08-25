package com.example.petstore.controllers;

import com.example.petstore.dto.BuyCommandLogDTO;
import com.example.petstore.service.CommandLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogController {
    private final CommandLogService commandLogService;

    public LogController(CommandLogService commandLogService) {
        this.commandLogService = commandLogService;
    }

    @GetMapping("/history-log")
    public ResponseEntity<List<BuyCommandLogDTO>> getBuyCommandLogHistory() {
        return ResponseEntity.ok(commandLogService.getBuyCommandLogs());
    }
}

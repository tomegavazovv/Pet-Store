package com.example.petstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyCommandLogDTO {
    private LocalDate executionDate;
    private Integer successCount;
    private Integer failCount;
}

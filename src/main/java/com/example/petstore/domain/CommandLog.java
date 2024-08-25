package com.example.petstore.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


/**
 * CommandLog represents a generic class for all types of logs related with commands/methods.
 *
 * I probably didn't need to do it this way for the simple use case of logging the buy command
 * but I wanted to try to make it more generic and make it easy to add new commands to our
 * logging system.
 *
 * I haven't worked with json in relational database before so this part was fun too.
 *
 * So we identify the command by its name, the field additionalData is json which is specific
 * for each command that we would like to have in the logging system.
 *
 */
@Entity
@Data
@NoArgsConstructor
public class CommandLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commandName;
    private LocalDate executionDate;

    @Column(columnDefinition = "jsonb")
    private Object additionalData;

    public CommandLog(String commandName, LocalDate executionDate, Object additionalData) {
        this.commandName = commandName;
        this.executionDate = executionDate;
        this.additionalData = additionalData;
    }
}
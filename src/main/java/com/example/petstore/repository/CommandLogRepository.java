package com.example.petstore.repository;

import com.example.petstore.domain.CommandLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommandLogRepository extends JpaRepository<CommandLog, Long> {

    List<CommandLog> findAllByCommandName(String commandName);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO command_log (command_name, execution_date, additional_data) " +
            "VALUES (:commandName, :executionDate, CAST(:additionalData AS jsonb))", nativeQuery = true)
    void saveWithNativeQuery(@Param("commandName") String commandName,
                             @Param("executionDate") LocalDate executionDate,
                             @Param("additionalData") String additionalData);
}
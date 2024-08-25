package com.example.petstore;

import com.example.petstore.domain.Cat;
import com.example.petstore.domain.User;
import com.example.petstore.dto.BuyCommandLogDTO;
import com.example.petstore.repository.PetRepository;
import com.example.petstore.repository.UserRepository;
import com.example.petstore.service.CommandLogService;
import com.example.petstore.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CommandLogServiceImplIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CommandLogService commandLogService;

    @MockBean
    private PetRepository petRepository;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    void testLogBuyCommandExecution() {
        User user1 = new User();
        user1.setBudget(20.0);
        Cat pet1 = new Cat();
        pet1.setDateOfBirth(LocalDate.now().minusYears(10));

        when(userRepository.findAll()).thenReturn(List.of(user1));
        when(petRepository.findAllByOwnerIsNull()).thenReturn(List.of(pet1));
        when(userRepository.saveAll(any())).thenReturn(List.of(user1));

        userService.buy();

        List<BuyCommandLogDTO> logs = commandLogService.getBuyCommandLogs();

        assertNotNull(logs);
        assertEquals(1, logs.size());
        BuyCommandLogDTO log = logs.get(0);
        assertEquals(1, log.getSuccessCount());
        assertEquals(0, log.getFailCount());
    }
}
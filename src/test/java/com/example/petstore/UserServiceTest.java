package com.example.petstore;

import com.example.petstore.domain.CommandLog;
import com.example.petstore.domain.Pet;
import com.example.petstore.domain.User;
import com.example.petstore.repository.CommandLogRepository;
import com.example.petstore.repository.PetRepository;
import com.example.petstore.repository.UserRepository;
import com.example.petstore.service.UserService;
import com.example.petstore.utils.DataGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class UserServiceImplIntegrationTest {


    @Autowired
    private UserService userService;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommandLogRepository commandLogRepository;

    @Autowired
    private DataGenerator dataGenerator;

    @Test
    void testCreateAndListUsers() throws IOException {
        int count = 5;
        List<User> createdUsers = userService.createUsers(5);

        assertEquals(count, createdUsers.size());

        List<User> usersInDb = userService.listUsers();
        assertEquals(count, usersInDb.size());

    }

    @Test
    void testBuy() throws IOException {
        int userCount = 5;
        int petCount = 10;
        List<User> users = userService.createUsers(userCount);
        List<Pet> pets = dataGenerator.generateRandomPets(petCount);
        petRepository.saveAll(pets);

        Map<Long, Double> initialBudgets = users.stream()
                .collect(Collectors.toMap(User::getId, User::getBudget));

        users.get(0).setBudget(20.0);
        users.get(2).setBudget(30.0);
        userRepository.saveAll(users);

        long initialPetsWithoutOwner = petRepository.findAllByOwnerIsNull().size();

        userService.buy();

        List<User> updatedUsers = userRepository.findAll();

        int usersWithPets = (int) updatedUsers.stream().filter(user -> !user.getPets().isEmpty()).count();
        assertTrue(usersWithPets > 0, "At least one user should have bought a pet");

        long finalPetsWithoutOwner = petRepository.findAllByOwnerIsNull().size();
        assertTrue(finalPetsWithoutOwner < initialPetsWithoutOwner, "The number of pets without owners should have decreased");

        for (User updatedUser : updatedUsers) {
            if (!updatedUser.getPets().isEmpty()) {
                double initialBudget = initialBudgets.get(updatedUser.getId());
                double finalBudget = updatedUser.getBudget();
                assertTrue(finalBudget < initialBudget,
                        "User " + updatedUser.getId() + " budget should have decreased after buying pet/s");
            }
        }

        List<CommandLog> commandLogs = commandLogRepository.findAll();
        assertEquals(1, commandLogs.size(), "Exactly one command log should be saved in the DB");

        CommandLog logEntry = commandLogs.get(0);
        assertEquals("buy", logEntry.getCommandName());

    }
}

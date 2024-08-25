package com.example.petstore.service.impls;

import com.example.petstore.domain.Pet;
import com.example.petstore.domain.User;
import com.example.petstore.repository.PetRepository;
import com.example.petstore.repository.UserRepository;
import com.example.petstore.service.CommandLogService;
import com.example.petstore.service.UserService;
import com.example.petstore.utils.DataGenerator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final DataGenerator dataGenerator;
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final CommandLogService commandLogService;

    public UserServiceImpl(DataGenerator dataGenerator, PetRepository petRepository, UserRepository userRepository, CommandLogService commandLogService) {
        this.dataGenerator = dataGenerator;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.commandLogService = commandLogService;
    }

    @Override
    public List<User> createUsers(Integer count) throws IOException {
        List<User> users = dataGenerator.generateRandomUsers(count);
        return userRepository.saveAll(users);

    }

    @Override
    public List<User> listUsers() {
        List<User> users = userRepository.findAll();
        users.forEach(System.out::println);
        return users;
    }

    // The assumption here is that a user buys pets until he has the budget for it. No optimizations, just random.
    @Override
    @Transactional
    public List<User> buy() {
        List<Pet> pets = petRepository.findAllByOwnerIsNull();
        List<User> users = userRepository.findAll();

        int successCount = 0;
        int failCount = 0;

        for (User user : users) {
            if (tryBuyPetForUser(user, pets)) {
                successCount++;
            } else {
                failCount++;
            }
        }

        logBuyCommandExecution(successCount, failCount);
        return userRepository.saveAll(users);
    }

    private boolean tryBuyPetForUser(User user, List<Pet> availablePets) {
        for (Pet pet : availablePets) {
            if (pet.getOwner().isEmpty() && user.getBudget() > pet.getPrice()) {
                user.buyPet(pet);
                return true;
            }
        }
        return false;
    }

    private void logBuyCommandExecution(Integer successCount, Integer failCount) {
        Map<String, Object> logAdditionalData = new HashMap<>();
        logAdditionalData.put("successCount", successCount);
        logAdditionalData.put("failCount", failCount);
        commandLogService.logCommandExecution("buy", logAdditionalData);
    }

}

package com.example.petstore.utils;

import com.example.petstore.domain.Cat;
import com.example.petstore.domain.Dog;
import com.example.petstore.domain.Pet;
import com.example.petstore.domain.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * For the creating random users and pets I decided to go with reading from a json file.
 * I tried to find an API that creates random persons or pets in bulk but I couldn't find such an API.
 */

@Component
public class DataGenerator {
    private final JsonFileUtils jsonFileUtils;

    public DataGenerator(JsonFileUtils jsonFileUtils) {
        this.jsonFileUtils = jsonFileUtils;
    }

    public List<User> generateRandomUsers(Integer count) throws IOException {
        Random random = new Random();
        return jsonFileUtils.readJsonFile("data/randomPeople.json", User.class)
                .stream()
                .limit(count)
                .peek(user -> user.setBudget(random.nextDouble(10, 20)))
                .toList();
    }

    public List<Pet> generateRandomPets(Integer count) throws IOException {
        List<Dog> dogs = jsonFileUtils.readJsonFile("data/randomDogs.json", Dog.class);
        List<Cat> cats = jsonFileUtils.readJsonFile("data/randomCats.json", Cat.class);
        return Stream.concat(dogs.stream(), cats.stream()).limit(count).collect(Collectors.toList());
    }
}

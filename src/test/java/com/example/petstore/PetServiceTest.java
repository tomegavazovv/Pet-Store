package com.example.petstore;

import com.example.petstore.domain.Pet;
import com.example.petstore.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PetServiceTest {

    @Autowired
    private PetService petService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAndListPets() throws IOException {
        int count = 10;
        List<Pet> createdPets = petService.createPets(count);

        assertEquals(count, createdPets.size());
        List<Pet> petsInDb = petService.listPets();
        assertEquals(count, petsInDb.size());
    }
}
package com.example.petstore;

import com.example.petstore.controllers.PetController;
import com.example.petstore.domain.Pet;
import com.example.petstore.dto.request.CreatePetsRequest;
import com.example.petstore.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PetControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetService petService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreatePetsWithDefaultCount() throws Exception {
        int count = PetController.MAX_USERS_ALLOWED;
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/create-pets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(count));

        List<Pet> petsInDb = petService.listPets();
        assertEquals(count, petsInDb.size());
    }

    @Test
    void testCreatePetsWithCustomCount() throws Exception {
        int count = 10;
        CreatePetsRequest requestBody = new CreatePetsRequest(count);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/create-pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(count));

        List<Pet> petsInDb = petService.listPets();
        assertEquals(count, petsInDb.size());
    }

    @Test
    void testListPets() throws Exception {
        petService.createPets(5);
        mockMvc.perform(MockMvcRequestBuilders.get("/list-pets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));
    }

}
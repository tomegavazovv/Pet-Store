package com.example.petstore;
import com.example.petstore.controllers.UserController;
import com.example.petstore.domain.Pet;
import com.example.petstore.domain.User;
import com.example.petstore.dto.request.CreateUsersRequest;
import com.example.petstore.service.PetService;
import com.example.petstore.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PetService petService;

    @Test
    void testCreateUsersWithDefaultCount() throws Exception {
        int count = UserController.MAX_USERS_ALLOWED;
        userService.createUsers(count);

        mockMvc.perform(MockMvcRequestBuilders.post("/create-users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(count));

        List<User> users = userService.listUsers();
        assertEquals(count, users.size());
    }

    @Test
    void testCreateUsersWithCustomCount() throws Exception {
        int count = 5;
        CreateUsersRequest request = new CreateUsersRequest(count);

        userService.createUsers(count);

        mockMvc.perform(MockMvcRequestBuilders.post("/create-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(count));

        List<User> users = userService.listUsers();
        assertEquals(count, users.size());
    }

    @Test
    void testListUsers() throws Exception {
        userService.createUsers(5);
        mockMvc.perform(MockMvcRequestBuilders.get("/list-pets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    void testBuyPets() throws Exception {
        userService.createUsers(10);
        petService.createPets(10);

        mockMvc.perform(MockMvcRequestBuilders.post("/buy")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    // Check if at least one user has bought a pet
                    String responseBody = result.getResponse().getContentAsString();
                    JSONArray usersArray = new JSONArray(responseBody);
                    boolean hasUserWithPets = false;
                    for (int i = 0; i < usersArray.length(); i++) {
                        JSONObject user = usersArray.getJSONObject(i);
                        JSONArray pets = user.optJSONArray("pets");
                        if (pets != null && pets.length() > 0) {
                            hasUserWithPets = true;
                            break;
                        }
                    }
                    assertTrue(hasUserWithPets, "At least one user should have bought a pet.");
                });
    }
}
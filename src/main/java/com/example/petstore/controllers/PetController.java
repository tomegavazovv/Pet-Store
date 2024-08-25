package com.example.petstore.controllers;

import com.example.petstore.domain.Pet;
import com.example.petstore.dto.request.CreatePetsRequest;
import com.example.petstore.service.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class PetController {

    private final PetService petService;
    public static final int MAX_USERS_ALLOWED = 20;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("/create-pets")
    public ResponseEntity<?> createPets(@RequestBody(required = false) CreatePetsRequest request) {
        int count = MAX_USERS_ALLOWED;
        if (request != null) {
            count = request.getCount();
        }

        try {
            List<Pet> createdPets = petService.createPets(count);
            return ResponseEntity.ok(createdPets);
        } catch (IOException e) {
            return ResponseEntity.status(400).body("Error while creating new pets. Try again later.");
        }

    }

    @GetMapping("/list-pets")
    public ResponseEntity<List<Pet>> listPets() {
        return ResponseEntity.ok(petService.listPets());
    }

}


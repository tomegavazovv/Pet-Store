package com.example.petstore.service;

import com.example.petstore.domain.Pet;

import java.io.IOException;
import java.util.List;

public interface PetService {
    List<Pet> createPets(Integer count) throws IOException;

    List<Pet> listPets();
}

package com.example.petstore.service.impls;

import com.example.petstore.domain.Pet;
import com.example.petstore.repository.PetRepository;
import com.example.petstore.service.PetService;
import com.example.petstore.utils.DataGenerator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class PetServiceImpl implements PetService {
    private final DataGenerator dataGenerator;
    private final PetRepository petRepository;

    public PetServiceImpl(DataGenerator dataGenerator, PetRepository petRepository) {
        this.dataGenerator = dataGenerator;
        this.petRepository = petRepository;
    }

    @Transactional
    @Override
    public List<Pet> createPets(Integer count) throws IOException {
        List<Pet> pets = dataGenerator.generateRandomPets(count);
        Collections.shuffle(pets);
        return petRepository.saveAll(pets);

    }

    @Override
    public List<Pet> listPets() {
        return petRepository.findAll();
    }
}

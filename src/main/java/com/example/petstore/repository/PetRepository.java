package com.example.petstore.repository;

import com.example.petstore.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByOwnerIsNull();
}

package com.example.petstore.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity(name = "pet_store_user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Double budget;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Pet> pets = new ArrayList<>();

    public User(String firstName, String lastName, String emailAddress, Double budget) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.budget = budget;
    }

    public void buyPet(Pet pet) {
        this.budget -= pet.getPrice();
        this.pets.add(pet);
        pet.setOwner(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(emailAddress, user.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(emailAddress);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("User:\n" + "name: " + firstName + ' ' + lastName + "\nemail: " + emailAddress + "\nbudget: " + budget + "\n");
        stringBuilder.append("Pets: [\n");
        this.pets.forEach(pet -> stringBuilder.append("\t").append(pet.toString()).append("\n**\n"));
        stringBuilder.append("]\n--------------\n");
        return stringBuilder.toString();
    }
}

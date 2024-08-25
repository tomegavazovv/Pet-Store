package com.example.petstore.domain;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Cat extends Pet {
    public Cat(String name, String description, LocalDate dateOfBirth) {
        super(name, description, dateOfBirth);
    }

    @Override
    public Double getPrice() {
        return (double) this.calculateAge();
    }

    @Override
    public void setOwner(User owner) {
        this.owner = owner;
        String consoleMessage = "Meow, cat " + this.name + " has owner " + owner.getFirstName();
        System.out.println(consoleMessage);
    }

    @Override
    public String toString() {
        return "Cat:\n" + super.toString();
    }
}

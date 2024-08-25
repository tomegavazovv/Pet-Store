package com.example.petstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

/**
 * Pet represents an abstract base class for all types of pets in the system.
 *
 * I decided to code it this way since I want to take advantage of polymorphism and make it easy
 * to add new types of pets in the future etc.
 *
 */

@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    protected String name;
    @Column(length = 255)
    protected String description;
    protected LocalDate dateOfBirth;

    @ManyToOne
    @JsonIgnore
    protected User owner;

    public Pet(String name, String description, LocalDate dateOfBirth) {
        this.name = name;
        this.description = description;
        this.dateOfBirth = dateOfBirth;
        this.owner = null;
    }

    public Integer calculateAge() {
        LocalDate currentDate = LocalDate.now();
        return Period.between(this.dateOfBirth, currentDate).getYears();
    }

    public abstract Double getPrice();

    public Optional<User> getOwner() {
        return Optional.ofNullable(owner);
    }

    public abstract void setOwner(User owner);

    @Override
    public String toString() {
        return "\tname: " + name + '\n' + "\tdescription: " + description + '\n' + "\tdateOfBirth: " + dateOfBirth;
    }
}

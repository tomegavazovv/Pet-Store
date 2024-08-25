package com.example.petstore.domain;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Dog extends Pet {
    @Min(1)
    @Max(10)
    private Integer rating;

    public Dog(String name, String description, LocalDate dateOfBirth, Integer rating) {
        super(name, description, dateOfBirth);
        this.rating = rating;
    }

    @Override
    public Double getPrice() {
        return (double) this.calculateAge() + this.rating;
    }

    @Override
    public void setOwner(User owner) {
        this.owner = owner;
        String consoleMessage = "Woof, dog " + this.name + " has owner " + owner.getFirstName();
        System.out.println(consoleMessage);
    }

    @Override
    public String toString() {
        return "Dog:\n" + super.toString() + "\n\trating: " + rating;
    }

}

package com.example.petstore.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePetsRequest {
    private int count;

    public CreatePetsRequest(int count) {
        this.count = count;
    }
}
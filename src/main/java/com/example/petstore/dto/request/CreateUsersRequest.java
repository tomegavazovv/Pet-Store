package com.example.petstore.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateUsersRequest {
    private int count;

    public CreateUsersRequest(int count) {
        this.count = count;
    }
}
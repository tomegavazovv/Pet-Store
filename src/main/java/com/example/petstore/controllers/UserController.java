package com.example.petstore.controllers;

import com.example.petstore.domain.User;
import com.example.petstore.dto.request.CreateUsersRequest;
import com.example.petstore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    public static final int MAX_USERS_ALLOWED = 10;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-users")
    public ResponseEntity<?> createUsers(@RequestBody(required = false) CreateUsersRequest request) {
        int count = MAX_USERS_ALLOWED;
        if (request != null) {
            count = request.getCount();
        }

        try {
            List<User> createdUsers = userService.createUsers(Math.min(MAX_USERS_ALLOWED, count));
            return ResponseEntity.ok(createdUsers);
        } catch (IOException e) {
            return ResponseEntity.status(400).body("Error while creating new users. Try again later.");
        }

    }

    @GetMapping("/list-users")
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @PostMapping("/buy")
    public ResponseEntity<List<User>> buyPets(){
        return ResponseEntity.ok(userService.buy());
    }

}


package com.example.petstore.service;

import com.example.petstore.domain.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<User> createUsers(Integer count) throws IOException;

    List<User> listUsers();

    List<User> buy();
}

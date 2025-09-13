package com.example.demo.service;

import com.example.demo.model.User;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    CompletableFuture<List<User>> getAllUsersAsync();
}
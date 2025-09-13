package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Async // Marks this method to be executed on a separate thread from Spring's task executor.
    public CompletableFuture<List<User>> getAllUsersAsync() {
        logger.info("Executing method asynchronously on thread: {}", Thread.currentThread().getName());
        // Simulate a long-running task
        try { Thread.sleep(3000L); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        List<User> users = userRepository.findAll();
        return CompletableFuture.completedFuture(users);
    }
}
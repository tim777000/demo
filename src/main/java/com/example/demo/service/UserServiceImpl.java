package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Async // Marks this method to be executed on a separate thread from Spring's task executor.
    public CompletableFuture<List<User>> getAllUsersAsync() {
        log.info("Executing method asynchronously on thread: {}", Thread.currentThread().getName());
        // Simulate a long-running task
        try { Thread.sleep(3000L); } catch (InterruptedException e) {
            log.warn("Async task was interrupted", e);
            Thread.currentThread().interrupt();
        }
        List<User> users = userRepository.findAll();
        return CompletableFuture.completedFuture(users);
    }
}
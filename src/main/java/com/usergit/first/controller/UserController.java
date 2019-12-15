package com.usergit.first.controller;

import com.usergit.first.model.User;
import com.usergit.first.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@RequestMapping("/user")

public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public Flux<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC));
    }
    @GetMapping("/{userId}")
    public Mono<User> getUserById(@PathVariable String userId) {
        return userRepository.findById(userId);
    }

    @PutMapping("/{userId}")
    public Mono<User> updateUser(@PathVariable String userId, @RequestBody User user) {
        return userRepository.findById(userId).flatMap(existingUser -> {
            existingUser.setName(user.getName());
            existingUser.setAge(user.getAge());
            return userRepository.save(existingUser);
        });
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}


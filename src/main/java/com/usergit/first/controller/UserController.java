package com.usergit.first.controller;

import com.usergit.first.model.User;
import com.usergit.first.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Paths;

@RestController
@CrossOrigin
@RequestMapping("/user")

public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public Flux<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
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

    @DeleteMapping("/{userId}")
    public Mono<String> deleteUser(@PathVariable String userId) {
        return userRepository.findById(userId).flatMap(user -> userRepository.delete(user)).then(Mono.just("Deleted"));
    }

    @GetMapping("/download/{fileName:.+}")
    public Mono<Void> downloadFile(@PathVariable String fileName, ServerHttpResponse response) {
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=upload/" + fileName);
        response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
        File file = Paths.get("upload/" + fileName).toFile();
        return zeroCopyResponse.writeWith(file, 0, file.length());
    }
}


package com.usergit.first.security.service;

import com.usergit.first.model.User;
import com.usergit.first.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service

public class UserService {
    @Autowired
    UserRepository userRepository;

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username).switchIfEmpty(Mono.empty());
    }

    public Mono<User> saveUser(User user) {
        return userRepository.save(user);
    }
}

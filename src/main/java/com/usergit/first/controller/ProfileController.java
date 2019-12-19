package com.usergit.first.controller;

import com.usergit.first.model.Profile;
import com.usergit.first.model.User;
import com.usergit.first.repository.MessageRepository;
import com.usergit.first.repository.ProfileRepository;
import com.usergit.first.repository.UserRepository;
import com.usergit.first.security.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/profile")

public class ProfileController {
    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;


    public Mono<Profile> getProfile(Principal principal) {
        Mono<User> user = userRepository.findByUsername(principal.getName());
        return user.map(u -> u.getProfile());
    }

    @PostMapping
    public Mono createMessage(Principal principal, @RequestBody Message message) {
        Mono<User> userMono = userRepository.findByUsername(principal.getName());
        Mono<Profile> profileMono = userMono.map(u -> u.getProfile());
        Mono<Message> messageMono = messageRepository.save(message);
        Mono<Profile> profileMonoWithNewMessage = profileMono.zipWith(messageMono)
                .flatMap(t -> {
                    t.getT1().addMessageToList(t.getT2());
                   return profileRepository.save(t.getT1());
                });
        return userMono.zipWith(profileMonoWithNewMessage)
                .flatMap(t -> {
                    t.getT1().setProfile(t.getT2());
                    return userRepository.save(t.getT1());
                });
    }
}

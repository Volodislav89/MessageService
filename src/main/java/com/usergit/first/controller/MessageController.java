package com.usergit.first.controller;

import com.usergit.first.repository.MessageRepository;
import com.usergit.first.security.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@RequestMapping("/messages")

public class MessageController {
    @Autowired
    MessageRepository messageRepository;

    @GetMapping
    public Flux<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @GetMapping("/{messageId}")
    public Mono<Message> getMessageById(@PathVariable String messageId) {
        return messageRepository.findById(messageId);
    }

    @PostMapping
    public Mono<Message> createMessage(@RequestBody Message message) {
        return messageRepository.save(message);
    }

    @PutMapping("/{messageId}")
    public Mono<Message> updateMessage(@PathVariable String messageId, @RequestBody Message message) {
        return messageRepository.findById(messageId)
                .flatMap(existingMessage -> {
                    existingMessage.setContent(message.getContent());
                    return messageRepository.save(existingMessage);
                });
    }

    @DeleteMapping("/{messageId}")
    public Mono<String> deleteMessageById(@PathVariable String messageId) {
        return messageRepository.findById(messageId).flatMap(message -> messageRepository.delete(message)).then(Mono.just("Deleted"));
    }

    @DeleteMapping
    public Mono<String> deleteAllMessages() {
        messageRepository.deleteAll();
        return Mono.just("Delete all messages");
    }
}

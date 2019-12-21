package com.usergit.first.controller;

import com.usergit.first.model.Comment;
import com.usergit.first.repository.CommentRepository;
import com.usergit.first.repository.MessageRepository;
import com.usergit.first.security.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@RequestMapping("/comment")

public class CommentController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    MessageRepository messageRepository;

    @GetMapping
    public Flux<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @GetMapping("/{commentId}")
    public Mono<Comment> getCommentById(@PathVariable String commentId) {
        return commentRepository.findById(commentId);
    }

    @PostMapping
    public Mono<Comment> createComment(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }

    @PutMapping("/{commentId}")
    public Mono<Comment> updateComment(@PathVariable String commentId, @RequestBody Comment comment) {
        return commentRepository.findById(commentId).flatMap(ec -> {
            ec.setText(comment.getText());
            return commentRepository.save(ec);
        });
    }

    @DeleteMapping("/{commentId}")
    public Mono<String> deleteCommentById(@PathVariable String commentId) {
        return commentRepository.findById(commentId).flatMap(comment -> commentRepository.delete(comment)).then(Mono.just("Deleted"));
    }

    @DeleteMapping
    public Mono<String> deleteAllComments() {
        return commentRepository.deleteAll().then(Mono.just("All comments deleted"));
    }

    @PostMapping
    public Mono newMessageWithNewComment(@RequestBody Message message, @RequestBody Comment comment) {
        Mono<Message> messageMono = messageRepository.save(message);
        Mono<Comment> commentMono = commentRepository.save(comment);
        return messageMono.zipWith(commentMono).flatMap(tuple -> {
            tuple.getT1().addCommentToList(tuple.getT2());
            return messageRepository.save(tuple.getT1());
        });
    }

    @PostMapping("/{messageId}/{commentId}")
    public Mono messageWithComment(@PathVariable String messageId, @PathVariable String commentId) {
        Mono<Message> messageMono = messageRepository.findById(messageId);
        Mono<Comment> commentMono = commentRepository.findById(commentId);
        return messageMono.zipWith(commentMono).flatMap(tuple -> {
            tuple.getT1().addCommentToList(tuple.getT2());
            return messageRepository.save(tuple.getT1());
        });
    }

    @PostMapping("/{messageId}")
    public Mono messageWithNewComment(@PathVariable String messageId, @RequestBody Comment comment) {
        Mono<Message> messageMono = messageRepository.findById(messageId);
        Mono<Comment> commentMono = commentRepository.save(comment);
        return messageMono.zipWith(commentMono).flatMap(tuple -> {
            tuple.getT1().addCommentToList(tuple.getT2());
            return messageRepository.save(tuple.getT1());
        });
    }
}

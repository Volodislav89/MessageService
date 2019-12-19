package com.usergit.first.controller;

import com.usergit.first.model.Comment;
import com.usergit.first.repository.CommentRepository;
import com.usergit.first.repository.MessageRepository;
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
}

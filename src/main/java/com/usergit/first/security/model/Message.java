package com.usergit.first.security.model;

import com.usergit.first.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Message {
    @Id
    private String id;
    private String content;
    private List<Comment> comments = new ArrayList<>();

    public Message(String content) {
        this.content = content;
    }

    public List<Comment> addCommentToList(Comment comment) {
        this.comments.add(comment);
        return this.comments;
    }
}

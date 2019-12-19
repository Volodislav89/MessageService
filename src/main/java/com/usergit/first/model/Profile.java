package com.usergit.first.model;

import com.usergit.first.security.model.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor

public class Profile {
    @Id
    private String id;
    private List<Message> messages = new ArrayList<>();

    public List<Message> addMessageToList(Message message) {
        this.messages.add(message);
        return this.messages;
    }

    public List<Message> removeMessageFromList(Message message) {
        this.messages.remove(message);
        return this.messages;
    }

    public String removeAllMessages() {
        this.messages.removeAll(this.messages);
        return "Deleted";
    }
}

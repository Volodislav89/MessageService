package com.usergit.first.repository;

import com.usergit.first.security.model.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MessageRepository extends ReactiveMongoRepository<Message, String> {
}

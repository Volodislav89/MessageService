package com.usergit.first.repository;

import com.usergit.first.model.Profile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProfileRepository extends ReactiveMongoRepository<Profile, String> {
}

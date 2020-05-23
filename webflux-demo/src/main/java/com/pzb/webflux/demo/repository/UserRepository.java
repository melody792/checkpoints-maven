package com.pzb.webflux.demo.repository;

import com.pzb.webflux.demo.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
}

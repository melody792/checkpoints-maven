package com.pzb.webflux.demo.service;

import com.google.common.collect.Lists;
import com.pzb.webflux.demo.domain.UserDemo;
import com.pzb.webflux.demo.repository.UserRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Random;
import java.util.RandomAccess;

@Component
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Flux<UserDemo> getUsers() {
        UserDemo userDemo1 = new UserDemo();
        userDemo1.setAge(10);
        userDemo1.setName("bob");
        UserDemo userDemo2 = new UserDemo();
        userDemo2.setAge(10);
        userDemo2.setName("bob");

        Random random = new Random();
        Flux<UserDemo> userDemoFlux = Flux.fromIterable(Lists.newArrayList(userDemo1, userDemo2));
        if (random.nextInt(2) == 1) {
            userDemoFlux = userRepository.findAll();
        }
        return userDemoFlux;
    }
}

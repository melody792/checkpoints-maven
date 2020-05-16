package com.pzb.webflux.routerfunction.handlers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class TestHandler {
    public Mono<ServerResponse> testList(ServerRequest request) {
        Map<String, String> list = request.pathVariables();
        int random = (int) Math.floor(Math.random()*10);
        if (random > 5) {
            int x = random/0;
        }
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(list);
    }

}

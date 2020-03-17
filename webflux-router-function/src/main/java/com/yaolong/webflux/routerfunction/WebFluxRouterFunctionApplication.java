package com.yaolong.webflux.routerfunction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: webflux
 * @description:
 * @author: yaolong
 * @create: 2020-03-13 11:47
 **/
@EnableReactiveMongoRepositories
@SpringBootApplication
public class WebFluxRouterFunctionApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebFluxRouterFunctionApplication.class, args);
    }
}

package com.pzb.webflux.routerfunction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * @program: webflux
 * @description:
 * @author: pzb
 * @create: 2020-03-13 11:47
 **/
@EnableReactiveMongoRepositories
@SpringBootApplication
public class WebFluxRouterFunctionApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebFluxRouterFunctionApplication.class, args);
    }
}

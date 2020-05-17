package com.pzb.webflux.demo;

import com.pzb.webflux.demo.service.CsvHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: webflux
 * @description:
 * @author: pzb
 * @create: 2020-03-13 11:47
 **/
@RestController
@EnableReactiveMongoRepositories
@RequestMapping("/api/")
@SpringBootApplication
public class WebFluxDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebFluxDemoApplication.class, args);
    }

    @Bean
    public CsvHandler csvHandler() {
        return new CsvHandler();
    }

    @RequestMapping("test")
    public Mono<Map<String, Object>> test() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("msg", "成功");
        return Mono.just(map);
    }




}

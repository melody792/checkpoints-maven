package com.yaolong.webflux.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @program: webflux-demo
 * @description:
 * @author: yaolong
 * @create: 2020-03-16 14:23
 **/
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    /**
     * 普通案例
     *
     * @return
     */
    @GetMapping("/get1")
    public String get1() {
        log.info("get1 start");
        String s = creteData();
        log.info("get1 end");
        return s;
    }


    /**
     * mono案例
     *
     * @return
     */
    @GetMapping("/get2")
    public Mono<String> get2() {
        log.info("get2 start");
        Mono<String> result = Mono.fromSupplier(this::creteData);
        log.info("get2 end");
        return result;
    }

    /**
     * flux案例
     *
     * @return
     */
    @GetMapping(value = "/get3",produces = "text/event-stream")
    public Flux<String> get3() {
        Flux<String> result = Flux.fromStream(IntStream.range(1, 5).mapToObj(s -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "result--data" + s;
        }));
        return result;
    }

    private String creteData() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "this is String ";

    }

}

package com.pzb.webflux.client.controller;

import com.pzb.webflux.client.API.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: webflux
 * @description:
 * @author: pzb
 * @create: 2020-03-18 23:35
 **/
@RestController
public class TestController {

    final IUserService userService;

    public TestController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public void test(){
        //测试信息提取
//        userService.getAllUser();

//        userService.deleteUserById("2");
        userService.getUserById("5e6f6a4ebdca7c6fb13e9d0e").subscribe(System.out::println);
//        userService.deleteUserById("5e6f6a4ebdca7c6fb13e9d0e").subscribe(System.out::println);


//        //直接调用 实现调用rest接口的效果
//        Flux<User> allUser = userService.getAllUser();
//        allUser.subscribe(System.out::println);

//        userService.createUser(Mono.just(User.builder().age(10).name("asdas").build())).subscribe(System.out::println);

    }
}

package com.yaolong.webflux.client.API;


import com.yaolong.webflux.client.yaolong.ApiServer;
import com.yaolong.webflux.client.domain.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * @author yaolong
 */
@ApiServer("http://localhost:8080/user")
public interface IUserService {

    /**
     * 获取所有用户
     *
     * @return
     */
    @GetMapping("/getAllUser")
    Flux<User> getAllUser();

    /**
     * 根据用户id查找
     *
     * @return
     */
    @GetMapping("/findUserById/{id}")
    Mono<User> getUserById(@PathVariable("id") String id);

    /**
     * 根据id删除
     */
    @DeleteMapping("/deleteUserById/{id}")
    Mono<Void> deleteUserById(@PathVariable("id") String id);

    /**
     * 根据id删除
     */
    @PostMapping("/createUser")
    Mono<User> createUser(@RequestBody Mono<User> user);

}

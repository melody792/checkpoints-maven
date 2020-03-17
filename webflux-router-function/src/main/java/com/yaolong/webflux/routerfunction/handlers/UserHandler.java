package com.yaolong.webflux.routerfunction.handlers;

import com.yaolong.webflux.routerfunction.domain.User;
import com.yaolong.webflux.routerfunction.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @program: webflux
 * @description:
 * @author: yaolong
 * @create: 2020-03-17 19:21
 **/

@Component
public class UserHandler {
    private final UserRepository userRepository;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 获取所有用户
     * @param request
     * @return
     */
    public Mono<ServerResponse> getAllUser(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(this.userRepository.findAll(), User.class);
    }

    /**
     * 创建用户
     * @param request
     * @return
     */
    public Mono<ServerResponse> createUser(ServerRequest request){
        Mono<User> user = request.bodyToMono(User.class);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(this.userRepository.saveAll(user), User.class);
    }


    /**
     * 根据id查找用户
     * @param request
     * @return
     */
    public Mono<ServerResponse> findUserById(ServerRequest request){
        //获取请求体的id
        String id = request.pathVariable("id");
        return this.userRepository.findById(id).then(ServerResponse.ok().build()).switchIfEmpty(ServerResponse.notFound().build());
    }



    /**
     * 根据id删除用户
     * @param request
     * @return
     */
    public Mono<ServerResponse> deleteUserById(ServerRequest request){
        //获取请求体的id
        String id = request.pathVariable("id");
        return this.userRepository.findById(id).flatMap(this.userRepository::delete).then(ServerResponse.ok().build()).switchIfEmpty(ServerResponse.notFound().build());
    }
}

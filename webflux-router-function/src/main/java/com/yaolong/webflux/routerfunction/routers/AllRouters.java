package com.yaolong.webflux.routerfunction.routers;

import com.yaolong.webflux.routerfunction.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @program: webflux
 * @description: 路由API配置
 * @author: yaolong
 * @create: 2020-03-17 20:02
 **/
@Configuration
public class AllRouters {

    @Bean
    RouterFunction<ServerResponse> userRouters(UserHandler handler){
         return RouterFunctions
                .nest(RequestPredicates
                        //相当于@RequestMaping("/user")统一的路劲
                        .path("/user"),
                RouterFunctions
                        //相当于@GetMaping("/getAll")
                        .route(RequestPredicates.GET("/getAll"),handler::getAllUser))
                        //相当于@PostTMaping("/createUser")

                        .andRoute(RequestPredicates.POST("/createUser").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::createUser)
                        //相当于@GetMaping("/findUserById/{id}")

                        .andRoute(RequestPredicates.GET("/findUserById/{id}"),handler::findUserById)
                        //相当于@DleteTMaping("/deleteUserById/{id}")

                        .andRoute(RequestPredicates.DELETE("/deleteUserById/{id}"),handler::deleteUserById);

    }
}

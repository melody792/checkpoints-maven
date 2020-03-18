package com.yaolong.webflux.client.yaolong.resthandlers;

import com.yaolong.webflux.client.yaolong.bean.MethodInfo;
import com.yaolong.webflux.client.yaolong.bean.ServerInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * @program: webflux
 * @description: webflux客户端rest调用处理
 * @author: yaolong
 * @create: 2020-03-18 23:32
 **/
public class WebClientRestHandler implements RestHandler {

    private WebClient client;

    @Override
    public void init(ServerInfo serverInfo) {
        this.client = WebClient.create(serverInfo.getUrl());
    }


    @Override
    public Object invokeRest(MethodInfo methodInfo) {

        Object result = null;
        System.out.println("================================配置http请求---start================================");
        //发送的请求
        RequestBodyUriSpec method = this.client
                //请求的方法类型
                .method(methodInfo.getMethod());



        //判断请求路径是否有参数
        RequestBodySpec uri = null;
        if (methodInfo.getParams()!=null){
             //请求的路劲和参数
            uri = method.uri(methodInfo.getUrl(), methodInfo.getParams());
        }else {
            //请求的路劲
            uri = method.uri(methodInfo.getUrl());
        }



        //以json格式发送
        RequestBodySpec accept = uri.accept(MediaType.APPLICATION_JSON);


        //判断是否有body
        ResponseSpec retrieve;
        if (methodInfo.getBody() != null) {
            retrieve = accept.body(methodInfo.getBody(), methodInfo.getBodyElementType()).retrieve();
        } else {
            //发送请求
            retrieve = accept.retrieve();
        }

        //异常处理
        retrieve.onStatus(httpStatus -> httpStatus.value() == 404, clientResponse -> Mono.just(new RuntimeException("NOT FOUNT")));


        //处理body
        //判断返回是mono还是flux
        if (methodInfo.isReturnFlux()) {
            result = retrieve.bodyToFlux(methodInfo.getReturnElementType());
        } else {
            result = retrieve.bodyToMono(methodInfo.getReturnElementType());
        }

        System.out.println("================================配置http请求---end================================");


        return result;
    }


}

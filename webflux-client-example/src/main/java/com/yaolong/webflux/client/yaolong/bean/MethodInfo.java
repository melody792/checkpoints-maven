package com.yaolong.webflux.client.yaolong.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @program: webflux
 * @description: 方法信息
 * @author: yaolong
 * @create: 2020-03-18 22:14
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodInfo {

    /**
     * 请求的URL
     */
    private String url;

    /**
     * 请求的方法
     */
    private HttpMethod method;

    /**
     * 请求的参数（URL）
     */
    private Map<String, Object> params;

    /**
     * 请求体
     */
    private Mono body;

    /**
     * 请求体类型
     */
    private Class<?> bodyElementType;


    /**
     * 返回的是flux还是mono
     */
    private boolean returnFlux;

    /**
     * 返回的对象类型
     */
    private Class<?> returnElementType;


}

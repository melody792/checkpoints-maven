package com.pzb.webflux.client.pzb.proxys;

import com.pzb.webflux.client.pzb.ApiServer;
import com.pzb.webflux.client.pzb.resthandlers.RestHandler;
import com.pzb.webflux.client.pzb.resthandlers.WebClientRestHandler;
import com.pzb.webflux.client.pzb.bean.MethodInfo;
import com.pzb.webflux.client.pzb.bean.ServerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * @program: webflux
 * @description: jdk动态代理
 * @author: pzb
 * @create: 2020-03-18 19:29
 **/
@Slf4j
public class JDKProxyCreator implements ProxyCreator {
    @Override
    public Object createProxy(Class<?> type) {
        log.info("createProxy:" + type);
        //根据接口的到API服务器接口
        ServerInfo serverInfo = extractServerInfo(type);

        log.info("serverInfo :" + serverInfo);

        //给每个代理类一个实现（不同的方式调用）
        RestHandler restHandler = new WebClientRestHandler();

        //初始化服务器信息
        restHandler.init(serverInfo);
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{type}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                //根据方法和参数得到调用信息
                MethodInfo methodInfo = extractMethodInfo(method, args);

                log.info("methodInfo : " + methodInfo);
                //调用rest
                return restHandler.invokeRest(methodInfo);
            }

            /**
             * 获取类中方法的参数和请求类型（GET,POST,DELETE,PUT）、请求的路劲
             * @param method
             * @param args
             * @return
             */
            private MethodInfo extractMethodInfo(Method method, Object[] args) {

                MethodInfo methodInfo = new MethodInfo();

                //得到请求的URL和请求方法类型
                extractUrlAndHttpMethodType(method, methodInfo);

                //获取方法中的注解参数,和body
                extractParameterAndBody(method, args, methodInfo);

                //提取对象返回信息
                extractReturnInfo(method,methodInfo);


                return methodInfo;
            }

            /**
             * 提取对象返回信息
             * @param method
             * @param methodInfo
             */
            private void extractReturnInfo(Method method, MethodInfo methodInfo) {

                //返回flux还是Mono
                //isAssignableFrom判断返回的类型是否某个的子类
                //注意，instanceof判断的是实例的类型
                boolean assignableFrom = method.getReturnType().isAssignableFrom(Flux.class);
                methodInfo.setReturnFlux(assignableFrom);

                //返回的对象的实际类型
                Class<?> elementType = extractElementType(method.getGenericReturnType());
                methodInfo.setReturnElementType(elementType);

            }

            /**
             * 获取方法中的注解参数,和body
             * @param method
             * @param args
             * @param methodInfo
             */
            private void extractParameterAndBody(Method method, Object[] args, MethodInfo methodInfo) {
                Parameter[] parameters = method.getParameters();
                HashMap<String, Object> params = new HashMap<>();
                for (int i = 0; i < parameters.length; i++) {
                    //如果是@PathVariable("/{id}") 则取出set在methodInfo中
                    PathVariable annoPath = parameters[i].getAnnotation(PathVariable.class);
                    if (annoPath != null) {
                        params.put(annoPath.value(), args[i]);
                        System.out.println("参数"+annoPath.value()+"为:-----"+args[i]);
                        methodInfo.setParams(params);
                    }


                    //如果是@RequestBody 则取出set在methodInfo中
                    RequestBody annoBody = parameters[i].getAnnotation(RequestBody.class);
                    if (annoBody != null) {
                        methodInfo.setBody((Mono) args[i]);
                        //请求对象的实际类型
                        methodInfo.setBodyElementType(extractElementType(parameters[i].getParameterizedType()));
                    }
                }
            }

            /**
             * 得到请求的URL和请求方法类型
             * @param method
             * @param methodInfo
             */
            private void extractUrlAndHttpMethodType(Method method, MethodInfo methodInfo) {
                Annotation[] annotations = method.getAnnotations();
                for (Annotation annotation : annotations) {

                    //GET
                    if (annotation instanceof GetMapping) {
                        GetMapping getMapping = (GetMapping) annotation;
                        methodInfo.setUrl(getMapping.value()[0]);
                        methodInfo.setMethod(HttpMethod.GET);
                    }
                    //POST
                    else if (annotation instanceof PostMapping) {
                        PostMapping postMapping = (PostMapping) annotation;
                        methodInfo.setUrl(postMapping.value()[0]);
                        methodInfo.setMethod(HttpMethod.POST);
                    }
                    //Delete
                    else if (annotation instanceof DeleteMapping) {
                        DeleteMapping deleteMapping = (DeleteMapping) annotation;
                        methodInfo.setUrl(deleteMapping.value()[0]);
                        methodInfo.setMethod(HttpMethod.DELETE);
                    }
                    //PUT
                    else if (annotation instanceof PutMapping) {
                        PutMapping putMapping = (PutMapping) annotation;
                        methodInfo.setUrl(putMapping.value()[0]);
                        methodInfo.setMethod(HttpMethod.PUT);
                    }
                }
            }
        });
    }

    /**
     * 返回的对象的实际类型
     * @param genericReturnType
     * @return
     */
    private Class<?> extractElementType(Type genericReturnType) {
        Type[] actualTypeArguments =
                ((ParameterizedType) genericReturnType)
                .getActualTypeArguments();

        return (Class<?>) actualTypeArguments[0];

    }

    /**
     * 获取服务信息
     *
     * @param type
     * @return
     */
    private ServerInfo extractServerInfo(Class<?> type) {
        ServerInfo serverInfo = new ServerInfo();

        //获取注解ApiServer并得到注解的信息，保存到ServerInfo中
        ApiServer annotation = type.getAnnotation(ApiServer.class);
        serverInfo.setUrl(annotation.value());
        return serverInfo;
    }
}

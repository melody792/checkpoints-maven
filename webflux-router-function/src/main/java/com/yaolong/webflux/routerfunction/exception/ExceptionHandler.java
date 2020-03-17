package com.yaolong.webflux.routerfunction.exception;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @program: webflux
 * @description: 异常处理
 * @author: yaolong
 * @create: 2020-03-17 21:32
 **/
@Component
@Order(-2)
public class ExceptionHandler implements WebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        //得到响应
        ServerHttpResponse response = serverWebExchange.getResponse();
        //设置响应头400
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        //设置返回类型
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        //异常信息
        String exceptionMsg = toStr(throwable);

        DataBuffer body = response.bufferFactory().wrap(exceptionMsg.getBytes());
        return response.writeWith(Mono.just(body));
    }

    /**
     * 将异常转换为字符串
     * @param ex
     * @return
     */
    private String toStr(Throwable ex) {
        //已知异常
        if (ex instanceof CheckException){
            CheckException checkException = (CheckException)ex;

            return checkException.getFiledId()+":（非法）invalid value"+checkException.getFiledName();

        }else {
            //位置异常打印堆栈，定位方便
            ex.printStackTrace();
            return ex.toString();
        }
    }
}

package com.yaolong.webflux.demo.advice;

import com.yaolong.webflux.demo.exception.CheckException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Arrays;
import java.util.Optional;

/**
 * @program: webflux-demo
 * @description: 异常处理切面
 * @author: yaolong
 * @create: 2020-03-17 15:59
 **/
@ControllerAdvice
public class CheckAdvice {

    /**
     * 验证异常处理
     *
     * @param e
     * @return
     */
    //当触发这个异常时进行返回处理
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleBindException(MethodArgumentNotValidException e) {
        return new ResponseEntity<String>(toStr(e), HttpStatus.BAD_REQUEST);
    }

    /**
     * 把校验的异常转换为字符串
     *
     * @param e
     * @return
     */
    private String toStr(MethodArgumentNotValidException e) {
        return e
                //异常流化
                .getBindingResult().getFieldErrors().stream()
                //处理集合格式
                .map(error -> error.getField() + ":" + error.getDefaultMessage())
                //转换为字符串
                .reduce("", (str1, str2) -> str1 + "\n" + str2);
    }


    /**
     * 校验非法用户名处理
     * @param e
     * @return
     */
    @ExceptionHandler(CheckException.class)
    public ResponseEntity<String> handleBindException(CheckException e) {
        return new ResponseEntity<String>(toStr(e), HttpStatus.BAD_REQUEST);
    }


    /**
     * 把校验的异常转换为字符串
     *
     * @param e
     * @return
     */
    private String toStr(CheckException e) {
        return e.getFiledId()+"错误的值:"+e.getFiledName();
    }


}



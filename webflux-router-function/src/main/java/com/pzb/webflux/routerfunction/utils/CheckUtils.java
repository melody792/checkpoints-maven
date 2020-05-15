package com.pzb.webflux.routerfunction.utils;

import com.pzb.webflux.routerfunction.exception.CheckException;

import java.util.stream.Stream;

/**
 * @program: webflux-demo
 * @description: 检查工具类
 * @author: pzb
 * @create: 2020-03-17 18:22
 **/
public class CheckUtils {

    private static final String[] INVALID_NAME= {"傻逼","草拟吗","草拟吗","你妈的","麻痹","SB"};

    /**
     * 校验名字是否合法不合法抛出CheckException
     * @param value
     */
    public static void checkName(String value){
        Stream.of(INVALID_NAME).filter(name-> name.equals(value)).findAny().ifPresent(name->{
            throw new CheckException("name",name);
        });
    }
}

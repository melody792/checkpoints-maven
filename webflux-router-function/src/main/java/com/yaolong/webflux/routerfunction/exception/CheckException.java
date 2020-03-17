package com.yaolong.webflux.routerfunction.exception;

import lombok.Data;

/**
 * @program: webflux-demo
 * @description: 校验异常
 * @author: yaolong
 * @create: 2020-03-17 18:12
 **/
@Data
public class CheckException extends RuntimeException{

    static final long serialVersionUID = 1L;


    /**
     * 出错的id
     */
    private String filedId;

    /**
     * 不合法的名字
     */
    private String filedName;

    public CheckException() {
        super();
    }

    public CheckException(String message) {
        super(message);
    }

    public CheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckException(Throwable cause) {
        super(cause);
    }

    public CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CheckException(String filedId, String filedName) {
       this.filedId = filedId;
       this.filedName = filedName;
    }
}

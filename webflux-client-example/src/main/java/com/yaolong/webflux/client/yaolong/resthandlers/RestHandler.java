package com.yaolong.webflux.client.yaolong.resthandlers;

import com.yaolong.webflux.client.yaolong.bean.MethodInfo;
import com.yaolong.webflux.client.yaolong.bean.ServerInfo;

/**
 * @program: webflux
 * @description: rest调用方式接口
 * @author: yaolong
 * @create: 2020-03-18 22:20
 **/
public interface RestHandler {


    /**
     * 初始化服务器信息
     *
     * @param serverInfo
     */
    void init(ServerInfo serverInfo);

    /**
     * 调用rest请求，返回结果
     *
     * @return
     */
    Object invokeRest(MethodInfo methodInfo);
}

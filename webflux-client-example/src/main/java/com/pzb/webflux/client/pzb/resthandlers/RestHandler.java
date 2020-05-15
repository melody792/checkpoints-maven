package com.pzb.webflux.client.pzb.resthandlers;

import com.pzb.webflux.client.pzb.bean.MethodInfo;
import com.pzb.webflux.client.pzb.bean.ServerInfo;

/**
 * @program: webflux
 * @description: rest调用方式接口
 * @author: pzb
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

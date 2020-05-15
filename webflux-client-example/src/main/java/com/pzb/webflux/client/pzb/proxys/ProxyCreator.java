package com.pzb.webflux.client.pzb.proxys;

/**
 * @program: webflux
 * @description: 创建代理接口
 * @author: pzb
 * @create: 2020-03-18 18:39
 **/

/**
 * 创建代理的工厂方法
 * @author pzb
 */
public interface ProxyCreator {

    /**
     * 创建代理的工厂方法
     * @param type
     * @return
     */
    Object createProxy(Class<?> type);
}

package com.yaolong.webflux.client;

import com.yaolong.webflux.client.API.IUserService;
import com.yaolong.webflux.client.yaolong.proxys.ProxyCreator;
import com.yaolong.webflux.client.yaolong.proxys.JDKProxyCreator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @program: webflux
 * @description: 客户端
 * @author: yaolong
 * @create: 2020-03-18 18:04
 **/
@SpringBootApplication
public class WebFluxClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebFluxClientApplication.class,args);
    }


    /**
     * 创建JDK代理工具类
     * @return
     */
    @Bean
    ProxyCreator jdkProxyCreator(){
        return new JDKProxyCreator();
    }

    @Bean
    FactoryBean<IUserService> userApi(ProxyCreator proxyCreator){
        return new FactoryBean<IUserService>() {
            @Override
            public IUserService getObject() throws Exception {
                return (IUserService) proxyCreator.createProxy(this.getObjectType());
            }

            @Override
            public Class<?> getObjectType() {
                return IUserService.class;
            }
        };
    }
}

package com.yaolong.webflux.client.yaolong.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: webflux
 * @description:
 * @author: yaolong
 * @create: 2020-03-18 19:39
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerInfo {

    /**
     * 服务器地址
     */
    private String url;
}

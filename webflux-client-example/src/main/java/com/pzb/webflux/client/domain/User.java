package com.pzb.webflux.client.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @program: webflux-demo
 * @description:
 * @author: pzb
 * @create: 2020-03-16 17:54
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;

    private String name;

    private int age;
}

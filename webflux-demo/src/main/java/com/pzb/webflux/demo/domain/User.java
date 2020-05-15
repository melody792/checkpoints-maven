package com.pzb.webflux.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;


/**
 * @program: webflux-demo
 * @description:
 * @author: pzb
 * @create: 2020-03-16 17:54
 **/
@Document(collection ="user" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    @NotBlank
    private String name;

    @Range(max = 100,min = 10)
    private int age;
}

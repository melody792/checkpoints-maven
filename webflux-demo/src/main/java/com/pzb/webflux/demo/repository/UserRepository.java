package com.pzb.webflux.demo.repository;

import com.pzb.webflux.demo.domain.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @program: webflux-demo
 * @description:
 * @author: pzb
 * @create: 2020-03-16 17:56
 **/
@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String> {

    /**
     * 根据年龄段查找
     * @param start
     * @param end
     * @return
     */
    Flux<User> findByAgeBetween(int start,int end);
    /**
     * 根据年龄段查找
     * @param start
     * @param end
     * @return
     */
    @Query("{\"age\":{$gte:\"10\",$lte:\"40\"}}")
    Flux<User> oldUser(int start,int end);


}

package com.yaolong.webflux.demo.controller;

import com.yaolong.webflux.demo.domain.User;
import com.yaolong.webflux.demo.repository.UserRepository;
import com.yaolong.webflux.demo.utils.CheckUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


/**
 * @program: webflux-demo
 * @description: 用户管理
 * @author: yaolong
 * @create: 2020-03-16 17:59
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository repository;

    /**
     * 构造器获取注入的Bean
     *
     * @param repository
     */
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * 以数组的形式一次性返回
     *
     * @return
     */
    @GetMapping("/getAllUser")
    public Flux<User> getAll() {
        return this
                .repository.findAll();
    }

    /**
     * 以流的形式一次性返回
     *
     * @return
     */
    @GetMapping(value = "/getStreamAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> getStreamAll() {
        return this
                .repository.findAll();
    }

    /**
     * 添加数据
     *
     * @param user
     * @return
     */
    @PostMapping("/createUser")
    public Mono<User> createUser(@Validated @RequestBody User user) {
        //spring data jap 里面，新增和修改的方法都是save，有id是修改，没有id是新增
        //校验name是否合法
        CheckUtils.checkName(user.getName());
        user.setId(null);
        return this
                .repository.save(user);

    }

    /**
     * 修改数据
     *
     * @param user
     * @return
     */
    @PutMapping("/updateUser")
    public Mono<ResponseEntity<User>> updateUser(@Validated @RequestBody User user) {
        //spring data jap 里面，新增和修改的方法都是save，有id是修改，没有id是新增
        //校验name是否合法
        CheckUtils.checkName(user.getName());
        return this
                .repository.findById(user.getId())
                .flatMap(u -> {
                    u.setName(user.getName());
                    u.setAge(user.getAge());
                    return this.repository.save(u);
                })
                .map(u -> new ResponseEntity<User>(HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/deleteUserById/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") String id) {

        //此方法返回值为Mono无法判断数据是否存在，不能直接使用
        //this.repository.deleteById(id);

        //正确的使用方法
        return this
                //先找到这个用户
                .repository.findById(id)
                //map不操作数据，值只转换数据
                //flatMap操作数据返回一个Mono(此处是接收到查询的用户再删除这个用户)
                .flatMap(user -> this.repository.deleteById(user.getId())
                        //成功返回一个mono
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                //失败返回一个mono
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    @GetMapping("/findUserById/{id}")
    public Mono<ResponseEntity<User>> findUserById(@PathVariable("id") String id) {
        return this
                .repository.findById(id)
                .map(user -> new ResponseEntity<User>(HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据年龄段查找
     *
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/findByAge/{start}/{end}")
    public Flux<User> findByAge(@PathVariable int start, @PathVariable int end) {
        return this
                .repository.findByAgeBetween(start, end);
    }

    /**
     * 根据年龄段查找
     * @param start
     * @param end
     * @return
     */
    @GetMapping(value = "/findStreamByAge/{start}/{end}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> findStreamByAge(@PathVariable int start, @PathVariable int end) {
        return this
                .repository.findByAgeBetween(start, end);
    }
    /**
     * 根据年龄段查找
     * @param start
     * @param end
     * @return
     */
    @GetMapping(value = "/oldUser/{start}/{end}")
    public Flux<User> oldUser(@PathVariable int start, @PathVariable int end) {
        return this
                .repository.oldUser(start, end);
    }
    /**
     * 根据年龄段查找
     * @param start
     * @param end
     * @return
     */
    @GetMapping(value = "/oldStreamUser/{start}/{end}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> oldStreamUser(@PathVariable int start, @PathVariable int end) {
        return this
                .repository.oldUser(start, end);
    }
}

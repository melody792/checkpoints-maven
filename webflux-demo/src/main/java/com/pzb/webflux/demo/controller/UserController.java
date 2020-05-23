package com.pzb.webflux.demo.controller;

import com.pzb.webflux.demo.domain.TaskInfo;
import com.pzb.webflux.demo.domain.UserDemo;
import com.pzb.webflux.demo.domain.UserFields;
import com.pzb.webflux.demo.repository.UserDemoRepository;
import com.pzb.webflux.demo.repository.UserRepository;
import com.pzb.webflux.demo.service.CsvHandler;
import com.pzb.webflux.demo.service.UserService;
import com.pzb.webflux.demo.utils.CheckUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * @program: webflux-demo
 * @description: 用户管理
 * @author: pzb
 * @create: 2020-03-16 17:59
 **/
@RestController
@RequestMapping("/user")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserDemoRepository repository;
    private final UserRepository userRepository;

    private final UserService userService;

    private final CsvHandler csvHandler;


    @RequestMapping(path = "/ingest/{tenantId}", method = RequestMethod.POST)
    public ResponseEntity ingestUser(@PathVariable String tenantId, @RequestPart("file") MultipartFile file) throws Exception {
        TaskInfo info = TaskInfo.builder().build();
        Flux.fromIterable(csvHandler.getUsers(file.getInputStream()))
                .map(u -> { // set client id in jwt
                    u.getAttributes().put(UserFields.ATTRIBUTE_CLIENT_ID, tenantId);
                    return u;
                })
                .flatMap(user -> {
                    info.setCount(info.getCount()+1);
                    if (info.getCount() == 3) {
                        int i = 1/0;
                    }
                    return userRepository.save(user);
                })
                .doOnError(ex -> {
                    info.setMessage(ex.getMessage());//会继续抛出异常,结束流
                })
                //.onErrorResume()
                .then().block();
        log.info("user ingestion completed");
        return ResponseEntity.ok(info);
    }

    @GetMapping("/getUsers")
    public Flux<UserDemo> getUsers() {
        return this.userService.getUsers();
    }

    /**
     * 构造器获取注入的Bean
     *
     * @param repository
     */
//    public UserController(UserRepository repository, CsvHandler csvHandler) {
//        this.repository = repository;
//        this.csvHandler = csvHandler;
//    }

    /**
     * 以数组的形式一次性返回
     *
     * @return
     */
    @GetMapping("/getAllUser")
    public Flux<UserDemo> getAll() {
        return this.repository.findAll();
    }

    /**
     * 以流的形式一次性返回
     *
     * @return
     */
    @GetMapping(value = "/getStreamAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserDemo> getStreamAll() {
        return this.repository.findAll();
    }

    /**
     * 添加数据
     *
     * @param userDemo
     * @return
     */
    @PostMapping("/createUser")
    public Mono<UserDemo> createUser(@Validated @RequestBody UserDemo userDemo) {
        //spring data jap 里面，新增和修改的方法都是save，有id是修改，没有id是新增
        //校验name是否合法
        CheckUtils.checkName(userDemo.getName());
        userDemo.setId(null);
        return this
                .repository.save(userDemo);

    }

    /**
     * 修改数据
     *
     * @param userDemo
     * @return
     */
    @PutMapping("/updateUser")
    public Mono<ResponseEntity<UserDemo>> updateUser(@Validated @RequestBody UserDemo userDemo) {
        //spring data jap 里面，新增和修改的方法都是save，有id是修改，没有id是新增
        //校验name是否合法
        CheckUtils.checkName(userDemo.getName());
        return this
                .repository.findById(userDemo.getId())
                .flatMap(u -> {
                    u.setName(userDemo.getName());
                    u.setAge(userDemo.getAge());
                    return this.repository.save(u);
                })
                .map(u -> new ResponseEntity<UserDemo>(HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<UserDemo>(HttpStatus.NOT_FOUND));
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
                .flatMap(userDemo -> this.repository.deleteById(userDemo.getId())
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
    public Mono<ResponseEntity<UserDemo>> findUserById(@PathVariable("id") String id) {
        return this
                .repository.findById(id)
                .map(userDemo -> new ResponseEntity<UserDemo>(HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<UserDemo>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据年龄段查找
     *
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/findByAge/{start}/{end}")
    public Flux<UserDemo> findByAge(@PathVariable int start, @PathVariable int end) {
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
    public Flux<UserDemo> findStreamByAge(@PathVariable int start, @PathVariable int end) {
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
    public Flux<UserDemo> oldUser(@PathVariable int start, @PathVariable int end) {
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
    public Flux<UserDemo> oldStreamUser(@PathVariable int start, @PathVariable int end) {
        return this
                .repository.oldUser(start, end);
    }
}

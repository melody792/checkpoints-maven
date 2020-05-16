# skills learning

## WebFlux
异常处理：
```java
.retrieve()
.onStatus(HttpStatus::isError, clientResponse -> {
    return Mono.error(new Exception(clientResponse.statusCode() + "error code"));
})
```


### webflux-domo
webflux demo程序

### webflux-router-function
web项目的另一种编码风格


## kafka
### spring cloud stream
### spring kafka

##
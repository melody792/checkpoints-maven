package com.pzb.webflux.demo.customTests;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.Exceptions;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;

public class WebfluxTest {

    /**
     * 来源： https://zhuanlan.zhihu.com/p/36272662
     */
    @Test
    public void testFlux_onErrorResume() {
        //1. 默认方法
        Flux<String> flux = Flux.just("0", "1", "2", "abc", "3")
                .map(i -> Integer.parseInt(i) + "")
                .onErrorResume(e -> Mono.just("input string is not a number ," + e.getMessage()));
        flux.log().subscribe(System.out::println);

        //2. 根据异常类型选择返回方法
        flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .onErrorResume(ArithmeticException.class, e -> Mono.just("ArithmeticException:" + e.getMessage()))
                .onErrorResume(NumberFormatException.class, e -> Mono.just("input string is not a number"))
                //如果上面列出的异常类型都不满足，使用默认方法
                .onErrorResume(e -> Mono.just(e.getMessage()));
        // 因为异常类型为NumberFormatException，此处应该打印字符串input string is not a number
        flux.log().subscribe(System.out::println);

        //3. 根据Predicate选择返回方法
        flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .onErrorResume(e -> e.getMessage().equals("For input string: \"abc\""),
                        e -> Mono.just("exception data is abc"))
                //onErrorResume可以和onErrorReturn混合使用
                .onErrorReturn("SystemException");
        //因为判断条件，此处应该打印exception data is abc
        flux.log().subscribe(System.out::println);
    }

    @Test
    public void testFlux_onErrorReturn() {
        //1. 根据异常类型进行判断
        Flux<Integer> flux = Flux.just(0)
                .map(i -> 1 / i)
                //ArithmeticException异常时返回1
                .onErrorReturn(NullPointerException.class, 0)
                .onErrorReturn(ArithmeticException.class, 1);
        //输出应该为1
        flux.log().subscribe(System.out::println);

        final String nullStr = null;
        //just不允许对象为null
        Flux<String> stringFlux = Flux.just("")
                .map(str -> nullStr.toString())
                //NullPointerException异常时返回字符串NullPointerException
                .onErrorReturn(NullPointerException.class, "NullPointerException")
                .onErrorReturn(ArithmeticException.class, "ArithmeticException");
        //输出应该为NullPointerException
        stringFlux.log().subscribe(System.out::println);

        //2. 根据Predicate进行判断
        AtomicInteger index = new AtomicInteger(0);
        Flux.just(0, 1, 2, 3)
                .map(i -> {
                    index.incrementAndGet();
                    return 1 / i;
                })
                .onErrorReturn(NullPointerException.class, 0)
                .onErrorReturn(e -> index.get() < 2, 1)
                //因为上一个onErrorReturn匹配了条件，所以异常传播被关闭，之后的
                //onErrorReturn不会再被触发
                .onErrorReturn(e -> index.get() < 1, 2)

                //因为异常类型为NumberFormatException，此处应打印1
                .log().subscribe(System.out::println);
    }

    @Test
    public void testFlux_doOnError() {
        //1. 默认doOnError方法
        Flux<String> flux = Flux.just("0", "1", "2", "abc","3")
                .map(i -> Integer.parseInt(i) + "")
                .doOnError(e -> e.printStackTrace())
                //.onErrorReturn("System exception")
                .doOnComplete(() -> System.out.println("complete..."));
        flux.log().subscribe(System.out::println);

        //2. 根据异常类型选择doError方法
        flux = Flux.just("0", "1", "2", "abc","3")
                .map(i -> Integer.parseInt(i) + "")
                .doOnError(RuntimeException.class, e -> {
                    System.err.println("发生了RuntimeException");
                    e.printStackTrace();
                })
                .doOnError(NumberFormatException.class, e -> {
                    System.err.println("发生了NumberFormatException");
                    e.printStackTrace();
                })
                .onErrorReturn("System exception");
        //因为异常类型为NumberFormatException，此处应打印字符串发生了NumberFormatException
        //又因为doOnError不会阻止异常传播，所以onErrorReturn会执行，返回字符串System exception
        flux.log().subscribe(System.out::println);

        //3. 根据Predicate选择doError方法
        //   注意doOnError不会阻止异常传播，所以onErrorReturn可以多次触发
        flux = Flux.just("0", "1", "2", "abc","3")
                .map(i -> Integer.parseInt(i) + "")
                .doOnError(e -> e instanceof Throwable, e -> {
                    System.err.println("异常类型为Throwable");
                })
                .doOnError(e -> e instanceof Exception, e -> {
                    System.err.println("同时异常类型为Exception");
                })
                .doOnError(e -> e instanceof NumberFormatException, e -> {
                    System.err.println("并且异常类型为NumberFormatException");
                })
                .doOnError(e -> e instanceof Error, e -> {
                    System.err.println("异常类型为Error");
                })
                .onErrorReturn("System exception");
        //因为异常类型为NumberFormatException，所以前面3个doOnError都会被调用
        flux.log().subscribe(System.out::println);
    }

    /**
     * 非受检异常会被Reactor传播，而受检异常必须被用户代码try catch，为了让受检异常被reactor的异常传播机制和异常处理机制支持，可以使用如下步骤处理：
     *
     * 使用 Exceptions.propagate将受检异常包装为非受检异常并重新抛出传播出去。
     * onError、error回调等异常处理操作获取到异常之后，可以调用Exceptions.unwrap取得原受检的异常。
     */
    @Test
    public void testCheckedExceptionHandle() {
        Flux<String> flux = Flux.just("abc", "def", "exception", "ghi")
                .map(s -> {
                    try {
                        return doSth(s);
                    } catch (FileNotFoundException e) {
                        // 包装并传播异常
                        throw Exceptions.propagate(e);
                    }
                }).doOnError(throwable -> {
                    throwable.printStackTrace();
                });
        //abc、def正常打印，然后打印 参数异常
        flux.subscribe(System.out::println,
                e -> {
                    //获取原始受检异常
                    Throwable sourceEx = Exceptions.unwrap(e);
                    //判断异常类型并处理
                    if (sourceEx instanceof FileNotFoundException) {
                        System.err.println(((FileNotFoundException) sourceEx).getMessage());
                    } else {
                        System.err.println("Other exception");
                    }
                });

    }

    public static String doSth(String str) throws FileNotFoundException {
        if ("exception".equals(str)) {
            throw new FileNotFoundException("参数异常");
        } else {
            return str.toUpperCase();
        }
    }

    @Test
    public void testRetry() throws InterruptedException {
        //默认异常retry
        Flux<String> flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .retry(2);
        flux.subscribe(newSub());

        //带条件判断的retry
        System.out.println("-------------------------------------------------");
        Thread.sleep(500);
        flux = Flux.just("0", "1", "2", "abc")
                .map(i -> Integer.parseInt(i) + "")
                .retry(1, e -> e instanceof Exception);

        flux.subscribe(newSub());
    }

    private static Subscriber<String> newSub() {
        return new BaseSubscriber<String>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("start");
                request(1);
            }

            @Override
            protected void hookOnNext(String value) {
                System.out.println("get value is " + Integer.parseInt(value));
                request(1);
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("Complete");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                System.err.println(throwable.getMessage());
            }
        };
    }
}

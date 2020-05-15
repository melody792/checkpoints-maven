package lambda.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @program: webflux
 * @description: 流的创建
 * @author: pzb
 * @create: 2020-03-13 16:39
 **/
public class CreateStream {
    public static void main(String[] args) {
        //集合创建

        List<String> list = new ArrayList<>();
        list.stream();
        list.parallelStream();

        //从数组创建
        Arrays.stream(new int[]{1,3,2,3});

        //创建数字流
        IntStream.of(1,2,3,45,6);
        IntStream.rangeClosed(1,10);

        //使用random创建无限流
        new Random().ints().limit(10);

        //自己产生的流
        Random random = new Random();


        Stream.generate(()->random.nextInt()).limit(20).forEach(System.out::println);
    }
}

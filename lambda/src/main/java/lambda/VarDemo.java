package lambda;

import java.util.function.Consumer;

/**
 * @program: webflux
 * @description: 类型推断
 * @author: yaolong
 * @create: 2020-03-13 16:01
 **/
public class VarDemo {
    public static void main(String[] args) {

        //此时的变量a是一个final类型的变量

        String a = "asdasd";
        Consumer<String> consumer = s->System.out.println(s + a);

        consumer.accept("12313");
    }
}

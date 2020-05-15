package lambda;

import java.util.function.Function;

/**
 * @program: webflux
 * @description: 级联表达式和科利华
 * @author: pzb
 * @create: 2020-03-13 16:09
 **/
public class CurryDemo {
    public static void main(String[] args) {
        Function<Integer, Function<Integer, Integer>> fu = x -> y -> x + y;

        System.out.println(fu.apply(1).apply(2));


        Function<Integer, Function<Integer, Function<Integer, Integer>>> fu2 = x -> y -> z -> x + y + z;
        System.out.println(fu2.apply(5).apply(4).apply(3));

    }
}

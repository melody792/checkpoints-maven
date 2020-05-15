package lambda;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * @program: webflux
 * @description:
 * @author: pzb
 * @create: 2020-03-13 15:32
 **/
@FunctionalInterface
interface IMath {
    public int add(int x, int y);
}

@FunctionalInterface
interface IMath2 {
    public int add(int x, int y);
}

public class TypeDemo {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        IMath a = creteLambda();
        System.out.println(a.add(1, 2));

        IMath[] b = {creteLambda()};

        Object c = (IMath) creteLambda();

        TypeDemo demo = new TypeDemo();
        demo.method((IMath) (x, y) -> x + y);
        demo.method((IMath2) (x, y) -> x + y);
    }

    public void method(IMath2 math2) {

    }

    public void method(IMath math) {
    }

    private static IMath creteLambda() {
        return (x, y) -> x + y;
    }
}

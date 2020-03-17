package lambda.stream;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

/**
 * @program: webflux
 * @description:
 * @author: yaolong
 * @create: 2020-03-13 16:24
 **/
public class Stream {

    public static void main(String[] args) {

        int[] ints = {1,2,3,4,5,6};

        System.out.println(Arrays.stream(ints).map(Stream::getIntUnaryOperator).sum());
    }

    private static int getIntUnaryOperator(int i) {
        return i+1;
    }

    private static IntUnaryOperator getIntUnaryOperator() {
        return a -> a + 1;
    }
}

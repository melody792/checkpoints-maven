package lambda.stream;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: webflux
 * @description: 终止流操作
 * @author: pzb
 * @create: 2020-03-13 18:15
 **/
public class TerminationStream {
    public static void main(String[] args) {

        String str = "my name is yinyuelong";

        //并行流
        // 输出：oeimg e ialynus nymyn 可以看出输出是混乱的
        str.chars().parallel().forEach(i-> System.out.print((char)i));
        System.out.println();

        //使用forEachOrdered
        // 输出：my name is yinyuelong
        str.chars().parallel().forEachOrdered(i-> System.out.println((char)i));

        //使用 collect 将数据收集到list集合中
        // 输出：[my, name, is, yinyuelong]
        List<String> collect = Stream.of(str.split(" ")).collect(Collectors.toList());
        System.out.println(collect);

        //使用reduce拼接字符串
        // 输出：my|name|is|yinyuelong
        Optional<String> reduce = Stream.of(str.split(" ")).reduce((s1, s2) -> s1 + "|" + s2);
        System.out.println(reduce.orElse(""));

        // 带初始值的reduce
        // 输出：|my|name|is|yinyuelong
        String reduce1 = Stream.of(str.split(" ")).reduce("", (s1, s2) -> s1 + "|" + s2);
        System.out.println(reduce1);

        //计算所有单词总数
        //输出：21
        Integer reduce2 = Stream.of(str.split(" ")).map(s -> s.length()).reduce(0, (s1, s2) -> s1 + s2);
        System.out.println(reduce2);

        //计算最大长度的数组
        //输出：yinyuelong
        Optional<String> max = Stream.of(str.split(" ")).max((s1,s2)->s1.length()-s2.length());
        System.out.println(max.get());

        //使用findFirst短路操作
        //输出：182163707
        OptionalInt findFirst = new Random().ints().findFirst();
        System.out.println(findFirst.getAsInt());
    }
}

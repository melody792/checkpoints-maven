package lambda.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @program: webflux
 * @description: 操作流-中间部分
 * @author: pzb
 * @create: 2020-03-13 17:10
 **/
public class OperationStream {
    public static void main(String[] args) {

        String str = "my name is yinyuelong";

        System.out.println("----------------------map----------------------");
        //打印每个单词的长度
        Stream.of(str.split(" ")).map(s -> s.length()).forEach(System.out::println);

        System.out.println("----------------------flatMap----------------------");
        //flatMap A元素下的B属性（是一个集合），最终得到所有A元素的所有B属性的集合
        Stream.of(str.split(" ")).flatMap(a -> a.chars().boxed()).forEach(s -> System.out.println((char) s.intValue()));

        //peek操作用于debug，是中间操作
        System.out.println("----------------------peek----------------------");
        Stream.of(str.split(" ")).peek(System.out::println).forEach(System.out::println);

        System.out.println("----------------------limit----------------------");
        //limit主要用于无限流,设置一个过滤器限制随机数的范围，limit表示限制随机数的个数，不写就是无限的打印
        new Random().ints().filter(i -> i > 10 && i < 100).limit(10).forEach(System.out::println);


        User u1 = new User(1, "a", 19);
        User u2 = new User(2, "b", 21);
        User u3 = new User(3, "c", 18);
        User u4 = new User(4, "d", 22);
        User u5 = new User(5, "e", 22);

        List<User> users = Arrays.asList(u1, u2, u3, u4, u5);

        // 过滤年龄大于19的再过滤id大于3的，将名字转换为大写，将子字母倒叙
        users.stream()
                //过滤条件
                .filter(user -> user.getAge() > 19)
                //过滤条件
                .filter(user -> user.getId() > 3)

                .map(s -> s.getUsername().toUpperCase())
                //相当于数据库的分页
                .limit(1)
                //倒叙
                .sorted((o1, o2) -> o2.compareTo(o1))
                .forEach(System.out::println);
    }

}
class User {
    private int id;
    private String username;
    private int age;

    public User(int id, String username, int age) {
        this.id = id;
        this.username = username;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

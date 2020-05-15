package lambda.stream;

        import java.util.Arrays;
        import java.util.IntSummaryStatistics;
        import java.util.List;
        import java.util.Map;
        import java.util.Set;
        import java.util.TreeMap;
        import java.util.TreeSet;
        import java.util.stream.Collectors;

/**
 * @program: webflux
 * @description: 收集器
 * @author: pzb
 * @create: 2020-03-15 13:03
 **/
public class StreamCollector {
    public static void main(String[] args) {
        User2 u1 = new User2(1, "a", 19);
        User2 u2 = new User2(2, "b", 21);
        User2 u3 = new User2(3, "c", 18);
        User2 u4 = new User2(4, "d", 22);
        User2 u5 = new User2(5, "e", 22);

        List<User2> users = Arrays.asList(u1, u2, u3, u4, u5);

        //使用收集器收集用户的年龄列表
        //map()中的参数可以使用方法引用User2::getAge尽量不要使用user->(user.getAge)。不会多生成一个类似于lambda$0这样的函数
        //运行结果: 用户的年龄列表为[19, 21, 18, 22, 22]
        List<Integer> collect = users.stream().map(User2::getAge).collect(Collectors.toList());
        System.out.println("用户的年龄列表为"+collect);
        System.out.println("----------------------------------------------------");

        //去重服Collectors.toSet()
        //运行结果：去重的用户的年龄列表为[18, 19, 21, 22]
        Set<Integer> collect2 = users.stream().map(User2::getAge).collect(Collectors.toSet());
        System.out.println("去重的用户的年龄列表为"+collect2);
        System.out.println("----------------------------------------------------");

        //收集指定的集合，Collectors.toCollection(TreeSet::new)，Collectors.toCollection(HashSet::new)
        Set<Integer> collect3 = users.stream().map(User2::getAge).collect(Collectors.toCollection(TreeSet::new));
        System.out.println("用户的年龄列表为"+collect3);
        System.out.println("----------------------------------------------------");

        //统计汇总信息
        //运行结果：年龄汇总信息:IntSummaryStatistics{count=5, sum=102, min=18, average=20.400000, max=22}
        IntSummaryStatistics collect1 = users.stream().collect(Collectors.summarizingInt(User2::getAge));
        System.out.println("年龄汇总信息:"+collect1);
        System.out.println("----------------------------------------------------");

        //分块
        //Collectors.partitioningBy(user2 -> user2.getAge() > 19)分块规则按照以年龄>19来区分
        //运行结果：{false=[User2{id=1, username='a', age=19},
        //                 User2{id=3, username='c', age=18}],
        //          true=[User2{id=2, username='b', age=21},
        //                User2{id=4, username='d', age=22},
        //                User2{id=5, username='e', age=22}]}
        Map<Boolean, List<User2>> collect4 = users.stream().collect(Collectors.partitioningBy(user2 -> user2.getAge() > 19));
        System.out.println(collect4);
        System.out.println("----------------------------------------------------");

        //分组
        //按年龄分组
        //运行结果：分组结果
        //              {18=[User2{id=3, username='c', age=18}],
        //              19=[User2{id=1, username='a', age=19}],
        //              21=[User2{id=2, username='b', age=21}],
        //              22=[User2{id=4, username='d', age=22}, User2{id=5, username='e', age=22}]}
        Map<Integer, List<User2>> collect5 = users.stream().collect(Collectors.groupingBy(User2::getAge));
        System.out.println("分组结果\n"+collect5);


        //分组（年龄个数列表）
        //按年龄分组
        //运行结果： 个数列表分组结果
        //      {18=1, 19=1, 21=1, 22=2}
        Map<Integer,Long> getCounts = users.stream().collect(Collectors.groupingBy(User2::getAge,Collectors.counting()));
        System.out.println("年龄个数列表分组结果\n"+getCounts);
    }

}
class User2 {
    private int id;
    private String username;
    private int age;

    public User2(int id, String username, int age) {
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

    @Override
    public String toString() {
        return "User2{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}


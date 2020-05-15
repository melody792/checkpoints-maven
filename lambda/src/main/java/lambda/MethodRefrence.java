package lambda;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @program: webflux
 * @description:
 * @author: pzb
 * @create: 2020-03-13 13:47
 **/

class Person {
    private String name = "小明";

    private int age;

    private int money;
    public Person() {
        System.out.println("这是无参数构造");
    }

    public Person(int money){
        System.out.println("这是有参数构造");
        this.money = money;
    }




    public static void foot(Person person) {
        System.out.println(person + "正在走路");
    }


    public int getAge(int age){
        System.out.println("小明现在"+age);
        return age;
    }

    public int setAge(int age){
        this.age =age;
        System.out.println("改变后的年龄"+this.age);
        return this.age;
    }
    public void setAges(int age){
        System.out.println("改变后的年龄21"+this.age);
        this.age =age;
    }

    @Override
    public String toString() {
        return name;
    }


}
public class MethodRefrence {
    public static void main(String[] args) {


        //静态方法引用
        Consumer<Person> consumer = Person::foot;

        Person person = new Person();
        consumer.accept(person);

        //普通方法引用
        Supplier<String> consumer2 = person::toString;
        System.out.println(consumer2.get());

//        Function<Integer,Integer> function = person::getAge;
//        UnaryOperator<Integer> function = person::getAge;
        IntUnaryOperator function = person::getAge;
        System.out.println(function.applyAsInt(10));




        //this关键字
        BiFunction<Person,Integer,Integer> consumer1 = Person::setAge;
        consumer1.apply(person,15);
        BiConsumer<Person,Integer> consumer3 = Person::setAge;
        consumer3.accept(person,12);


        //构造器引用
        //无参数
        Supplier<Person> consumer4 = Person::new;
        System.out.println(consumer4.get());

        //有参
        Function<Integer,Person> function1 = Person::new;
        System.out.println(function1.apply(10));




    }
}

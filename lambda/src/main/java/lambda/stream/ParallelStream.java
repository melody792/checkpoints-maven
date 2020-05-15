package lambda.stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @program: webflux
 * @description: 并行流测试
 * @author: pzb
 * @create: 2020-03-14 21:48
 **/
public class ParallelStream {
    public static void main(String[] args) {

        //调用一个parallel产生并行流
        IntStream.range(1,100).parallel().peek(ParallelStream::debug).count();

        //线并行再串行 无法实现，一般实现的是串行，也就是最以最后的一个标准进行
        IntStream.range(1,100)
                .sequential().peek(ParallelStream::debug2)
                .parallel().peek(ParallelStream::debug)
                .count();

       //并行流使用的线程池：ForkJoinPool.commonPool-worker
        //默认的线程数为当前的cup个数
        //使用这个属性可以修改默认线程数
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","3");
        IntStream.range(1,100).parallel().peek(ParallelStream::debug).count();

        //创建自己的线程次不使用默认的线程池，防止任务被阻塞
        //线程名字：ForkJoinPool-1-worker
        ForkJoinPool pool = new ForkJoinPool(20);
        pool.submit(()->IntStream.range(1,100).parallel().peek(ParallelStream::debug).count());
        pool.shutdown();
        synchronized (pool){
            try {
                pool.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public static void debug(int i){
        System.out.println(Thread.currentThread().getName()+"debug"+i);

    } public static void debug2(int i){
        System.out.println("debug2"+i);

        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
    }

}

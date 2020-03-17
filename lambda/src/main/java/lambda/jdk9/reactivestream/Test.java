//package lambda.jdk9.reactivestream;
//
//import java.util.concurrent.Flow;
//import java.util.concurrent.SubmissionPublisher;
//import java.util.concurrent.TimeUnit;
//
///**
// * @program: webflux
// * @description:
// * @author: yaolong
// * @create: 2020-03-15 15:21
// **/
//public class Test {
//    public static void main(String[] args) {
//
//        //创建发布者
//        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
//        //创建订阅者
//        Flow.Subscriber<String> subscriber = new Flow.Subscriber<>() {
//
//            private Flow.Subscription subscription;
//
//            @Override
//            public void onSubscribe(Flow.Subscription subscription) {
//                //维护关系
//                this.subscription = subscription;
//
//                //请求一条数据
//                subscription.request(1);
//            }
//
//            @Override
//            public void onNext(String item) {
//                //接收一个数据
//                System.out.println("接收到数据" + item);
//
//
//
//
//                //再次请求一条数据
//                subscription.request(1);
//
//                //不接收了
//                //this.subscription.cancel();
//            }
//
//
//            @Override
//            public void onError(Throwable throwable) {
//                //上面接收数据报错抛异常
//                throwable.printStackTrace();
//
//                //不接受数据
//                this.subscription.cancel();
//            }
//
//            @Override
//            public void onComplete() {
//                //在调用发布者的close方法时触发此方法
//                System.out.println("全部数据处理完成（发布者关闭）");
//            }
//        };
//
//        //订阅发布者
//        publisher.subscribe(subscriber);
//        String data = "有数据了！快接收";
//
//        publisher.submit(data);
//        publisher.submit(data);
//        publisher.submit(data);
//        publisher.submit(data);
//        publisher.submit(data);
//
//
//        //发布者设置一个消息让订阅者订阅此消息
//        publisher.close();
//
//
//        //延迟线程关闭时间，防止订阅者没有接收到消息就关闭
//        try {
//            Thread.currentThread().join(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}

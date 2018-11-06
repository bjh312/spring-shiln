package com.shiln.user.future;//package com.shiln.user.future;
//
//import java.util.concurrent.*;
//
///**
// * <p> 类描述：
// * <p> 创建人: baojunhu
// * <p> 创建时间: 2018/8/29 15:38
// * <p> 版权申明：Huobi All Rights Reserved
// */
//public class Test1 {
//    public static void main(String[] args) throws InterruptedException,ExecutionException{
//        ExecutorService executorService = Executors.newCachedThreadPool();
//
//        Future<String> future = executorService.submit(new Callable<String>(){
//
//            @Override
//            public String call() throws Exception {
//                TestService testService = new TestService();
//                return testService.getString();
//            }
//        });
//        System.out.println(future.get());
//    }
//
//
//}

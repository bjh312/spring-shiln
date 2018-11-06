package com.shiln.user.future;//package com.shiln.user.future;
//
//import java.util.concurrent.*;
//
///**
// * <p> 类描述：
// * <p> 创建人: baojunhu
// * <p> 创建时间: 2018/8/29 14:51
// * <p> 版权申明：Huobi All Rights Reserved
// */
//public class Test {
//
//    public static void main(String[] args) {
//        ExecutorService executor = Executors.newCachedThreadPool();
//        Task task = new Task();
//        //第一种方法Future
//        //Future<Integer> result = executor.submit(task);
//        //第二种方法FutureTask + ExecutorService
//        FutureTask<Integer> result = new FutureTask<Integer>(task);
//        executor.submit(result);
//        executor.shutdown();
//        //第三种方法FutureTask + Thread
//        //FutureTask<Integer> result = new FutureTask<Integer>(task);
//        //Thread thread = new Thread(result);
//        //thread.start();
//
//
//
////        try {
////            Thread.sleep(1000);
////        } catch (InterruptedException e1) {
////            e1.printStackTrace();
////        }
//
//        System.out.println("主线程在执行任务");
//
//        try {
//            System.out.println("task运行结果"+result.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("所有任务执行完毕");
//
//    }
//}

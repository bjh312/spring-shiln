package com.shiln.user.future;//package com.shiln.user.future;
//
//import java.util.concurrent.Callable;
//
///**
// * <p> 类描述：
// * <p> 创建人: baojunhu
// * <p> 创建时间: 2018/8/29 14:52
// * <p> 版权申明：Huobi All Rights Reserved
// */
//public class Task implements Callable<Integer> {
//
//    @Override
//    public Integer call() throws Exception {
//        System.out.println("子线程在进行计算");
//        Thread.sleep(3000);
//        int sum = 0;
//        for(int i=0;i<100;i++)
//            sum += i;
//        return sum;
//    }
//}

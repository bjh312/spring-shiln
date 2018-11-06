package com.shiln.bao;

import com.shiln.user.UserApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p> 类描述：
 * <p> 创建人: baojunhu
 * <p> 创建时间: 2018/11/1 17:26
 * <p> 版权申明：Huobi All Rights Reserved
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class CountDownLatchTest {

    private  final  static Logger logger = LoggerFactory.getLogger(UserApplication.class);

    @Test
    public void concurrenceTest(){
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        final CountDownLatch countDownLatch = new CountDownLatch(1000); // 相当于计数器，当所有都准备好了，再一起执行，模仿多并发，保证并发量
        final CountDownLatch countDownLatch2 = new CountDownLatch(1000); // 保证所有线程执行完了再打印atomicInteger的值

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try{
            for(int i=0;i<1000;i++){
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            countDownLatch.await(); //一直阻塞当前线程，直到计时器的值为0,保证同时并发
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        //每个线程增加1000次，每次加1
                        for (int j = 0; j < 1000; j++) {
                            atomicInteger.incrementAndGet();
                        }
                        countDownLatch2.countDown();
                    }
                });
                countDownLatch.countDown();
            }
            countDownLatch2.await();// 保证所有线程执行完
            System.out.println("==========================================================="+atomicInteger);
            executorService.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

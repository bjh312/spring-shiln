package com.shiln.bao;

import com.shiln.user.UserApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p> 类描述：
 * <p> 创建人: baojunhu
 * <p> 创建时间: 2018/9/8 18:35
 * <p> 版权申明：Huobi All Rights Reserved
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class CompletionTestExcutor {

    private static AtomicLong counter = new AtomicLong(0);

    @org.junit.Test
    public void getYun() throws ExecutionException, InterruptedException {
        List<Future<String>> list =new ArrayList<>();

        //List<FutureTask<String>> list =new ArrayList<>();
        ExecutorService executorService = Executors. newFixedThreadPool(10);
        //ExecutorService executorService = Executors. newCachedThreadPool();

        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);
        Long startTime = System.currentTimeMillis();
        //String str [] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","1","2","3","4","5","6","7","8","9","0"};
        //String str [] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        String str [] = {"p","q","r","2","7"};
        for(int a=0;a<str.length;a++){
            for(int b=0;b<str.length;b++){
                for(int c=0;c<str.length;c++){
                    for(int d=0;d<str.length;d++){
                        String string = str[a].concat(str[b]).concat(str[c]).concat(str[d]);
                        counter.incrementAndGet();
//                        FutureTask<String> ft = new FutureTask<>(new YuMingTask(string));
//                        list.add(ft);
//                        executorService.submit(ft);
                        list.add(completionService.submit(new YuMingTask(string)));
                    }
                }
            }
        }
        for (Future<String> tempFt : list){
            if(!"".equals(tempFt.get())){
                System.out.println(tempFt.get());
            }
        }
        executorService.shutdown();
        System.out.println(counter);
        Long endTime = System.currentTimeMillis();
        System.out.println("查询处理数据耗时：" + (endTime - startTime) + "毫秒");
    }
}

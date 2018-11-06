package com.shiln.bao;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.shiln.user.UserApplication;
import com.shiln.user.bean.User;
import com.shiln.user.util.HttpClientUtils;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * <p> 类描述：
 * <p> 创建人: baojunhu
 * <p> 创建时间: 2018/9/8 18:35
 * <p> 版权申明：Huobi All Rights Reserved
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    private static AtomicLong counter = new AtomicLong(0);

    @org.junit.Test
    public void getYun(){
        Long startTime = System.currentTimeMillis();
        //String str [] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","1","2","3","4","5","6","7","8","9","0"};
        //String str [] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        String str [] = {"p","q","r","2","7"};
        for(int a=0;a<str.length;a++){
            for(int b=0;b<str.length;b++){
                for(int c=0;c<str.length;c++){
                    for(int d=0;d<str.length;d++){
                        //System.out.println(str[a].concat(str[b]).concat(str[c]).concat(str[d]));
                        String string = str[a].concat(str[b]).concat(str[c]).concat(str[d]);
                        counter.incrementAndGet();
                        if(getIsvalid(string)){
                            System.out.println(string);
                        }
                    }
                }
            }
        }
        System.out.println(counter);
        Long endTime = System.currentTimeMillis();
        System.out.println("查询处理数据耗时：" + (endTime - startTime) + "毫秒");
    }

    public boolean getIsvalid(String yun){
        boolean flag = false;
        String url = "https://checkapi.aliyun.com/check/checkdomain?domain="+yun+".com&command&token=Y189b3b23a78aaf10779d24c59692f0ca&ua&currency&site&bid&_csrf_token&callback=jsonp_1536403350582_9145";
        try {
            String str = HttpClientUtils.get(url);
            int begin = str.indexOf("[");
            int end = str.indexOf("]");
            JSONObject obj = JSONObject.parseObject(str.substring(begin+1,end));
            String reString = obj.get("avail").toString();
            flag = reString.equals("1");
        }catch (IOException e){
            e.printStackTrace();
        }
        return flag;
    }

    @org.junit.Test
    public void getListsplit(){
        List<String> list = new ArrayList();
        for(int i=0;i<4120;i++){
                list.add(String.valueOf(i));
        }
        System.out.println(list);

        int flag = 1000;//每次取的数据

        int size = list.size();
        int temp = size / flag + 1;
        boolean special = size % flag == 0;
        List<String> cutList = null;
        for (int i = 0; i < temp; i++) {
            if (i == temp - 1) {
                if (special) {
                    break;
                }
                cutList = list.subList(flag * i, size);
            } else {
                cutList = list.subList(flag * i, flag * (i + 1));
            }
            System.out.println("第" + (i + 1) + "组：" + cutList.toString());
        }

    }

    @org.junit.Test
    public void getList(){
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setUserID(2L);
        user.setUserName("二哈");
        User user1 = new User();
        user1.setUserID(3L);
        user1.setUserName("三哈");
        User user2 = new User();
        user2.setUserID(4L);
        user2.setUserName("四哈");
        list.add(user);
        list.add(user);
        list.add(user1);
        list.add(user2);
        list.add(user2);
        System.out.println(list.size());

        List<User> list1 = list.stream().distinct().collect(Collectors.toList());
        System.out.println(list1.size());
        //List<String> list1 = new ArrayList<>();
        //list.forEach(v->list1.add(v.getUserID().toString()));
        //System.out.println(list1);
    }

    @org.junit.Test
    public void getCountDownLatch(){
        int threadCount = 10;
        final CountDownLatch latch = new CountDownLatch(threadCount);
        for(int i=0; i< threadCount; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程" + Thread.currentThread().getId() + "开始出发");
                    try{
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread().getId() + "已到达终点");

                    latch.countDown();
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("10个线程已经执行完毕！开始计算排名");

    }

    @org.junit.Test
    public void getMonitorMq() throws Exception{
        String queue_name = "bao-huobi-queues";
        ConnectionFactory factory = new ConnectionFactory();
        // amqp://userName:password@hostName:portNumber/virtualHost
        factory.setHost("39.106.28.99");
        factory.setUsername("bao");
        factory.setPassword("bao");
        factory.setPort(5672);
        Connection conn = factory.newConnection();
        // System.out.println(conn.getChannelMax());
        Channel channel = conn.createChannel();
        // System.out.println(channel.getChannelNumber());
        System.out.println("未消费的消息数: "+channel.messageCount(queue_name));
        System.out.println("队列的消费者个数: "+channel.consumerCount(queue_name));
        channel.close();
        conn.close();
    }

}

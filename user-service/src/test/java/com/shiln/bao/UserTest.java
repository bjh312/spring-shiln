package com.shiln.bao;

import com.shiln.user.UserApplication;
import com.shiln.user.bean.User;
import com.shiln.user.service.UserService;
import com.shiln.user.util.CacheUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * <p> 类描述：
 * <p> 创建人: baojunhu
 * <p> 创建时间: 2018/8/29 17:18
 * <p> 版权申明：Huobi All Rights Reserved
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@EnableCaching
public class UserTest {

    @Autowired
    private UserService userService;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Test
    public void getFutureTest() throws Exception{
        List<User> list = userService.getList();
        String userIDs = list.stream().map(v -> v.getUserID().toString()).collect(Collectors.joining(","));
        List<User> taskList;
        Map<Integer,List<User>> tasksMap = new HashMap<>();
        int length = list.size();
        /**将集合切分的段数**/
        int threadPool = 20;
        for(int index=0;index*threadPool<length;index++) {
            if ((index + 1) * threadPool > length) {
                taskList = list.subList(index * threadPool,length);
            } else {
                taskList = list.subList(index * threadPool, (index + 1) * threadPool);
            }
            tasksMap.put(index,taskList);
        }

        CompletionService<List<User>> completionService = new ExecutorCompletionService<>(taskExecutor);

        for(Map.Entry<Integer,List<User>> entry : tasksMap.entrySet()){
            completionService.submit(new Callable<List<User>>() {
                @Override
                public List<User> call() throws Exception {
                    List<User> userList = userService.getListParams(userIDs);
                    return userList;
                }
            });
        }

        List<User> rlist = new ArrayList<>();
        Future<List<User>> futures;
        for (int i=0;i<tasksMap.size();i++){
            futures = completionService.take();
            rlist.addAll(futures.get());
        }
        System.out.println(rlist.size());
    }

    @Test
    public void getCache(){
        //List<User> list = userService.getList();
        //CacheUtils.set("831",list.toString());
        //List<User> cacheList = CacheUtils.getStr("831");
        CacheUtils.set("11","xxxxaaa");
        System.out.println(CacheUtils.getStr("11"));
    }

    @Test
    public void getSortList(){
        List<User> list = new ArrayList<>();
        User user1 = new User();
        user1.setUserID(4L);
        user1.setUserName("b");
        User user2 = new User();
        user2.setUserID(2L);
        user2.setUserName("c");
        User user3 = new User();
        user3.setUserID(3L);
        user3.setUserName("a");
        list.add(user1);
        list.add(user2);
        list.add(user3);

        list.sort(Comparator.comparing(User::getUserName));
        System.out.println(list);

    }
}

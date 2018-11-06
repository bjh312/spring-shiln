package com.shiln.user.service;

import com.rabbitmq.client.AMQP;
import com.shiln.user.bean.User;
import com.shiln.user.config.Constants;
import com.shiln.user.mapper.UserMapper;
import com.shiln.user.util.AfterCommitExecutor;
import org.apache.ibatis.annotations.Param;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by baojunhu on 2018/7/20
 */

@Service
public class UserService {

    @Autowired
    public UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;


    @Autowired
    private AfterCommitExecutor afterCommitExecutor;

//    public UserService(AmqpTemplate amqpTemplate) {
//        this.amqpTemplate = amqpTemplate;
//    }

    /**
     * 查询列表
     * @return
     */
    public List<User> getList(){
        return userMapper.getList();
    }

    public User getUserByID(Long userID){
        return userMapper.getUserByID(userID);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(User user){
        userMapper.add(user);
//        afterCommitExecutor.execute(() ->{
           sendMessage(user);
//        });
    }

    /**
     * 发送消息
     * @param user
     */
    public void sendMessage(User user) {
        amqpTemplate.convertAndSend(Constants.EXCHANGE_NAME, Constants.ROUTING_KEY_NAME, user);
    }

    public List<User> getListParams(String userIDs){
        return userMapper.getListParams(userIDs);
    }

}

package com.shiln.order.service;

import com.shiln.order.bean.Order;
import com.shiln.order.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by baojunhu on 2018/7/20
 */

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public List<Order> getList(){
        return orderMapper.getList();
    }

    public Order getOrderByID(Long orderID){
        return orderMapper.getOrderByID(orderID);
    }

    public int add(Order order){
        return orderMapper.add(order);
    }

}

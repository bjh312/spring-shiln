package com.shiln.order.controller;

import com.shiln.order.bean.Order;
import com.shiln.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by baojunhu on 2018/7/20
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/getOrderList")
    public List<Order> getOrderList(){
        return orderService.getList();
    }

    @RequestMapping("/addOrder")
    public void addOrder(){
        Order order = new Order();
        order.setOrderName("测试1");
        order.setUserName("admin");
        order.setUserID(1L);
        order.setOrderAmount(BigDecimal.valueOf(100));
        order.setOrderRemark("测试订单");
        orderService.add(order);
    }




}

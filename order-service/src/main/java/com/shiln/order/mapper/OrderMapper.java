package com.shiln.order.mapper;

import com.shiln.order.bean.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by baojunhu on 2018/7/20.
 */


@Mapper
public interface OrderMapper {

    /**
     * 查询列表
     * @return
     */
    List<Order> getList();

    Order getOrderByID(Long orderID);

    int add(Order order);

}

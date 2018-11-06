package com.shiln.order.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by baojunhu on 2018/7/20.
 */

@Data
public class Order {

    private Long orderID;
    private Long userID;
    private String userName;
    private String orderName;
    private BigDecimal orderAmount;
    private String orderRemark;
    private Long createTime;

}

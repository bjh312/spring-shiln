package com.shiln.user.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p> 类描述：
 * <p> 创建人: baojunhu
 * <p> 创建时间: 2018/10/17 17:31
 * <p> 版权申明：Huobi All Rights Reserved
 */
public class AccountActionVo implements Serializable {

    private Long id;
    private BigDecimal interest;
    private BigDecimal amount;
    private BigDecimal create_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCreate_id() {
        return create_id;
    }

    public void setCreate_id(BigDecimal create_id) {
        this.create_id = create_id;
    }
}

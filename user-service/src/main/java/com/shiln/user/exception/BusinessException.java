package com.shiln.user.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * <p> 类描述：
 * <p> 创建人: baojunhu
 * <p> 创建时间: 2018/7/23 20:54
 * <p> 版权申明：Huobi All Rights Reserved
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String code = StringUtils.EMPTY;
    private String msg = StringUtils.EMPTY;

    public BusinessException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

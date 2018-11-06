package com.shiln.user.bean;

import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * Created by baojunhu on 2018/7/20.
 */
@Data
public class User {

    private Long userID;
    @NotNull(message = "用户名称不能为空")
    private String userName;
    private String alias;
    private String address;
    private Long telephone;
    private int sex;
    private Long card;
    private Long createTime;

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", alias='" + alias + '\'' +
                ", address='" + address + '\'' +
                ", telephone=" + telephone +
                ", sex=" + sex +
                ", card=" + card +
                ", createTime=" + createTime +
                '}';
    }


}

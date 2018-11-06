package com.shiln.user.mapper;

import com.shiln.user.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by baojunhu on 2018/7/20.
 */


@Mapper
public interface UserMapper {

    /**
     * 查询列表
     * @return
     */
    List<User> getList();

    User getUserByID(Long userID);

    int add(User user);

    List<User> getListParams(@Param("userIDs") String userIDs);

}

package com.shixun.mapper;

import com.shixun.pojo.entity.User;

/**
 * 用户持久化接口
 */
public interface UserMapper {


    /**
     * 用户信息新增
     *
     * @param userInsert 用户信息
     * @return int 受影响行数
     */
    int insert(User userInsert);


    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return int 受影响行数
     */
    int update(User user);

    /**
     * 根据不为空的查询信息查找用户
     *
     * @param user 参数
     * @return User
     */
    User getByActive(User user);

}

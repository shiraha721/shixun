package com.shixun.pojo.vo;

import com.shixun.pojo.entity.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息视图对象（VO）
 * 在 Message 实体基础上扩展用户信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageVO extends Message {

    /**
     * 用户名
     */
    private String userName;
    
    /**
     * 用户账号
     */
    private String userAccount;
    
    /**
     * 用户头像 URL
     */
    private String userAvatar;

}

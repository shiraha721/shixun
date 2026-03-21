package com.shixun.pojo.vo;

import com.shixun.pojo.entity.Interaction;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 互动行为信息VO类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InteractionVO extends Interaction {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 商品标题
     */
    private String productTitle;
}

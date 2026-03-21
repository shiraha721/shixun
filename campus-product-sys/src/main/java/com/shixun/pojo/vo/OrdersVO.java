package com.shixun.pojo.vo;

import com.shixun.pojo.entity.Orders;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单的出参类VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrdersVO extends Orders {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 商品标题
     */
    private String productTitle;
    /**
     * 商品图
     */
    private String productCover;
}

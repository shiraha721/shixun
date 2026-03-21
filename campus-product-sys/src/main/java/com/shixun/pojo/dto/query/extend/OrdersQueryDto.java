package com.shixun.pojo.dto.query.extend;

import com.shixun.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 订单的查询条件类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrdersQueryDto extends QueryDto {
    /**
     * 订单号
     */
    private String code;
    /**
     * 备注
     */
    private String detail;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 商品ID
     */
    private Integer productId;
    /**
     * 交易状态
     */
    private Boolean tradeStatus;
    /**
     * 退款状态
     */
    private Boolean refundStatus;
    /**
     * 商品ID列表
     */
    private List<Integer> productIds;
}

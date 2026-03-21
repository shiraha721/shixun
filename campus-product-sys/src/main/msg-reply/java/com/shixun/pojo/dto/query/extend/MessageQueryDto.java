package com.shixun.pojo.dto.query.extend;

import com.shixun.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息查询条件 DTO
 * 用于封装消息查询的分页和筛选参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MessageQueryDto extends QueryDto {
    
    /**
     * 用户 ID（筛选特定用户的消息）
     */
    private Integer userId;
    
    /**
     * 消息内容（支持模糊查询）
     */
    private String content;
}

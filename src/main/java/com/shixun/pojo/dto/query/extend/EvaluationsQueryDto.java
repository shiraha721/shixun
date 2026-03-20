package com.shixun.pojo.dto.query.extend;

import com.shixun.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 评论查询条件 DTO
 * 用于封装评论查询的分页和筛选参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EvaluationsQueryDto extends QueryDto {

    /**
     * 内容类型（如：product、order 等）
     */
    private String contentType;
    
    /**
     * 评论内容（支持模糊查询）
     */
    private String content;

}

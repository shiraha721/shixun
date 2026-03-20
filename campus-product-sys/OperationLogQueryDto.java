package com.shixun.pojo.dto.query.extend;

import com.shixun.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 操作日志查询条件类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OperationLogQueryDto extends QueryDto {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 描述
     */
    private String detail;
}

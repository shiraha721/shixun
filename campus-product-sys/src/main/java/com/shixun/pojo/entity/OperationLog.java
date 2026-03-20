package com.shixun.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 操作日志实体
 * 用来封装日志的 ID、用户 ID、操作描述、创建时间等信息
 * 是 AOP 切面保存日志、Mapper 层操作数据库的核心数据载体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationLog {
    /**
     * ID
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 描述
     */
    private String detail;
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

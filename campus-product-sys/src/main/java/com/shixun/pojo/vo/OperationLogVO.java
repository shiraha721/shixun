package com.shixun.pojo.vo;

import com.shixun.pojo.entity.OperationLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 操作日志出参类
 * 这是操作日志的 VO（View Object）类，继承自日志实体类 OperationLog，
 * 在原有日志字段（ID、用户 ID、操作描述、创建时间）基础上，
 * 新增了「用户名」「用户账号」字段，专门用来给前端返回展示用的日志数据，
 * 避免直接暴露数据库实体，同时丰富展示信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OperationLogVO extends OperationLog {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户账号
     */
    private String userAccount;
}

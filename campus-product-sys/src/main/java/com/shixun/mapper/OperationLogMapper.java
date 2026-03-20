package com.shixun.mapper;

import com.shixun.pojo.dto.query.extend.OperationLogQueryDto;
import com.shixun.pojo.entity.OperationLog;
import com.shixun.pojo.vo.OperationLogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作日志持久化接口
 * 这是日志模块的 Mapper 层接口，
 * 基于 MyBatis 框架定义了日志的新增、分页查询、总数统计、批量删除四个核心数据库操作方法，
 * Service 层调用这些方法就能完成日志的数据库读写，是 “业务逻辑” 到 “数据库” 的关键层。
 * 定义了 4 个核心方法
 */
public interface OperationLogMapper {

    /**
     * 新增
     *
     * @param operationLog 实体信息
     * @return int 受影响行数
     */
    int save(OperationLog operationLog); //新增日志到数据库

    /**
     * 分页查询信息
     *
     * @param operationLogQueryDto 分页查询参数
     * @return List<OperationLogVO>
     */
    List<OperationLogVO> query(OperationLogQueryDto operationLogQueryDto);
    //分页查询日志

    /**
     * 查询满足分页查询的记录总数
     *
     * @param operationLogQueryDto 分页查询参数
     * @return int 数据总数
     */
    int queryCount(OperationLogQueryDto operationLogQueryDto); //查询日志总数

    /**
     * 批量删除操作日志信息
     *
     * @param ids 用户ID集合
     */
    void batchDelete(@Param(value = "ids") List<Integer> ids); //批量删除日志

}

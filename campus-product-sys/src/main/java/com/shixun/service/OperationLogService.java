package com.shixun.service;

import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.OperationLogQueryDto;
import com.shixun.pojo.entity.OperationLog;
import com.shixun.pojo.vo.OperationLogVO;

import java.util.List;

/**
 * 操作日志的业务逻辑接口
 * 这是日志模块的 Service 层接口，
 * 定义了日志的「新增、批量删除、分页查询」三个核心业务方法，
 * 所有日志相关的业务逻辑（比如分页处理、权限校验、异常封装）都通过这个接口落地，
 * 是分层架构中 “业务逻辑层” 的核心规范，保证了代码的可扩展和可维护性。
 */
public interface OperationLogService {

    Result<String> save(OperationLog operationLog);

    Result<String> batchDelete(List<Integer> ids);

    Result<List<OperationLogVO>> query(OperationLogQueryDto operationLogQueryDto);

}

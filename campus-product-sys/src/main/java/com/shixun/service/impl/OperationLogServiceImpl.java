package com.shixun.service.impl;

import com.shixun.mapper.OperationLogMapper;
import com.shixun.pojo.api.ApiResult;
import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.OperationLogQueryDto;
import com.shixun.pojo.entity.OperationLog;
import com.shixun.pojo.vo.OperationLogVO;
import com.shixun.service.OperationLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 操作日志业务逻辑接口实现类
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Resource
    private OperationLogMapper operationLogMapper;

    /**
     * 新增
     *
     * @param operationLog 参数
     * @return Result<String> 后台通用返回封装类
     */
    @Override
    public Result<String> save(OperationLog operationLog) {
        operationLogMapper.save(operationLog);
        return ApiResult.success("操作日志新增成功");
    }

    /**
     * 删除
     *
     * @param ids 待删除ID集合
     * @return Result<String> 后台通用返回封装类
     */
    @Override
    public Result<String> batchDelete(List<Integer> ids) {
        operationLogMapper.batchDelete(ids);
        return ApiResult.success("操作日志删除成功");
    }

    /**
     * 查询
     *
     * @param operationLogQueryDto 查询参数
     * @return Result<List < OperationLogVO>> 后台通用返回封装类
     */
    @Override
    public Result<List<OperationLogVO>> query(OperationLogQueryDto operationLogQueryDto) {
        int totalCount = operationLogMapper.queryCount(operationLogQueryDto);
        List<OperationLogVO> categoryList = operationLogMapper.query(operationLogQueryDto);
        return ApiResult.success(categoryList, totalCount);
    }
}

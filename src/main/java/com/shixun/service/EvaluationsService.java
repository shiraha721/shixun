package com.shixun.service;

import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.EvaluationsQueryDto;
import com.shixun.pojo.entity.Evaluations;

import java.util.List;

/**
 * 评论服务接口
 */
public interface EvaluationsService {

    /**
     * 新增评论
     *
     * @param evaluations 评论信息
     * @return Result<Object> 操作结果
     */
    Result<Object> insert(Evaluations evaluations);

    /**
     * 查询指定内容的全部评论
     *
     * @param contentId   内容 ID
     * @param contentType 内容类型
     * @return Result<Object> 评论 VO 对象
     */
    Result<Object> list(Integer contentId, String contentType);

    /**
     * 分页查询评论数据
     *
     * @param evaluationsQueryDto 评论查询条件
     * @return Result<Object> 分页评论数据
     */
    Result<Object> query(EvaluationsQueryDto evaluationsQueryDto);

    /**
     * 批量删除评论
     *
     * @param ids 评论 ID 列表
     * @return Result<Object> 操作结果
     */
    Result<Object> batchDelete(List<Integer> ids);

    /**
     * 根据 ID 删除评论
     *
     * @param id 评论 ID
     * @return Result<Void> 操作结果
     */
    Result<Void> delete(Integer id);

    /**
     * 修改评论信息
     *
     * @param evaluations 评论信息
     * @return Result<Void> 操作结果
     */
    Result<Void> update(Evaluations evaluations);

}

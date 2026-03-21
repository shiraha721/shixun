package com.shixun.controller;

import com.shixun.aop.Pager;
import com.shixun.aop.Protector;
import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.EvaluationsQueryDto;
import com.shixun.pojo.entity.Evaluations;
import com.shixun.service.EvaluationsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论管理 Controller
 * 处理评论相关的 HTTP 请求
 */
@RestController
@RequestMapping("/evaluations")
public class EvaluationsController {

    @Resource
    private EvaluationsService evaluationsService;

    /**
     * 新增评论
     *
     * @param evaluations 评论信息
     * @return Result<Object> 操作结果
     */
    @Protector
    @PostMapping("/insert")
    public Result<Object> insert(@RequestBody Evaluations evaluations) {
        return evaluationsService.insert(evaluations);
    }

    /**
     * 修改评论
     *
     * @param evaluations 评论信息
     * @return Result<Void> 操作结果
     */
    @Protector
    @PutMapping("/update")
    public Result<Void> update(@RequestBody Evaluations evaluations) {
        return evaluationsService.update(evaluations);
    }

    /**
     * 查询指定内容下的全部评论
     *
     * @param contentId   内容 ID
     * @param contentType 内容类型
     * @return Result<Object> 评论列表及统计信息
     */
    @Protector
    @GetMapping("/list/{contentId}/{contentType}")
    public Result<Object> list(
            @PathVariable Integer contentId,
            @PathVariable String contentType) {
        return evaluationsService.list(contentId, contentType);
    }

    /**
     * 分页查询评论数据
     *
     * @param evaluationsQueryDto 评论查询条件
     * @return Result<Object> 分页评论数据
     */
    @Pager
    @PostMapping("/query")
    public Result<Object> query(@RequestBody EvaluationsQueryDto evaluationsQueryDto) {
        return evaluationsService.query(evaluationsQueryDto);
    }

    /**
     * 批量删除评论
     *
     * @param ids 评论 ID 列表
     * @return Result<Object> 操作结果
     */
    @PostMapping("/batchDelete")
    public Result<Object> batchDelete(@RequestBody List<Integer> ids) {
        return evaluationsService.batchDelete(ids);
    }

    /**
     * 根据 ID 删除评论
     *
     * @param id 评论 ID
     * @return Result<Void> 操作结果
     */
    @Protector
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        return evaluationsService.delete(id);
    }

}


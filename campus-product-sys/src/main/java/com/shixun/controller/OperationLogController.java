package com.shixun.controller;

import com.shixun.aop.Pager;
import com.shixun.aop.Protector;
import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.OperationLogQueryDto;
import com.shixun.pojo.vo.OperationLogVO;
import com.shixun.service.OperationLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 操作日志控制器
 * 这是日志模块的 Controller 层代码，主要提供两个接口：
 * 一是管理员专属的「日志批量删除」接口（带权限校验），
 * 二是「日志分页查询」接口（带自动分页处理），
 * 是前端和后端日志业务逻辑交互的桥梁。
 */
@RestController
@RequestMapping("/operation-log")
public class OperationLogController {

    @Resource
    private OperationLogService operationLogService;

    /**
     * 批量删除
     */
    @Protector(role = "管理员") //只有管理员能删除日志
    @PostMapping(value = "/batchDelete") //@PostMapping：接口请求方式为POST
    @ResponseBody  //接口方法：接收前端传的日志ID列表，返回删除结果（String类型的成功/失败提示）
    public Result<String> batchDelete(@RequestBody List<Integer> ids) {
        return operationLogService.batchDelete(ids);
    }
    //调用Service层的批量删除方法，把请求参数传给业务层，返回响应结果

    /**
     * 查询
     *
     * @param operationLogQueryDto 查询参数
     * @return Result<List < OperationLogVO>> 响应结果
     */
    @Pager //@Pager：分页注解（AOP切面），自动处理分页参数，不用手动写分页逻辑
    @PostMapping(value = "/query")
    @ResponseBody
    public Result<List<OperationLogVO>> query(@RequestBody OperationLogQueryDto operationLogQueryDto) {
        return operationLogService.query(operationLogQueryDto);  //调用Service层的查询方法，返回分页后的日志数据
    }
}


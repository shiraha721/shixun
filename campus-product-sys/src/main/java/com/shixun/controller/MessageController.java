package com.shixun.controller;

import com.shixun.aop.Pager;
import com.shixun.context.LocalThreadHolder;
import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.MessageQueryDto;
import com.shixun.pojo.vo.MessageVO;
import com.shixun.service.MessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息管理 Controller
 * 处理消息通知相关的 HTTP 请求
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 批量删除消息
     *
     * @param ids 消息 ID 列表
     * @return Result<Void> 操作结果
     */
    @PostMapping("/batchDelete")
    public Result<String> batchDelete(@RequestBody List<Integer> ids) {
        return messageService.batchDelete(ids);
    }

    /**
     * 将用户消息标记为已读
     *
     * @return Result<Void> 操作结果
     */
    @PostMapping("/setRead")
    public Result<String> setRead() {
        return messageService.setRead(LocalThreadHolder.getUserId());
    }

    /**
     * 分页查询消息数据
     *
     * @param messageQueryDto 消息查询条件
     * @return Result<List<MessageVO>> 分页消息数据
     */
    @Pager
    @PostMapping("/query")
    public Result<List<MessageVO>> query(@RequestBody MessageQueryDto messageQueryDto) {
        return messageService.query(messageQueryDto);
    }

    /**
     * 查询当前用户的消息列表
     * 自动从上下文中获取用户 ID
     *
     * @param messageQueryDto 消息查询条件
     * @return Result<List<MessageVO>> 用户消息数据
     */
    @PostMapping("/queryUser")
    public Result<List<MessageVO>> queryUser(@RequestBody MessageQueryDto messageQueryDto) {
        messageQueryDto.setUserId(LocalThreadHolder.getUserId());
        return messageService.query(messageQueryDto);
    }

}


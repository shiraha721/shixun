package com.shixun.service;

import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.MessageQueryDto;
import com.shixun.pojo.entity.Message;
import com.shixun.pojo.vo.MessageVO;

import java.util.List;

/**
 * 消息的业务逻辑接口
 */
public interface MessageService {

    /**
     * 保存消息
     *
     * @param message 消息实体
     * @return Result<String> 操作结果
     */
    Result<String> save(Message message);

    /**
     * 将用户消息标记为已读
     *
     * @param userId 用户 ID
     * @return Result<String> 操作结果
     */
    Result<String> setRead(Integer userId);

    /**
     * 批量删除消息
     *
     * @param ids 消息 ID 列表
     * @return Result<String> 操作结果
     */
    Result<String> batchDelete(List<Integer> ids);

    /**
     * 分页查询消息数据
     *
     * @param messageQueryDto 消息查询条件
     * @return Result<List<MessageVO>> 分页消息数据（包含总记录数）
     */
    Result<List<MessageVO>> query(MessageQueryDto messageQueryDto);

}

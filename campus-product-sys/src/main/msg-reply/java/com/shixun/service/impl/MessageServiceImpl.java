package com.shixun.service.impl;

import com.shixun.mapper.MessageMapper;
import com.shixun.pojo.api.ApiResult;
import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.MessageQueryDto;
import com.shixun.pojo.entity.Message;
import com.shixun.pojo.vo.MessageVO;
import com.shixun.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息业务逻辑接口实现类
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageMapper messageMapper;

    /**
     * 保存消息
     *
     * @param message 消息实体
     * @return Result<String> 操作结果
     */
    @Override
    public Result<String> save(Message message) {
        messageMapper.save(message);
        return ApiResult.success("消息新增成功");
    }

    /**
     * 将用户的所有消息标记为已读
     *
     * @param userId 用户 ID
     * @return Result<String> 操作结果
     */
    @Override
    public Result<String> setRead(Integer userId) {
        messageMapper.setRead(userId);
        return ApiResult.success("消息已读");
    }

    /**
     * 批量删除消息
     *
     * @param ids 消息 ID 列表
     * @return Result<String> 操作结果
     */
    @Override
    public Result<String> batchDelete(List<Integer> ids) {
        messageMapper.batchDelete(ids);
        return ApiResult.success("消息删除成功");
    }

    /**
     * 分页查询消息数据
     *
     * @param messageQueryDto 消息查询条件
     * @return Result<List<MessageVO>> 分页消息数据（包含总记录数）
     */
    @Override
    public Result<List<MessageVO>> query(MessageQueryDto messageQueryDto) {
        int totalCount = messageMapper.queryCount(messageQueryDto);
        List<MessageVO> messageVOList = messageMapper.query(messageQueryDto);
        return ApiResult.success(messageVOList, totalCount);
    }
}

package com.shixun.mapper;

import com.shixun.pojo.dto.query.extend.MessageQueryDto;
import com.shixun.pojo.entity.Message;
import com.shixun.pojo.vo.MessageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息数据持久层接口
 * 负责消息数据的增删改查操作
 */
public interface MessageMapper {

    /**
     * 新增消息
     *
     * @param message 消息实体对象
     * @return 受影响的记录数
     */
    int save(Message message);

    /**
     * 将指定用户的所有消息标记为已读
     *
     * @param userId 用户 ID
     * @return 受影响的记录数
     */
    int setRead(@Param("userId") Integer userId);

    /**
     * 分页查询消息数据
     *
     * @param messageQueryDto 消息查询条件
     * @return 消息视图对象列表
     */
    List<MessageVO> query(MessageQueryDto messageQueryDto);

    /**
     * 查询满足条件的消息总数
     *
     * @param messageQueryDto 消息查询条件
     * @return 消息总数
     */
    int queryCount(MessageQueryDto messageQueryDto);

    /**
     * 批量删除消息
     *
     * @param ids 待删除的消息 ID 列表
     */
    void batchDelete(@Param("ids") List<Integer> ids);

}

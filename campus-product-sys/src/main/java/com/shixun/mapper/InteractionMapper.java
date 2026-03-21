package com.shixun.mapper;

import com.shixun.pojo.dto.query.extend.InteractionQueryDto;
import com.shixun.pojo.entity.Interaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 互动信息持久化接口
 */
public interface InteractionMapper {

    /**
     * 新增
     *
     * @param interaction 实体信息
     * @return int 受影响行数
     */
    int save(Interaction interaction);

    /**
     * 分页查询互动行为信息
     *
     * @param interactionQueryDto 分页查询参数
     * @return List<Interaction>
     */
    List<Interaction> query(InteractionQueryDto interactionQueryDto);

    /**
     * 查询商品指标数据
     *
     * @param ids 商品ID列表
     * @return List<Interaction>
     */
    List<Interaction> queryByProductIds(@Param(value = "ids")List<Integer> ids);

    /**
     * 查询满足分页查询的记录总数
     *
     * @param interactionQueryDto 分页查询参数
     * @return int 数据总数
     */
    int queryCount(InteractionQueryDto interactionQueryDto);

    /**
     * 批量删除用户信息
     *
     * @param ids 用户ID集合
     */
    void batchDelete(@Param(value = "ids") List<Integer> ids);

}

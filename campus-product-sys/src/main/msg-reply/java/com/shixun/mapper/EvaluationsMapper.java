package com.shixun.mapper;

import com.shixun.pojo.dto.query.extend.EvaluationsQueryDto;
import com.shixun.pojo.entity.Evaluations;
import com.shixun.pojo.vo.CommentChildVO;
import com.shixun.pojo.vo.CommentParentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论数据持久层接口
 * 负责评论数据的增删改查操作
 */
public interface EvaluationsMapper {

    /**
     * 查询指定内容下的所有父级评论（一级评论）
     *
     * @param contentId   内容 ID
     * @param contentType 内容类型（如：product、order 等）
     * @return 父级评论列表
     */
    List<CommentParentVO> getParentComments(
            @Param("contentId") Integer contentId,
            @Param("contentType") String contentType);

    /**
     * 分页查询评论数据
     *
     * @param evaluationsQueryDto 评论查询条件
     * @return 子评论列表
     */
    List<CommentChildVO> query(EvaluationsQueryDto evaluationsQueryDto);

    /**
     * 查询分页评论数据的总数
     *
     * @param evaluationsQueryDto 评论查询条件
     * @return 评论总数
     */
    Integer queryCount(EvaluationsQueryDto evaluationsQueryDto);

    /**
     * 查询指定父级评论下的所有子评论 ID
     *
     * @param ids 父级评论 ID 列表
     * @return 子评论 ID 列表
     */
    List<Integer> selectChildComments(@Param("ids") List<Integer> ids);

    /**
     * 批量删除评论
     *
     * @param ids 待删除的评论 ID 列表
     */
    void batchDelete(@Param("ids") List<Integer> ids);

    /**
     * 统计指定内容下的评论总数
     *
     * @param contentId   内容 ID
     * @param contentType 内容类型
     * @return 评论总数
     */
    Integer totalCount(
            @Param("contentId") Integer contentId,
            @Param("contentType") String contentType);

    /**
     * 新增评论
     *
     * @param evaluations 评论实体对象
     */
    void save(Evaluations evaluations);

    /**
     * 更新评论信息
     *
     * @param evaluations 评论实体对象
     */
    void update(Evaluations evaluations);

}

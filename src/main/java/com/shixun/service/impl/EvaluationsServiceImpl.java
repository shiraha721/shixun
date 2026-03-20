package com.shixun.service.impl;

import com.shixun.context.LocalThreadHolder;
import com.shixun.mapper.EvaluationsMapper;
import com.shixun.mapper.UserMapper;
import com.shixun.pojo.api.ApiResult;
import com.shixun.pojo.api.PageResult;
import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.EvaluationsQueryDto;
import com.shixun.pojo.entity.Evaluations;
import com.shixun.pojo.entity.User;
import com.shixun.pojo.vo.CommentChildVO;
import com.shixun.pojo.vo.CommentParentVO;
import com.shixun.pojo.vo.EvaluationsVO;
import com.shixun.service.EvaluationsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 评论服务实现类
 */
@Service
public class EvaluationsServiceImpl implements EvaluationsService {

    @Resource
    private EvaluationsMapper evaluationsMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 新增评论
     *
     * @param evaluations 评论信息
     * @return Result<Object> 操作结果
     */
    @Override
    public Result<Object> insert(Evaluations evaluations) {
        evaluations.setCommenterId(LocalThreadHolder.getUserId());
        User queryConditionEntity = User.builder().id(LocalThreadHolder.getUserId()).build();
        User user = userMapper.getByActive(queryConditionEntity);
        if (user.getIsWord()) {
            return ApiResult.error("账户已被禁言");
        }
        evaluations.setCreateTime(LocalDateTime.now());
        evaluationsMapper.save(evaluations);
        return ApiResult.success("评论成功");
    }

    /**
     * 查询指定内容的全部评论（包含父子评论结构）
     *
     * @param contentId   内容 ID
     * @param contentType 内容类型
     * @return Result<Object> 评论 VO 对象（包含评论总数和评论列表）
     */
    @Override
    public Result<Object> list(Integer contentId, String contentType) {
        List<CommentParentVO> parentComments = evaluationsMapper.getParentComments(contentId, contentType);
        setUpvoteFlag(parentComments);
        Integer count = evaluationsMapper.totalCount(contentId, contentType);
        return ApiResult.success(new EvaluationsVO(count, parentComments));
    }

    /**
     * 设置评论点赞状态（包括父评论和子评论）
     *
     * @param parentComments 父评论列表
     */
    private void setUpvoteFlag(List<CommentParentVO> parentComments) {
        String userId = LocalThreadHolder.getUserId().toString();
        parentComments.forEach(parentComment -> {
            parentComment.setUpvoteFlag(isUserUpvote(parentComment.getUpvoteList(), userId));
            parentComment.setUpvoteCount(countVotes(parentComment.getUpvoteList()));

            if (parentComment.getCommentChildVOS() != null) {
                parentComment.getCommentChildVOS().forEach(child -> {
                    child.setUpvoteFlag(isUserUpvote(child.getUpvoteList(), userId));
                    child.setUpvoteCount(countVotes(child.getUpvoteList()));
                });
            }
        });
    }

    /**
     * 判断用户是否已点赞
     *
     * @param voteStr 点赞用户 ID 字符串（逗号分隔）
     * @param userId  当前用户 ID
     * @return true-已点赞，false-未点赞
     */
    private boolean isUserUpvote(String voteStr, String userId) {
        return Optional.ofNullable(voteStr)
                .map(s -> Arrays.asList(s.split(",")))
                .orElse(Collections.emptyList())
                .contains(userId);
    }

    /**
     * 计算点赞数量
     *
     * @param voteStr 点赞用户 ID 字符串（逗号分隔）
     * @return 点赞数量
     */
    private int countVotes(String voteStr) {
        return Optional.ofNullable(voteStr)
                .map(s -> s.split(",").length)
                .orElse(0);
    }

    /**
     * 分页查询评论数据
     *
     * @param evaluationsQueryDto 评论查询条件
     * @return Result<Object> 分页评论数据
     */
    @Override
    public Result<Object> query(EvaluationsQueryDto evaluationsQueryDto) {
        List<CommentChildVO> list = evaluationsMapper.query(evaluationsQueryDto);
        Integer totalPage = evaluationsMapper.queryCount(evaluationsQueryDto);
        return PageResult.success(list, totalPage);
    }

    /**
     * 批量删除评论
     *
     * @param ids 评论 ID 列表
     * @return Result<Object> 操作结果
     */
    @Override
    public Result<Object> batchDelete(List<Integer> ids) {
        evaluationsMapper.batchDelete(ids);
        return ApiResult.success();
    }

    /**
     * 根据 ID 删除评论
     *
     * @param id 评论 ID
     * @return Result<Void> 操作结果
     */
    @Override
    public Result<Void> delete(Integer id) {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(id);
        evaluationsMapper.batchDelete(ids);
        return ApiResult.success();
    }

    /**
     * 修改评论信息
     *
     * @param evaluations 评论信息
     * @return Result<Void> 操作结果
     */
    @Override
    public Result<Void> update(Evaluations evaluations) {
        evaluationsMapper.update(evaluations);
        return ApiResult.success();
    }
}

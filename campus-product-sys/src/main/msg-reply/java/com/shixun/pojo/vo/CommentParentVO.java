package com.shixun.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 父级评论视图对象（VO）
 * 用于展示一级评论及其子评论信息
 */
@Data
public class CommentParentVO {

    /**
     * 评论 ID
     */
    private Integer id;
    
    /**
     * 用户 ID
     */
    private Integer userId;
    
    /**
     * 用户名
     */
    private String userName;
    
    /**
     * 用户头像 URL
     */
    private String userAvatar;
    
    /**
     * 评论内容
     */
    private String content;
    
    /**
     * 回复输入框显示状态
     */
    private Boolean showReplyInput;
    
    /**
     * 子评论总数
     */
    private Integer childTotal;
    
    /**
     * 用户是否已点赞
     */
    private Boolean upvoteFlag;
    
    /**
     * 点赞用户 ID 列表（逗号分隔）
     */
    private String upvoteList;
    
    /**
     * 点赞数量
     */
    private Integer upvoteCount;
    
    /**
     * 评论时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    /**
     * 子评论列表
     */
    private List<CommentChildVO> commentChildVOS;

}

package com.shixun.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 子评论视图对象（VO）
 * 用于展示二级评论（回复）信息
 */
@Data
public class CommentChildVO {
    
    /**
     * 评论 ID
     */
    private Integer id;
    
    /**
     * 父级评论 ID
     */
    private Integer parentId;
    
    /**
     * 评论者用户 ID
     */
    private Integer userId;
    
    /**
     * 评论者用户名
     */
    private String userName;
    
    /**
     * 评论者用户头像 URL
     */
    private String userAvatar;

    /**
     * 被回复者用户 ID
     */
    private Integer replierId;

    /**
     * 被回复者用户名
     */
    private String replierName;

    /**
     * 被回复者用户头像 URL
     */
    private String replierAvatar;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 回复输入框显示状态
     */
    private Boolean replyInputStatus;

    /**
     * 点赞用户 ID 列表（逗号分隔）
     */
    private String upvoteList;

    /**
     * 用户是否已点赞
     */
    private Boolean upvoteFlag;

    /**
     * 点赞数量
     */
    private Integer upvoteCount;

    /**
     * 举报数量
     */
    private Integer reportsNum;
    
    /**
     * 内容类型
     */
    private String contentType;

    /**
     * 评论时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

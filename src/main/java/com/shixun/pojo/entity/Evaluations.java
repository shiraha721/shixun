package com.shixun.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 评论实体类
 * 对应数据库中的评论表
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Evaluations {
    
    /**
     * 主键 ID
     */
    private Integer id;

    /**
     * 父级评论 ID（0 表示一级评论）
     */
    private Integer parentId;

    /**
     * 评论者用户 ID
     */
    private Integer commenterId;

    /**
     * 被回复者用户 ID
     */
    private Integer replierId;

    /**
     * 内容类型（如：product、order 等）
     */
    private String contentType;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 关联的内容 ID
     */
    private Integer contentId;

    /**
     * 点赞用户 ID 列表（逗号分隔）
     */
    private String upvoteList;

    /**
     * 评论创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}

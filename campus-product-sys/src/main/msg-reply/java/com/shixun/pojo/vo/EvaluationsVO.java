package com.shixun.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 评论视图对象（VO）
 * 用于封装评论总数和评论列表数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationsVO {
    
    /**
     * 评论总数
     */
    private Integer count;
    
    /**
     * 评论数据列表
     */
    private List<CommentParentVO> data;
}

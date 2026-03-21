package com.shixun.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 消息实体类
 * 对应数据库中的消息表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    
    /**
     * 主键 ID
     */
    private Integer id;
    
    /**
     * 接收消息的用户 ID
     */
    private Integer userId;
    
    /**
     * 消息内容
     */
    private String content;
    
    /**
     * 是否已读（true-已读，false-未读）
     */
    private Boolean isRead;
    
    /**
     * 消息创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy 年 MM 月 dd 日 HH:mm:ss")
    private LocalDateTime createTime;
}

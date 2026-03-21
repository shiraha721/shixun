package com.shixun.pojo.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InteractionEnum {

    SAVE(1, "收藏"),
    VIEW(2, "浏览"),
    LOVE(3, "想要");

    /**
     * 状态
     */
    private final Integer type;
    /**
     * 状态解释
     */
    private final String detail;


}

package com.shixun.pojo.vo;

import com.shixun.pojo.entity.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品的出参VO类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductVO extends Product {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 商品类别名
     */
    private String categoryName;
    /**
     * 想要人数
     */
    private Integer likeNumber;
    /**
     * 收藏人数
     */
    private Integer saveNumber;
    /**
     * 浏览人数
     */
    private Integer viewNumber;
}

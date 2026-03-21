package com.shixun.service;

import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.CategoryQueryDto;
import com.shixun.pojo.entity.Category;

import java.util.List;

/**
 * 商品类别的业务逻辑接口
 */
public interface CategoryService {

    Result<String> save(Category category);

    Result<String> update(Category category);

    Result<String> batchDelete(List<Integer> ids);

    Result<List<Category>> query(CategoryQueryDto categoryQueryDto);

}

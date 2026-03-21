package com.shixun.service;

import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.InteractionQueryDto;
import com.shixun.pojo.entity.Interaction;
import com.shixun.pojo.vo.ProductVO;

import java.util.List;

/**
 * 互动行为的业务逻辑接口
 */
public interface InteractionService {

    Result<String> save(Interaction interaction);

    Result<String> batchDelete(List<Integer> ids);

    Result<List<Interaction>> query(InteractionQueryDto interactionQueryDto);

    Result<Boolean> saveOperation(Integer productId);

    Result<String> likeProduct(Integer productId);

    Result<List<ProductVO>> queryUser();

    Result<Void> view(Integer productId);

    Result<List<ProductVO>> myView();

    Result<String> batchDeleteInteraction();

}

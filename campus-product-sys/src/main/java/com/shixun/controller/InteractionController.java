package com.shixun.controller;

import com.shixun.aop.Pager;
import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.InteractionQueryDto;
import com.shixun.pojo.entity.Interaction;
import com.shixun.pojo.vo.ProductVO;
import com.shixun.service.InteractionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 互动行为控制器
 */
@RestController
@RequestMapping("/interaction")
public class InteractionController {

    @Resource
    private InteractionService interactionService;

    /**
     * "我想要"操作
     *
     * @param productId 商品ID
     * @return Result<Boolean> 后台通用返回封装类
     */
    @PostMapping(value = "/likeProduct/{productId}")
    @ResponseBody
    public Result<String> likeProduct(@PathVariable Integer productId) {
        return interactionService.likeProduct(productId);
    }

    /**
     * 收藏操作 （取消收藏与收藏是一组对立的操作）
     *
     * @param productId 商品ID
     * @return Result<Boolean> 后台通用返回封装类
     */
    @PostMapping(value = "/saveOperation/{productId}")
    @ResponseBody
    public Result<Boolean> saveOperation(@PathVariable Integer productId) {
        return interactionService.saveOperation(productId);
    }

    /**
     * 用户删除自己的浏览记录
     */
    @PostMapping(value = "/batchDeleteView")
    @ResponseBody
    public Result<String> batchDeleteInteraction() {
        return interactionService.batchDeleteInteraction();
    }


    /**
     * 批量删除
     */
    @PostMapping(value = "/batchDelete")
    @ResponseBody
    public Result<String> batchDelete(@RequestBody List<Integer> ids) {
        return interactionService.batchDelete(ids);
    }

    /**
     * 查询
     *
     * @param interactionQueryDto 查询参数
     * @return Result<List < Interaction>> 响应结果
     */
    @Pager
    @PostMapping(value = "/query")
    @ResponseBody
    public Result<List<Interaction>> query(@RequestBody InteractionQueryDto interactionQueryDto) {
        return interactionService.query(interactionQueryDto);
    }

    /**
     * 记录用户对于商品的浏览行为
     *
     * @param productId 商品ID
     * @return Result<Void> 响应结果
     */
    @PostMapping(value = "/view/{productId}")
    @ResponseBody
    public Result<Void> view(@PathVariable Integer productId) {
        return interactionService.view(productId);
    }


    /**
     * 查询用户自己收藏的商品
     *
     * @return Result<List < ProductVO>> 响应结果
     */
    @PostMapping(value = "/queryUser")
    @ResponseBody
    public Result<List<ProductVO>> queryUser() {
        return interactionService.queryUser();
    }

    /**
     * 查询用户自己浏览过的商品
     *
     * @return Result<List < ProductVO>> 响应结果
     */
    @PostMapping(value = "/myView")
    @ResponseBody
    public Result<List<ProductVO>> myView() {
        return interactionService.myView();
    }

}


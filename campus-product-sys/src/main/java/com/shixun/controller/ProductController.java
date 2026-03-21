package com.shixun.controller;

import com.shixun.aop.Log;
import com.shixun.aop.Pager;
import com.shixun.context.LocalThreadHolder;
import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.ProductQueryDto;
import com.shixun.pojo.dto.update.OrdersDTO;
import com.shixun.pojo.entity.Product;
import com.shixun.pojo.vo.ChartVO;
import com.shixun.pojo.vo.ProductVO;
import com.shixun.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品控制器
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    /**
     * 商品下单
     *
     * @param ordersDTO 参数
     * @return Result<String> 响应结果
     */
    @Log(detail = "商品下单")
    @PostMapping(value = "/buyProduct")
    @ResponseBody
    public Result<String> buyProduct(@RequestBody OrdersDTO ordersDTO) {
        return productService.buyProduct(ordersDTO);
    }


    /**
     * 新增
     *
     * @param product 参数
     * @return Result<String> 响应结果
     */
    @Log(detail = "商品上架")
    @PostMapping(value = "/save")
    @ResponseBody
    public Result<String> save(@RequestBody Product product) {
        return productService.save(product);
    }

    /**
     * 修改
     *
     * @param product 参数
     * @return Result<String> 响应结果
     */
    @Log(detail = "商品修改")
    @PutMapping(value = "/update")
    @ResponseBody
    public Result<String> update(@RequestBody Product product) {
        return productService.update(product);
    }

    /**
     * 批量删除
     */
    @PostMapping(value = "/batchDelete")
    @ResponseBody
    public Result<String> batchDelete(@RequestBody List<Integer> ids) {
        return productService.batchDelete(ids);
    }

    /**
     * 查询
     *
     * @param productQueryDto 查询参数
     * @return Result<List < ProductVO>> 响应结果
     */
    @Pager
    @PostMapping(value = "/query")
    @ResponseBody
    public Result<List<ProductVO>> query(@RequestBody ProductQueryDto productQueryDto) {
        return productService.query(productQueryDto);
    }

    /**
     * 商品下单
     *
     * @param ordersId 订单ID
     * @return Result<String> 响应结果
     */
    @PostMapping(value = "/placeAnOrder/{ordersId}")
    @ResponseBody
    public Result<String> placeAnOrder(@PathVariable Integer ordersId) {
        return productService.placeAnOrder(ordersId);
    }

    /**
     * 申请退款
     *
     * @param ordersId 订单ID
     * @return Result<String> 响应结果
     */
    @Log(detail = "商品申请退款")
    @PostMapping(value = "/refund/{ordersId}")
    @ResponseBody
    public Result<String> refund(@PathVariable Integer ordersId) {
        return productService.refund(ordersId);
    }

    /**
     * 查询用户商品指标情况
     *
     * @param productQueryDto 查询参数
     * @return Result<List < ChartVO>> 响应结果
     */
    @PostMapping(value = "/queryProductInfo")
    @ResponseBody
    public Result<List<ChartVO>> queryProductInfo(@RequestBody ProductQueryDto productQueryDto) {
        productQueryDto.setUserId(LocalThreadHolder.getUserId());
        return productService.queryProductInfo(productQueryDto);
    }

    /**
     * 查询用户商品列表
     *
     * @param productQueryDto 查询参数
     * @return Result<List < ProductVO>> 响应结果
     */
    @PostMapping(value = "/queryUser")
    @ResponseBody
    public Result<List<ProductVO>> queryUser(@RequestBody ProductQueryDto productQueryDto) {
        productQueryDto.setUserId(LocalThreadHolder.getUserId());
        return productService.query(productQueryDto);
    }

}


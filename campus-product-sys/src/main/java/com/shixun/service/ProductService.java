package com.shixun.service;

import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.ProductQueryDto;
import com.shixun.pojo.dto.update.OrdersDTO;
import com.shixun.pojo.entity.Orders;
import com.shixun.pojo.entity.Product;
import com.shixun.pojo.vo.ChartVO;
import com.shixun.pojo.vo.ProductVO;

import java.util.List;

/**
 * 商品的业务逻辑接口
 */
public interface ProductService {

    Result<String> save(Product product);

    Result<String> update(Product product);

    Result<String> batchDelete(List<Integer> ids);

    Result<List<ProductVO>> query(ProductQueryDto productQueryDto);

    Result<String> buyProduct(OrdersDTO ordersDTO);

    Result<String> placeAnOrder(Integer ordersId);

    Result<String> refund(Integer ordersId);

    Result<List<ChartVO>> queryProductInfo(ProductQueryDto productQueryDto);

}

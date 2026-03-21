package com.shixun.service;

import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.OrdersQueryDto;
import com.shixun.pojo.entity.Orders;
import com.shixun.pojo.vo.OrdersVO;

import java.util.List;

/**
 * 订单的业务逻辑接口
 */
public interface OrdersService {

    Result<String> save(Orders orders);

    Result<String> update(Orders orders);

    Result<String> batchDelete(List<Integer> ids);

    Result<List<OrdersVO>> query(OrdersQueryDto ordersQueryDto);

    Result<List<OrdersVO>> queryOrdersList(OrdersQueryDto ordersQueryDto);

    Result<String> returnMoney(Integer ordersId);

}

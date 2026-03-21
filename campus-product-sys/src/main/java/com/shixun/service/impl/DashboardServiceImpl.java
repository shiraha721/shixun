package com.shixun.service.impl;

import com.shixun.mapper.*;
import com.shixun.pojo.api.ApiResult;
import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.base.QueryDto;
import com.shixun.pojo.dto.query.extend.*;
import com.shixun.pojo.vo.ChartVO;
import com.shixun.pojo.vo.ProductVO;
import com.shixun.service.DashboardService;
import com.shixun.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 仪表盘业务逻辑接口实现类
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private InteractionMapper interactionMapper;
    @Resource
    private EvaluationsMapper evaluationsMapper;

    /**
     * 统计系统的基础数据
     *
     * @return Result<List < ChartVO>>
     */
    @Override
    public Result<List<ChartVO>> staticCount() {
        List<ChartVO> chartVOList = new ArrayList<>();

        int userCount = userMapper.queryCount(new UserQueryDto());
        ChartVO chartVOUser = new ChartVO("用户数", userCount);
        chartVOList.add(chartVOUser);

        int productCount = productMapper.queryCount(new ProductQueryDto());
        ChartVO chartVOProduct = new ChartVO("收录商品", productCount);
        chartVOList.add(chartVOProduct);

        int ordersCount = ordersMapper.queryCount(new OrdersQueryDto());
        ChartVO chartVOOrders = new ChartVO("订单数", ordersCount);
        chartVOList.add(chartVOOrders);

        int messageCount = messageMapper.queryCount(new MessageQueryDto());
        ChartVO chartVOMessage = new ChartVO("消息通知", messageCount);
        chartVOList.add(chartVOMessage);

        int interactionCount = interactionMapper.queryCount(new InteractionQueryDto());
        ChartVO chartVOInteraction = new ChartVO("互动数据", interactionCount);
        chartVOList.add(chartVOInteraction);

        int evaluationsCount = evaluationsMapper.queryCount(new EvaluationsQueryDto());
        ChartVO chartVOEvaluations = new ChartVO("商品评论", evaluationsCount);
        chartVOList.add(chartVOEvaluations);

        return ApiResult.success(chartVOList);
    }

    /**
     * 统计商品的上架情况
     *
     * @param day 往前查的天数
     * @return Result<List < ChartVO>>
     */
    @Override
    public Result<List<ChartVO>> productShelvesInfo(Integer day) {
        QueryDto queryDto = DateUtil.startAndEndTime(day);
        ProductQueryDto productQueryDto = new ProductQueryDto();
        productQueryDto.setStartTime(queryDto.getStartTime());
        productQueryDto.setEndTime(queryDto.getEndTime());
        List<ProductVO> productVOS = productMapper.query(productQueryDto);
        List<LocalDateTime> dateTimeList = productVOS.stream()
                .map(ProductVO::getCreateTime).collect(Collectors.toList());
        List<ChartVO> chartVOS = DateUtil.countDatesWithinRange(day, dateTimeList);
        return ApiResult.success(chartVOS);
    }
}

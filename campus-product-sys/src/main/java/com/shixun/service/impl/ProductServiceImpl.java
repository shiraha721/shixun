package com.shixun.service.impl;

import com.shixun.context.LocalThreadHolder;
import com.shixun.mapper.InteractionMapper;
import com.shixun.mapper.OrdersMapper;
import com.shixun.mapper.ProductMapper;
import com.shixun.pojo.api.ApiResult;
import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.ProductQueryDto;
import com.shixun.pojo.dto.update.OrdersDTO;
import com.shixun.pojo.em.InteractionEnum;
import com.shixun.pojo.entity.Interaction;
import com.shixun.pojo.entity.Orders;
import com.shixun.pojo.entity.Product;
import com.shixun.pojo.vo.ChartVO;
import com.shixun.pojo.vo.ProductVO;
import com.shixun.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 商品类别业务逻辑接口实现类
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private InteractionMapper interactionMapper;

    /**
     * 新增
     *
     * @param product 参数
     * @return Result<String> 后台通用返回封装类
     */
    @Override
    public Result<String> save(Product product) {
        product.setUserId(LocalThreadHolder.getUserId());
        product.setCreateTime(LocalDateTime.now());
        productMapper.save(product);
        return ApiResult.success("商品新增成功");
    }

    /**
     * 修改
     *
     * @param product 参数
     * @return Result<String> 后台通用返回封装类
     */
    @Override
    public Result<String> update(Product product) {
        productMapper.update(product);
        return ApiResult.success("商品修改成功");
    }

    /**
     * 删除
     *
     * @param ids 待删除ID集合
     * @return Result<String> 后台通用返回封装类
     */
    @Override
    public Result<String> batchDelete(List<Integer> ids) {
        productMapper.batchDelete(ids);
        return ApiResult.success("商品删除成功");
    }

    /**
     * 查询
     *
     * @param productQueryDto 查询参数
     * @return Result<List < ProductVO>> 后台通用返回封装类
     */
    @Override
    public Result<List<ProductVO>> query(ProductQueryDto productQueryDto) {
        int totalCount = productMapper.queryCount(productQueryDto);
        List<ProductVO> productVOList = productMapper.query(productQueryDto);
        return ApiResult.success(productVOList, totalCount);
    }

    /**
     * 商品下单
     *
     * @param ordersDTO 订单
     * @return Result<String>
     */
    @Override
    public Result<String> buyProduct(OrdersDTO ordersDTO) {
        if (ordersDTO.getProductId() == null) {
            return ApiResult.error("商品ID不为空");
        }
        ProductQueryDto productQueryDto = new ProductQueryDto();
        productQueryDto.setId(ordersDTO.getProductId());
        List<ProductVO> productVOS = productMapper.query(productQueryDto);
        if (productVOS.isEmpty()) {
            return ApiResult.error("商品信息异常");
        }
        // 有且仅有一条商品信息
        ProductVO productVO = productVOS.get(0);
        // 判断库存情况
        if (productVO.getInventory() <= 0
                || (productVO.getInventory() - ordersDTO.getBuyNumber()) < 0) {
            return ApiResult.error("商品库存不足");
        }
        createOrders(ordersDTO, productVO);
        ordersMapper.save(ordersDTO);
        // 扣库存
        Product product = new Product();
        product.setId(productVO.getId());
        product.setInventory(productVO.getInventory() - ordersDTO.getBuyNumber());
        productMapper.update(product);
        return ApiResult.success("下单成功");
    }

    /**
     * 设置订单所需参数
     *
     * @param orders    订单
     * @param productVO 商品信息
     */
    private void createOrders(Orders orders, ProductVO productVO) {
        orders.setCode(createOrdersCode());
        orders.setUserId(LocalThreadHolder.getUserId());
        orders.setTradeStatus(false); // 初始时，未交易成功
        orders.setBuyPrice(productVO.getPrice());
        orders.setCreateTime(LocalDateTime.now());
    }

    /**
     * 生成订单号
     *
     * @return String
     */
    private String createOrdersCode() {
        // UUID
        //String ordersCode = UUID.randomUUID().toString().toLowerCase();
        // 时间戳
        long timeMillis = System.currentTimeMillis();
        return String.valueOf(timeMillis);
    }

    /**
     * 商品下单
     *
     * @param ordersId 订单ID
     * @return Result<String> 通用返回封装类
     */
    @Override
    public Result<String> placeAnOrder(Integer ordersId) {
        Orders orders = new Orders();
        orders.setId(ordersId);
        orders.setTradeStatus(true);
        orders.setTradeTime(LocalDateTime.now());
        ordersMapper.update(orders);
        return ApiResult.success("下单成功");
    }

    /**
     * 申请退款
     *
     * @param ordersId 订单ID
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> refund(Integer ordersId) {
        Orders orders = new Orders();
        orders.setId(ordersId);
        orders.setRefundStatus(false);
        ordersMapper.update(orders);
        return ApiResult.success("申请退款成功，请等待卖家审核");
    }

    /**
     * 查询用户商品指标情况
     *
     * @param productQueryDto 查询参数
     * @return Result<List < ChartVO>> 响应结果
     */
    @Override
    public Result<List<ChartVO>> queryProductInfo(ProductQueryDto productQueryDto) {
        List<Integer> productIds = productMapper.queryProductIds(productQueryDto.getUserId());
        List<Interaction> interactionList = interactionMapper.queryByProductIds(productIds);
        // 浏览、收藏、想要
        long viewCount = getProductCount(interactionList, InteractionEnum.VIEW.getType());
        long saveCount = getProductCount(interactionList, InteractionEnum.SAVE.getType());
        long loveCount = getProductCount(interactionList, InteractionEnum.LOVE.getType());
        List<ChartVO> chartVOList = new ArrayList<>();
        ChartVO chartVOView = new ChartVO("商品被浏览",(int)viewCount);
        ChartVO chartVOSave = new ChartVO("商品被收藏",(int)saveCount);
        ChartVO chartVOLove = new ChartVO("商品被想要",(int)loveCount);
        chartVOList.add(chartVOView);
        chartVOList.add(chartVOSave);
        chartVOList.add(chartVOLove);
        return ApiResult.success(chartVOList);
    }

    /**
     * 过滤指定的商品指标数据
     *
     * @param interactionList 互动数据源
     * @param type            互动类型
     * @return long
     */
    private long getProductCount(List<Interaction> interactionList, Integer type) {
        return interactionList.stream()
                .filter(interaction -> Objects.equals(type, interaction.getType()))
                .count();
    }
}

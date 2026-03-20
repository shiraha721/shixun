package cn.kmbeast.mapper;

import cn.kmbeast.pojo.dto.query.extend.ProductQueryDto;
import cn.kmbeast.pojo.entity.Product;
import cn.kmbeast.pojo.vo.ProductVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品持久化接口
 */
public interface ProductMapper {

    /**
     * 新增
     *
     * @param product 实体信息
     * @return int 受影响行数
     */
    int save(Product product);

    /**
     * 分页查询商品类别信息
     *
     * @param productQueryDto 分页查询参数
     * @return List<ProductVO>
     */
    List<ProductVO> query(ProductQueryDto productQueryDto);

    /**
     * 查询满足分页查询的记录总数
     *
     * @param productQueryDto 分页查询参数
     * @return int 数据总数
     */
    int queryCount(ProductQueryDto productQueryDto);

    /**
     * 修改
     *
     * @param product 商品类别信息
     * @return int 受影响行数
     */
    int update(Product product);

    /**
     * 批量删除用户信息
     *
     * @param ids 用户ID集合
     */
    void batchDelete(@Param(value = "ids") List<Integer> ids);

    List<Integer> queryProductIds(@Param(value = "userId") Integer userId);

    List<ProductVO> queryProductList(@Param(value = "ids") List<Integer> ids);
}

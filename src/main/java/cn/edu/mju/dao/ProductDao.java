package cn.edu.mju.dao;


import cn.edu.mju.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
* 商品
* */
public interface ProductDao extends BaseDao {


    /**
     * 查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺Id,商品类别
     *
     */
    List<Product> queryProductList(
            @Param("productCondition") Product productCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 查询对应的商品总数
     *
     */
    int queryProductCount(@Param("productCondition") Product productCondition);

    /**
     *
     */
    Product queryProductByProductId(Long productId);

    /**
     * 插入商品
     *
     */
    int insertProduct(Product product);

    /**
     * 更新商品信息
     *
     */
    int updateProduct(Product product);

    /**
     * 删除商品类别之前，将商品类别ID置为空
     */
    int updateProductCategoryToNull(Long productCategoryId);

    /**
     * 删除商品
     *
     */
    int deleteProduct(@Param("productId") Long productId,
                      @Param("shopId") Long shopId);




}


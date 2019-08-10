package cn.edu.mju.dao;

import cn.edu.mju.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
* 店铺中商品的分类
* */
public interface ProductCategoryDao extends BaseDao  {


    /*
    * 根据商铺id 查询店铺的商品分类信息
    * */
    List<ProductCategory> queryProductCategory(@Param("shopId") Long shopId);


    /*
    *
    * 批量增加
    * */
    int batchInsertProductCategory(@Param("productCategoryList") List<ProductCategory> productCategoryList);


    /*
    * 根据商品类别id 和 店铺id 删除
    * */
    int deleteProductCategory(@Param("productCategoryId") Long productCategoryId, @Param("shopId") Long shopId);



}

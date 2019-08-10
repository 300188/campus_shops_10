package cn.edu.mju.service;


import cn.edu.mju.dto.ProductCategoryExecution;
import cn.edu.mju.entity.ProductCategory;
import cn.edu.mju.exceptions.ProductCategoryOperationException;

import java.util.List;

/*
* 商品分类
* */
public interface ProductCategoryService extends BaseService {


    /*
    * 根据店铺id 和 商品类别id 删除类别
    *
    * */
    ProductCategoryExecution removeProductCategory(Long productCategoryId,Long shopId) throws ProductCategoryOperationException;


    /*
    * 根据店铺id获取商品分类
    * */
    List<ProductCategory> getProductCategoryList(Long shopId);


    /*
    * 批量增加
    * */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;




}

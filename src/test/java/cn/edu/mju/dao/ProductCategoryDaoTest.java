package cn.edu.mju.dao;

import cn.edu.mju.BaseTest;
import cn.edu.mju.entity.ProductCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    @Ignore
    public void queryProductCategory() {
    }

    @Test
    public void batchInsertProductCategory() {

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("商品类别4");
        productCategory.setPriority(4);
        productCategory.setCreateTime(System.currentTimeMillis());
        productCategory.setShopId(1L);
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("商品类别5");
        productCategory2.setPriority(5);
        productCategory2.setCreateTime(System.currentTimeMillis());
        productCategory2.setShopId(1L);
        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory2);
        int effect = productCategoryDao.batchInsertProductCategory(productCategoryList);

        assertEquals(2,effect);

    }
}
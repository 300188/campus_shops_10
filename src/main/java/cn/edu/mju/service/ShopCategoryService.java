package cn.edu.mju.service;

import cn.edu.mju.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService extends BaseService {



    public static final String SHOP_CATEGORY_LIST_KEY = "shopcategorylist";

    /*
    * 获取所有商铺分类
    * */
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategory);

}

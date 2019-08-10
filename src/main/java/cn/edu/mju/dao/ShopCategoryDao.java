package cn.edu.mju.dao;

import cn.edu.mju.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao extends BaseDao  {

    List<ShopCategory> queryShopCategory(@Param("shopCategory") ShopCategory shopCategory);

}

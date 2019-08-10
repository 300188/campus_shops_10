package cn.edu.mju.dao;

import cn.edu.mju.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
 * 店铺
 * */

public interface ShopDao extends BaseDao  {


    /*
     *查询店铺总数
     * */
    int queryShopCount(@Param("shop") Shop shop);

    /*
     * 分页查询店铺，可输入的条件有：店铺名（模糊），店铺状态、类别，区域id，owner
     * @param rowIndex：从哪一行开始，pageSize: 查询多少行
     * */
    List<Shop> queryShopList(@Param("shop") Shop shop, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);


    /*
     * 通过shop id 查询店铺
     * */
    Shop queryByShopId(Long shopId);

    /*
     * 新增店铺
     * */
    int insertShop(Shop shop);

    /*
     * 更新店铺信息
     * */
    int updateShop(Shop shop);


}

package cn.edu.mju.dao;

import cn.edu.mju.BaseTest;
import cn.edu.mju.entity.Area;
import cn.edu.mju.entity.PersonInfo;
import cn.edu.mju.entity.Shop;
import cn.edu.mju.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private  ShopDao shopDao;

    @Test
    @Ignore
    public void insertShop() {

        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        Shop shop = new Shop();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(1L);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试");
        shop.setEnableStatus(1);
        shop.setPriority(1);
        assertEquals(1,shopDao.insertShop(shop));

    }

    @Test
    @Ignore
    public void updateShop() {

        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        Shop shop = new Shop();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(1L);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
//        shop.setShopCategory(shopCategory);
        shop.setShopName("更新");
        shop.setEnableStatus(1);
        shop.setPriority(1);
        shop.setShopId(1L);
        assertEquals(1,shopDao.updateShop(shop));

    }


    @Test
    @Ignore
    public void queryByShopId() {

        Long shopId = 1L;

        Shop shop = shopDao.queryByShopId(shopId);

        System.out.println(shop.getShopName());

    }

    @Test
    @Ignore
    public void queryShopCount() {

        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shop.setOwner(owner);
        int count = shopDao.queryShopCount(shop);

        assertEquals(1,count);
    }

    @Test
    @Ignore
    public void queryShopList() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shop.setOwner(owner);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1L);
        shop.setShopCategory(shopCategory);
        List<Shop> shopList = shopDao.queryShopList(shop, 0, 1);
        assertEquals(1,shopList.size());

    }
}
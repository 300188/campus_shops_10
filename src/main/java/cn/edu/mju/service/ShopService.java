package cn.edu.mju.service;

import cn.edu.mju.dto.ShopExecution;
import cn.edu.mju.entity.Shop;
import cn.edu.mju.exceptions.ShopOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;

public interface ShopService extends BaseService {

    /*
    * 分页查询店铺
    * */
    ShopExecution getShopList(Shop shop,int pageIndex,int pageSize);


    /*
    * 增加店铺
    * */
    ShopExecution addShop(Shop shop,CommonsMultipartFile shopImg) throws ShopOperationException;


    /*
    * 根据店铺id获取店铺信息
    * */
    Shop getByShopId(Long shopId);


    /*
    * 更新店铺信息，包括对图片的处理
    * */
    ShopExecution modifyShop(Shop shop,CommonsMultipartFile shopImg) throws ShopOperationException;



}

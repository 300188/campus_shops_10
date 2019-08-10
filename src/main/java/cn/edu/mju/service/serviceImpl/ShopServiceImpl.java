package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.dao.ShopCategoryDao;
import cn.edu.mju.dao.ShopDao;
import cn.edu.mju.dto.ShopExecution;
import cn.edu.mju.entity.Shop;
import cn.edu.mju.entity.ShopCategory;
import cn.edu.mju.enums.ShopStateEnum;
import cn.edu.mju.exceptions.ShopOperationException;
import cn.edu.mju.service.ShopService;
import cn.edu.mju.util.ImageUtil;
import cn.edu.mju.util.PageCalculator;
import cn.edu.mju.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

@Service
public class ShopServiceImpl extends BaseServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private ShopCategoryDao shopCategoryDao;


    @Override
    public ShopExecution getShopList(Shop shop, int pageIndex, int pageSize) {

        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);

        List<Shop> shopList = shopDao.queryShopList(shop, rowIndex, pageSize);

        int count = shopDao.queryShopCount(shop);

        ShopExecution shopExecution  = new ShopExecution();
        if(shopList != null ){
            shopExecution.setCount(count);
            shopExecution.setShopList(shopList);
        }else{
            shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
        }

        return shopExecution;
    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop,CommonsMultipartFile shopImg) throws ShopOperationException {

        if(null == shop){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }try {
            shop.setEnableStatus(0);
            shop.setCreateTime(System.currentTimeMillis());
            shop.setLastEditTime(System.currentTimeMillis());
/*            if (shop.getShopCategory() != null) {
                Long shopCategoryId = shop.getShopCategory()
                        .getShopCategoryId();*/
//                ShopCategory sc = new ShopCategory();
                /*sc = shopCategoryDao.queryShopCategoryById(shopCategoryId);
                ShopCategory parentCategory = new ShopCategory();
                parentCategory.setShopCategoryId(sc.getParentId());
                shop.setParentCategory(parentCategory);*/
//            }
            //插入
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                try {
                    if (shopImg != null) {
                        //存储图片
                        addShopImg(shop, shopImg);
                        effectedNum = shopDao.updateShop(shop);
                        if (effectedNum <= 0) {
                            throw new ShopOperationException("创建图片地址失败");
                        }
                    }
                } catch (Exception e) {
                    throw new ShopOperationException("addShopImg error: "
                            + e.getMessage());
                }
                // 执行增加shopAuthMap操作
/*                ShopAuthMap shopAuthMap = new ShopAuthMap();
                shopAuthMap.setEmployeeId(shop.getOwnerId());
                shopAuthMap.setShopId(shop.getShopId());
                shopAuthMap.setName("");
                shopAuthMap.setTitle("Owner");
                shopAuthMap.setTitleFlag(1);
                shopAuthMap.setCreateTime(new Date());
                shopAuthMap.setLastEditTime(new Date());
                shopAuthMap.setEnableStatus(1);
                try {
                    effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
                    if (effectedNum <= 0) {
                        throw new RuntimeException("授权创建失败");
                    } else {// 创建成功
                        return new ShopOperationException(ShopStateEnum.CHECK, shop);
                    }
                } catch (Exception e) {
                    throw new ShopOperationException("insertShopAuthMap error: "
                            + e.getMessage());
                }*/

            }
        } catch (Exception e) {
            throw new ShopOperationException("insertShop error: " + e.getMessage());
        }

        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }


    @Override
    public Shop getByShopId(Long shopId) {
        return shopDao.queryByShopId(shopId);
    }


    @Override
    @Transactional
    public ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg) throws ShopOperationException {

        if(null == shop || null == shop.getShopId()){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else{
            try{
                //判断是否需要处理图片
                if(null != shopImg){
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if(tempShop.getShopImg() != null){
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    //给shop添加新的图片
                    addShopImg(shop,shopImg);
                }
                //更新店铺信息
                shop.setLastEditTime(System.currentTimeMillis());
                int effectedNum = shopDao.updateShop(shop);

                if(effectedNum <= 0){
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }else{
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS,shop);
                }
            }catch (Exception e){
                throw  new ShopOperationException("modifyShop error" + e.getMessage());
            }

        }

    }



    //getshop增加图片地址
    private void addShopImg(Shop shop,CommonsMultipartFile shopImg){
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());

        String shopImgAddr = ImageUtil.generateThumbnail(shopImg,dest);

        shop.setShopImg(shopImgAddr);

    }


}

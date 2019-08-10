package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.cache.JedisUtil;
import cn.edu.mju.dao.ShopCategoryDao;
import cn.edu.mju.dto.ShopExecution;
import cn.edu.mju.entity.Shop;
import cn.edu.mju.entity.ShopCategory;
import cn.edu.mju.exceptions.ShopOperationException;
import cn.edu.mju.service.ShopCategoryService;
import cn.edu.mju.service.ShopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCategoryServiceImpl extends BaseServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;


    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private JedisUtil.Keys jedisKeys;


    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategory) {
        String key = SHOP_CATEGORY_LIST_KEY;
        List<ShopCategory> shopCategoryList = null;
        ObjectMapper mapper = new ObjectMapper();
        if(shopCategory == null){
            //如果查询条件为空 则列出所有首页大类 即parentId为空的店铺类别
            key = key + "_allfirstlevel";

        }else if(shopCategory != null && shopCategory.getParent() != null &&
                shopCategory.getParent().getShopCategoryId() != null){
            key = key + "_parent" + shopCategory.getParent().getShopCategoryId();
        }else if(shopCategory != null){
            //列出所有子类别 不管属于那个父类
            key = key + ")allsecondlevel";
        }
        //判断key是否存在
        if (!jedisKeys.exists(key)) {

            shopCategoryList = shopCategoryDao
                    .queryShopCategory(shopCategory);
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(shopCategoryList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new ShopOperationException(e.getMessage());
            }
            jedisStrings.set(key, jsonString);
        } else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory()
                    .constructParametricType(ArrayList.class,
                            ShopCategory.class);
            try {
                shopCategoryList = mapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                throw new ShopOperationException(e.getMessage());
            }
        }
        return shopCategoryList;
    }
}

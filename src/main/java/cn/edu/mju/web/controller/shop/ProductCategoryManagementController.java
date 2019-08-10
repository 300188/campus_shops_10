package cn.edu.mju.web.controller.shop;

import cn.edu.mju.dto.ProductCategoryExecution;
import cn.edu.mju.entity.ProductCategory;
import cn.edu.mju.entity.Shop;
import cn.edu.mju.enums.ProductCategoryStateEnum;
import cn.edu.mju.exceptions.ProductCategoryOperationException;
import cn.edu.mju.service.ProductCategoryService;
import cn.edu.mju.util.HttpServletRequestUtil;
import cn.edu.mju.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shop")
public class ProductCategoryManagementController extends BaseController {

    @Autowired
    private ProductCategoryService productCategoryService;


    /*
    * 根据商品类别id 和 店铺id删除商品类别
    * */
    @RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeProductCategory(Long productCategoryId,
                                                      HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productCategoryId != null && productCategoryId > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute(
                        "currentShop");
                ProductCategoryExecution pe = productCategoryService
                        .removeProductCategory(productCategoryId,
                                currentShop.getShopId());
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS
                        .getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少选择一个商品类别");
        }
        return modelMap;
    }




    /*
    * 批量添加商品分类
    * */
    @ResponseBody
    @RequestMapping(value = "/addproductcategories",method = RequestMethod.POST)
    public Map<String,Object> addProductCategories(@RequestBody List<ProductCategory> productCategoryList,HttpServletRequest request){

        Map<String,Object> modelMap = new HashMap<>();
//        从后台获取
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        for (ProductCategory pc : productCategoryList) {
            pc.setShopId(currentShop.getShopId());
            pc.setCreateTime(System.currentTimeMillis());
        }

        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                ProductCategoryExecution pe = productCategoryService
                        .batchAddProductCategory(productCategoryList);
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS
                        .getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个商品类别");
        }

        return modelMap;
    }


    /*
    * 获取店铺的商品分类
    * */
    @ResponseBody
    @RequestMapping(value = "/getproductcategorylist",method = RequestMethod.GET)
    public Map<String,Object> getProductCategoryList(HttpServletRequest request){

        Map<String,Object> modelMap = new HashMap<>();

        Shop currentShop = (Shop) request.getSession().getAttribute(
                "currentShop");
        if ((currentShop != null) && (currentShop.getShopId() != null)) {
            List<ProductCategory> productCategoryList = productCategoryService
                    .getProductCategoryList(currentShop.getShopId());
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }


        return modelMap;
    }





}

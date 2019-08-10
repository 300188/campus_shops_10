package cn.edu.mju.web.controller.shop;

import cn.edu.mju.dao.ProductCategoryDao;
import cn.edu.mju.dto.ProductExecution;
import cn.edu.mju.entity.Product;
import cn.edu.mju.entity.ProductCategory;
import cn.edu.mju.entity.Shop;
import cn.edu.mju.enums.ProductStateEnum;
import cn.edu.mju.exceptions.ProductOperationException;
import cn.edu.mju.service.ProductCategoryService;
import cn.edu.mju.service.ProductService;
import cn.edu.mju.util.CodeUtil;
import cn.edu.mju.util.HttpServletRequestUtil;
import cn.edu.mju.web.controller.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shop")
public class ProductManagementController extends BaseController {

    //支持上传商品详情图的最大数量

    private static final int IMAGE_MAX_COUNT = 6;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;


    /*
    *
    * 修改商品信息
    * */
    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        boolean statusChange = HttpServletRequestUtil.getBoolean(request,
                "statusChange");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request,
                "productStr");
        MultipartHttpServletRequest multipartRequest = null;
        CommonsMultipartFile thumbnail = null;
        List<CommonsMultipartFile> productImgs = new ArrayList<CommonsMultipartFile>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            multipartRequest = (MultipartHttpServletRequest) request;
            thumbnail = (CommonsMultipartFile) multipartRequest
                    .getFile("thumbnail");
            for (int i = 0; i < IMAGE_MAX_COUNT; i++) {
                CommonsMultipartFile productImg = (CommonsMultipartFile) multipartRequest
                        .getFile("productImg" + i);
                if (productImg != null) {
                    productImgs.add(productImg);
                }
            }
        }
        try {
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (product != null) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute(
                        "currentShop");
                if(null == currentShop){
                    modelMap.put("success",false);
                    modelMap.put("errMsg",ProductStateEnum.INNER_ERROR);
                    return modelMap;
                }
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                ProductExecution pe = productService.modifyProduct(product,
                        thumbnail, productImgs);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }


    /*
    * 根据店铺id查找商品的分类
    * */
    @RequestMapping(value = "/getproductcategorylistbyshopId", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductCategoryListByShopId(
            HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Shop currentShop = (Shop) request.getSession().getAttribute(
                "currentShop");
        if ((currentShop != null) && (currentShop.getShopId() != null)) {
            try{
                List<ProductCategory> productCategoryList = productCategoryService
                        .getProductCategoryList(currentShop.getShopId());
                modelMap.put("productCategoryList", productCategoryList);
                modelMap.put("success", true);
            }catch (ProductOperationException e){
                modelMap.put("success", false);
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }


    /*
    * 添加商品
    * */
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request,
                "productStr");
        MultipartHttpServletRequest multipartRequest = null;
        CommonsMultipartFile thumbnail = null;
        List<CommonsMultipartFile> productImgs = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try {
            if (multipartResolver.isMultipart(request)) {
                multipartRequest = (MultipartHttpServletRequest) request;
                thumbnail = (CommonsMultipartFile) multipartRequest
                        .getFile("thumbnail");
                    for (int i = 0; i < IMAGE_MAX_COUNT; i++) {
                    CommonsMultipartFile productImg = (CommonsMultipartFile) multipartRequest
                            .getFile("productImg" + i);
                    if (productImg != null) {
                        productImgs.add(productImg);
                    }
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        try {
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (product != null && thumbnail != null && productImgs.size() > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute(
                        "currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);

                ProductExecution pe = productService.addProduct(product,
                        thumbnail, productImgs);

                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }


    /*
    * 根据商品id查询商品
    *
    * */
    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //空值判断
        if (productId > -1) {
            try{
                Product product = productService.getProductById(productId);
                if(null == product){
                    modelMap.put("success", false);
                    return modelMap;
                }



//            获取店铺的商品类别列表
                List<ProductCategory> productCategoryList = productCategoryService
                        .getProductCategoryList(product.getShop().getShopId());
                modelMap.put("product", product);
                modelMap.put("productCategoryList", productCategoryList);
                modelMap.put("success", true);
            }catch (ProductOperationException e){
                modelMap.put("success", false);
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    //查询出此店铺的所有商品
    @RequestMapping(value = "/listproductsbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listProductsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop) request.getSession().getAttribute(
                "currentShop");
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null)
                && (currentShop.getShopId() != null)) {
            try{
                Long productCategoryId = HttpServletRequestUtil.getLong(request,
                        "productCategoryId");
                String productName = HttpServletRequestUtil.getString(request,
                        "productName");
                Product productCondition = compactProductCondition4Search(
                        currentShop.getShopId(), productCategoryId, productName);
                ProductExecution pe = productService.getProductList(
                        productCondition, pageIndex, pageSize);
                modelMap.put("productList", pe.getProductList());
                modelMap.put("count", pe.getCount());
                modelMap.put("success", true);
            }catch (ProductOperationException e){
                modelMap.put("success", false);
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }


    private Product compactProductCondition4Search(Long shopId,
                                                   Long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        return productCondition;
    }



}

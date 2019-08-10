package cn.edu.mju.web.controller.shop;

import cn.edu.mju.dto.ShopExecution;
import cn.edu.mju.entity.Area;
import cn.edu.mju.entity.PersonInfo;
import cn.edu.mju.entity.Shop;
import cn.edu.mju.entity.ShopCategory;
import cn.edu.mju.enums.ShopStateEnum;
import cn.edu.mju.service.AreaService;
import cn.edu.mju.service.ShopCategoryService;
import cn.edu.mju.service.ShopService;
import cn.edu.mju.util.CodeUtil;
import cn.edu.mju.util.HttpServletRequestUtil;
import cn.edu.mju.web.controller.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shop")
public class ShopManagementController extends BaseController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;


    //获取店铺管理信息 管理列表
    @RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopManagementInfo(HttpServletRequest request){

        Map<String, Object> modelMap = new HashMap<>();

        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");

        if(shopId <= 0){

            Object currentShopObj = request.getSession().getAttribute("currentShop");

            if(null == currentShopObj){
                modelMap.put("redirect",true);
                modelMap.put("url","/shop/shoplist");
            }else{
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect",false);
                modelMap.put("shopId",currentShop.getShopId());
            }

        }else{
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            modelMap.put("redirect",false);
            modelMap.put("shopId",currentShop.getShopId());

        }

        return modelMap;

    }



    //获取店铺列表
    @RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopList(HttpServletRequest request){

        Map<String, Object> modelMap = new HashMap<>();

        PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");

        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

        if(owner == null || owner.getUserId() == null){
            modelMap.put("success",false);
            modelMap.put("errMsg","非法登录");
            return modelMap;
        }
        try{
            Shop shop = new Shop();
            shop.setOwner(owner);
            if ((pageIndex > -1) && (pageSize > -1)){

                ShopExecution se = shopService.getShopList(shop,pageIndex,pageSize);
                modelMap.put("success",true);
                modelMap.put("shopList",se.getShopList());
                modelMap.put("user",owner);
                modelMap.put("count",se.getCount());
                request.getSession().setAttribute("shopList",se.getShopList());

            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "empty pageSize or pageIndex");
            }

        }catch (Exception e){

            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());

        }

        return modelMap;
    }


    //修改店铺信息
    @RequestMapping(value = "/modifyshop",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> modifyShop(HttpServletRequest request){


        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //json转换的mapper
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        MultipartHttpServletRequest multipartRequest = null;
        CommonsMultipartFile shopImg = null;
        // 文件上传解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            multipartRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartRequest
                    .getFile("shopImg");

        }

        if (null != shop && null != shop.getShopId()) {

            try {
//                修改
                ShopExecution se = shopService.modifyShop(shop,shopImg);

                if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
        }
        return modelMap;

    }



    /*
    * 获取店铺修改前的原始信息
    * */
    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopById(HttpServletRequest request){

        Map<String,Object> modelMap = new HashMap<>();

        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");

        if(shopId > -1 ){
            try{
                Shop shop = shopService.getByShopId(shopId);
                if(null == shop){
                    modelMap.put("success",false);
                    modelMap.put("errorMsg","not find shop");
                }else{
                    List<Area> areaList = areaService.getAreaList();
                    modelMap.put("shop",shop);
                    modelMap.put("areaList",areaList);
                    modelMap.put("success",true);
                }

            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errorMsg",e.getMessage());
            }

        }else{
            modelMap.put("success",false);
            modelMap.put("errorMsg","empty shopId");
        }


        return modelMap;
    }



    /*
    * 获取初始化信息 店铺分类 区域
    * */
    @RequestMapping(value = "/getshopinitinfo")
    @ResponseBody
    public Map<String,Object> getShopInitInfo(){

        Map<String,Object> modelMap = new HashMap<>();

        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        try{

            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);

        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errorMsg",e.getMessage());
        }
        return modelMap;

    }



    //注册店铺
    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> registerShop(HttpServletRequest request){


        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //json转换的mapper
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        MultipartHttpServletRequest multipartRequest = null;
        CommonsMultipartFile shopImg = null;
        // 文件上传解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            multipartRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartRequest
                    .getFile("shopImg");

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }
        if (null != shop && null != shopImg ) {
            try {
                PersonInfo user = (PersonInfo) request.getSession()
                        .getAttribute("user");
                shop.setOwner(user);
                ShopExecution se = shopService.addShop(shop, shopImg);
                if (se.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);

                    //该用户可以操作的店铺列表
                    @SuppressWarnings("unchecked")
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    if(shopList == null || shopList.size() == 0){
                        shopList = new ArrayList<>();
                    }

                    shopList.add(se.getShop());
                    request.getSession().setAttribute("shopList",shopList);

                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
        }
        return modelMap;

    }



}

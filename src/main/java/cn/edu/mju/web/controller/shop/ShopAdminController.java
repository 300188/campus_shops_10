package cn.edu.mju.web.controller.shop;

import cn.edu.mju.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/shop",method = RequestMethod.GET)
public class ShopAdminController extends BaseController {


    /*
    * 店铺信息管理页面
    * */
    @RequestMapping(value = "/shopoperation",method = RequestMethod.GET)
    public String shopOperation(){
        return "/shop/shopoperation";
    }

    /*
    * 店铺列表页面
    * */
    @RequestMapping(value = "/shoplist",method = RequestMethod.GET)
    public String shopList(){
        return "/shop/shoplist";
    }

    /*
    * 店铺管理页面
    * */
    @RequestMapping(value = "/shopmanage",method = RequestMethod.GET)
    public String shopManagement(){
        return "/shop/shopmanage";
    }


    /*
    * 店铺商品类别管理页面
    * */
    @RequestMapping(value = "/productcategorymanage",method = RequestMethod.GET)
    public String productCategoryManage(){
        return "/shop/productcategorymanage";
    }

    /*
    * 店铺商品类别管理页面
    * */
    @RequestMapping(value = "/productmanage",method = RequestMethod.GET)
    public String productManage(){
        return "/shop/productmanage";
    }


    /*
    * 商品信息
    * */
    @RequestMapping(value = "/productoperation",method = RequestMethod.GET)
    public String productOperation(){
        return "/shop/productoperation";
    }


}

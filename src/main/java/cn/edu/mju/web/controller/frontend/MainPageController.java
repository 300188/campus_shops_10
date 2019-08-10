package cn.edu.mju.web.controller.frontend;

import cn.edu.mju.entity.HeadLine;
import cn.edu.mju.entity.ShopCategory;
import cn.edu.mju.enums.HeadLineStateEnum;
import cn.edu.mju.service.HeadLineService;
import cn.edu.mju.service.ShopCategoryService;
import cn.edu.mju.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
*
* 前端首页
* */

@Controller
@RequestMapping("/frontend")
public class MainPageController extends BaseController {


    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private HeadLineService headLineService;


    /*
    * 初始化前端展示系统的主页信息 包括获取一级商铺类别列表以及头条列表
    * */

    @RequestMapping(value = "/listmainpageinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> listMainPageInfo(HttpServletRequest request){

        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();

        List<HeadLine> headLineList = new ArrayList<HeadLine>();
        try {
            HeadLine headLineCondition = new HeadLine();
            //获取状态为可用1的头条列表
            headLineCondition.setEnableStatus(1);
            headLineList = headLineService.getHeadLineList(headLineCondition);
            modelMap.put("headLineList", headLineList);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", HeadLineStateEnum.INNER_ERROR.getStateInfo());
            return modelMap;
        }
        try {
            shopCategoryList = shopCategoryService
                    .getShopCategoryList(null);
            modelMap.put("shopCategoryList", shopCategoryList);
        } catch (Exception e) {

            modelMap.put("success", false);
            modelMap.put("errMsg", HeadLineStateEnum.INNER_ERROR.getStateInfo());
            return modelMap;
        }
        modelMap.put("success", true);
        return modelMap;


    }





}

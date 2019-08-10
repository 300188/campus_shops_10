package cn.edu.mju.web.controller.area;

import cn.edu.mju.entity.Area;
import cn.edu.mju.service.AreaService;
import cn.edu.mju.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class AreaController extends BaseController {

    Logger logger  = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/listareas",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> listAreas(){

        logger.info("===start===");
        long startTime = System.currentTimeMillis();
        Map<String,Object> modelMap =  new HashMap<>();

        List<Area> list = new ArrayList<>();

        try{
            list = areaService.getAreaList();
            modelMap.put("rows",list);
            modelMap.put("total",list.size());
            modelMap.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            modelMap.put("success",false);
        }
        logger.error("test error!");
        long endTime = System.currentTimeMillis();
        logger.debug("costTime:[{}ms]",endTime-startTime);
        logger.info("===end===");
        return modelMap;
    }



}

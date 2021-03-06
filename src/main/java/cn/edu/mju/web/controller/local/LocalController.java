package cn.edu.mju.web.controller.local;

import cn.edu.mju.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/local")
public class LocalController extends BaseController {


    /*
    * 绑定账号页面
    * */

    @RequestMapping(value = "/accountbind" , method = RequestMethod.GET)
    public String accountbind(){
        return "/local/accountbind";
    }


    /*
    * 修改密码页面
    *
    * */

    @RequestMapping(value = "/changepsw" , method = RequestMethod.GET)
    public String changepsw(){
        return "/local/changepsw";
    }


    /*
    * 登录页面
    * */
    @RequestMapping(value = "/login" , method = RequestMethod.GET)
    public String login(){
        return "/local/login";
    }


}

package cn.edu.mju.web.controller.local;

import cn.edu.mju.dto.LocalAuthExecution;
import cn.edu.mju.entity.LocalAuth;
import cn.edu.mju.entity.PersonInfo;
import cn.edu.mju.enums.LocalAuthStateEnum;
import cn.edu.mju.exceptions.LoaclAuthOperationException;
import cn.edu.mju.service.LocalAuthService;
import cn.edu.mju.util.CodeUtil;
import cn.edu.mju.util.HttpServletRequestUtil;
import cn.edu.mju.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/local",method = {RequestMethod.GET
        ,RequestMethod.POST})
public class LocalAuthController extends BaseController {


    @Autowired
    private LocalAuthService localAuthService;


    /*
    * 将用户信息与平台账号绑定
    * */

    @RequestMapping(value = "/bindlocalauth",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> bindLocalAuth(HttpServletRequest request){

        Map<String,Object> modelMap = new HashMap<>();

        //验证码校验
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }

        //获取输入的账号
        String userName = HttpServletRequestUtil.getString(request,"userName");
        //获取输入的密码
        String password = HttpServletRequestUtil.getString(request,"password");

        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");

        if(userName != null && password != null && user != null && user.getUserId() != null){

            //创建LocalAuth对象

            LocalAuth localAuth = new LocalAuth();

            localAuth.setUsername(userName);

            localAuth.setPassword(password);

            localAuth.setPersonInfo(user);

            //账号绑定
            LocalAuthExecution localAuthExecution = localAuthService.bindLocalAuth(localAuth);

            if(localAuthExecution.getState() == LocalAuthStateEnum.SUCCESS.getState()){

                //绑定成功
                modelMap.put("success",true);

            }else{
                modelMap.put("success",false);
                modelMap.put("errMsg",localAuthExecution.getStateInfo());
            }

        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","用户名和密码均不能为空");

        }

        return modelMap;
    }


    /*
    * 修改密码
    * */


    @ResponseBody
    @RequestMapping(value = "/changelocalpwd",method = RequestMethod.POST)
    public Map<String,Object> changeLocalPwd(HttpServletRequest request){

        Map<String,Object> modelMap = new HashMap<>();

        //验证码校验
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }

        //获取账号
        String userName = HttpServletRequestUtil.getString(request,"userName");

        //获取旧密码
        String password = HttpServletRequestUtil.getString(request,"password");


        //获取新密码
        String newPassword = HttpServletRequestUtil.getString(request,"newPassword");

        //从session中获取当前用户信息（用户一旦通过微信登录后，就能获取到用户的信息）
        PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
        if(userName != null && password != null && newPassword != null &&
                user != null && user.getUserId() != null && !password.equals(newPassword)){

            try{

                //查看原先账号 看看与输入的账号是否一致 不一致则认为是非空操作

                LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());

                if(localAuth == null || !localAuth.getUsername().equals(userName)){

                    //不一致则直接退出
                    modelMap.put("success",false);
                    modelMap.put("errMsg","输入的账号非本次登录的账号");
                    return modelMap;
                }

                //修改平台账号的用户密码
                LocalAuthExecution localAuthExecution = localAuthService.modifyLocalAuth(user.getUserId(),userName,password,newPassword);
                if(localAuthExecution.getState() == LocalAuthStateEnum.SUCCESS.getState()){

                    modelMap.put("success",true);

                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",localAuthExecution.getStateInfo());
                }


            }catch (LoaclAuthOperationException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }



        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入密码");
        }


        return modelMap;

    }


    /*
    *
    * 登录校验
    * */
    @ResponseBody
    @RequestMapping(value = "/logincheck",method = RequestMethod.POST)
    public Map<String,Object> loginCheck(HttpServletRequest request){

        Map<String,Object> modelMap =  new HashMap<>();

        //获取是否需要进行验证码校验的标识符
        boolean needVerify = HttpServletRequestUtil.getBoolean(request,"needVerify");

        if(needVerify && !CodeUtil.checkVerifyCode(request)){

            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return  modelMap;
        }

        //获取输入的账号
        String userName = HttpServletRequestUtil.getString(request,"userName");

        //获取输入的密码
        String password = HttpServletRequestUtil.getString(request,"password");
        //非空判断
        if(userName != null && password != null){
            //传入账号和密码去获取平台账号信息

            LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(userName,password);

            if(localAuth != null){


                modelMap.put("success",true);
                request.getSession().setAttribute("user",localAuth.getPersonInfo());
            }else{
                modelMap.put("success",false);
                modelMap.put("errMsg","用户名或密码错误");

            }


        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","用户名和密码不能为空");
        }



        return modelMap;
    }


    @ResponseBody
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public Map<String,Object> logout(HttpServletRequest request){

        Map<String,Object> modelMap = new HashMap<>();

        //将用户session置为空
        request.getSession().setAttribute("user",null);

        modelMap.put("success",true);

        return modelMap;
    }









}

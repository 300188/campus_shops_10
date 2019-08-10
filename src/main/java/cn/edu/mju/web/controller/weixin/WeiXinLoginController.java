package cn.edu.mju.web.controller.weixin;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.mju.dto.ShopExecution;
import cn.edu.mju.dto.UserAccessToken;
import cn.edu.mju.dto.WechatAuthExecution;
import cn.edu.mju.dto.WeiXinUser;
import cn.edu.mju.entity.PersonInfo;
import cn.edu.mju.entity.WechatAuth;
import cn.edu.mju.enums.WechatAuthStateEnum;
import cn.edu.mju.service.PersonInfoService;
import cn.edu.mju.service.ShopService;
import cn.edu.mju.service.WechatAuthService;
import cn.edu.mju.util.weixin.WeiXinUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/wechatlogin")
/**
 * 从微信菜单点击后调用的接口，可以在url里增加参数（role_type）来表明是从商家还是从玩家按钮进来的，依次区分登陆后跳转不同的页面
 * 玩家会跳转到index.html页面
 * 商家如果没有注册，会跳转到注册页面，否则跳转到任务管理页面
 * 如果是商家的授权用户登陆，会跳到授权店铺的任务管理页面
 */
public class WeiXinLoginController {

	private static Logger log = LoggerFactory
			.getLogger(WeiXinLoginController.class);

	@Autowired
	private PersonInfoService personInfoService;

	@Autowired
	private WechatAuthService wechatAuthService;

	@Autowired
	private ShopService shopService;

	private static final String FRONTEND = "1";

	private static final String SHOPEND = "2";

	/*
	* https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxea710d4c0501ffeb&redirect_uri=https://b2f92d98.ngrok.io/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
	* */


	@RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		log.debug("weixin login get...");
		String code = request.getParameter("code");
		String roleType = request.getParameter("state");
		log.debug("weixin login code:" + code);
		WechatAuth auth = null;
		WeiXinUser user = null;
		String openId = null;
		if (null != code) {
			UserAccessToken token;
			try {
				token = WeiXinUserUtil.getUserAccessToken(code);
				log.debug("weixin login token:" + token.toString());
				String accessToken = token.getAccessToken();
				openId = token.getOpenId();
				user = WeiXinUserUtil.getUserInfo(accessToken, openId);
				if(user != null){
					log.debug("weixin login user:" + user.toString());
				}
				request.getSession().setAttribute("openId", openId);
				auth = wechatAuthService.getWechatAuthByOpenId(openId);
			} catch (IOException e) {
				log.error("error in getUserAccessToken or getUserInfo or findByOpenId: "
						+ e.toString());
				e.printStackTrace();
			}
		}
		log.debug("weixin login success.");
		log.debug("login role_type:" + roleType);
		WechatAuthExecution we = null;
		PersonInfo personInfo = new PersonInfo();
		if(auth == null  && user != null){
			try{

				personInfo = WeiXinUserUtil.getPersonInfoFromRequest(user);

				auth = new WechatAuth();
				auth.setOpenid(openId);

				if (FRONTEND.equals(roleType)) {
					personInfo.setUserType(1);
				}else if (SHOPEND.equals(roleType)){
					personInfo.setUserType(2);
				}
				auth.setPersonInfo(personInfo);
				we = wechatAuthService.register(auth);
				if(we == null || we.getState() != WechatAuthStateEnum.SUCCESS.getState()){
					return null;
				}
			}catch (Exception e){
				return null;
			}

		}
		try{

			personInfo = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());

		}catch (Exception e){
			return null;
		}
		request.getSession().setAttribute("user", personInfo);

		if (FRONTEND.equals(roleType)){
			return "/frontend/index";
		}else if(SHOPEND.equals(roleType)){
			return "/shop/shoplist";
		}else{
			return null;
		}

	}
}


package cn.edu.mju.util.weixin;

import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.util.Properties;

import cn.edu.mju.dto.UserAccessToken;

import cn.edu.mju.dto.WeiXinUser;
import cn.edu.mju.entity.PersonInfo;
import cn.edu.mju.util.DESUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;


public class WeiXinUserUtil {

	private static Logger log = LoggerFactory.getLogger(WeiXinUserUtil.class);

	//将WechatUser转换为PersonInfo
	public static PersonInfo getPersonInfoFromRequest(WeiXinUser user) {
		PersonInfo personInfo = new PersonInfo();
		personInfo.setName(user.getNickName());
		personInfo.setGender(user.getSex() + "");
		personInfo.setImage(user.getHeadimgurl());
		personInfo.setEnableStatus(1);
		return personInfo;
	}


	public static UserAccessToken getUserAccessToken(String code)
			throws IOException {

		Properties pro = new Properties();
		pro.load(WeiXinUserUtil.class.getClassLoader().getResourceAsStream(
				"weixin.properties"));
		String appId = DESUtils
				.getDecryptString(pro.getProperty("weixinappid"));
		log.debug("appId:" + appId);
		String appsecret = DESUtils.getDecryptString(pro
				.getProperty("weixinappsecret"));
		log.debug("secret:" + appsecret);
		//根据传入的code拼接出访问微信定义好的接口的URL
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appId + "&secret=" + appsecret + "&code=" + code
				+ "&grant_type=authorization_code";
		String tokenStr = httpsRequest(url,"GET",null);

		UserAccessToken token = new UserAccessToken();
		ObjectMapper objectMapper = new ObjectMapper();

		try{

			token = objectMapper.readValue(tokenStr,UserAccessToken.class);


		}catch (Exception e){
			log.error("获取用户accessToken失败" + e.getMessage());
			e.printStackTrace();
		}

		return token;
	}

	public static WeiXinUser getUserInfo(String accessToken, String openId) {
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
				+ accessToken + "&openid=" + openId + "&lang=zh_CN";
		String str = httpsRequest(url, "GET", null);

		WeiXinUser user = new WeiXinUser();
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			user = objectMapper.readValue(str, WeiXinUser.class);

			if(user.getOpenId() == null){
				return null;
			}

		} catch (Exception e) {
			log.error("获取用户信息失败" + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return user;
	}



	private static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {

		StringBuffer buffer = new StringBuffer();

		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			log.debug("https buffer:"+buffer.toString());

		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return buffer.toString();
	}



}

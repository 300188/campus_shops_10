package cn.edu.mju.web.controller.frontend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.mju.entity.PersonInfo;
import cn.edu.mju.entity.Product;
import cn.edu.mju.service.ProductService;
import cn.edu.mju.util.HttpServletRequestUtil;
import cn.edu.mju.util.QRCodeUtil;
import cn.edu.mju.util.baidu.ShortNetAddress;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/frontend")
public class ProductDetailController {

	@Autowired
	private ProductService productService;

	private static String URLPREFIX = "https://open.weixin.qq.com/connect/oauth2/authorize?"
			+ "appid=wxd7f6c5b8899fba83&"
			+ "redirect_uri=115.28.159.6/myo2o/shop/adduserproductmap&"
			+ "response_type=code&scope=snsapi_userinfo&state=";
	private static String URLSUFFIX = "#wechat_redirect";

	@RequestMapping(value = "/listproductdetailpageinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> listProductDetailPageInfo(
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		Product product = null;
		if (productId != -1) {
			product = productService.getProductById(productId);
			modelMap.put("product", product);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/generateqrcode4product", method = RequestMethod.GET)
	@ResponseBody
	public void generateQRCode4Product(HttpServletRequest request,
			HttpServletResponse response) {
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		PersonInfo user = (PersonInfo) request.getSession()
				.getAttribute("user");
		if(user == null){

			user = new PersonInfo();
			user.setUserId(1L);
		}
		if (productId != -1 && user != null && user.getUserId() != null) {
			try {
				long timpStamp = System.currentTimeMillis();
				String content = "{\"productId\":" + productId + ",\"customerId\":"
						+ user.getUserId() + ",\"createTime\":" + timpStamp + "}";
				String longUrl = URLPREFIX + content + URLSUFFIX;
				String shortUrl = ShortNetAddress.getShortURL(longUrl);
				BitMatrix qRcodeImg = QRCodeUtil.generateQRCodeStream(shortUrl,
						response);

				MatrixToImageWriter.writeToStream(qRcodeImg, "png",
						response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

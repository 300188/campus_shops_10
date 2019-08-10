package cn.edu.mju.web.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/frontend")
public class FrontendController {

	@RequestMapping(value = "/mainpage", method = RequestMethod.GET)
	public String showMainPage() {
		return "/frontend/mainpage";
	}

	@RequestMapping(value = "/productdetail", method = RequestMethod.GET)
	public String showProductDetail() {
		return "/frontend/productdetail";
	}

	@RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
	public String showShopDetail() {
		return "/frontend/shopdetail";
	}

	@RequestMapping(value = "/shoplist", method = RequestMethod.GET)
	public String showShopList() {
		return "/frontend/shoplist";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "/frontend/index";
	}

	@RequestMapping(value = "/mypoint", method = RequestMethod.GET)
	public String myPoint() {
		return "/frontend/mypoint";
	}

	@RequestMapping(value = "/myrecord", method = RequestMethod.GET)
	public String myRecord() {
		return "/frontend/myrecord";
	}

	@RequestMapping(value = "/pointrecord", method = RequestMethod.GET)
	public String pointRecord() {
		return "/frontend/pointrecord";
	}

	@RequestMapping(value = "/awarddetail", method = RequestMethod.GET)
	public String awardDetail() {
		return "/frontend/awarddetail";
	}

	@RequestMapping(value = "/customerbind", method = RequestMethod.GET)
	public String customerBind() {
		return "/local/customerbind";
	}

}

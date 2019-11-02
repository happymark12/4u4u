package _4u4u.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	// 表示"/"的請求，將會傳回"index"
	// 亦即回應的view將導向WEB-INF/views/

	@RequestMapping(value = "/properties", method = RequestMethod.GET)
	public String getSearchPage(Model model, HttpServletRequest request) {
		return "_4u4u_Search/properties";
	}
	@RequestMapping(value = "/PostRoomRentAd", method = RequestMethod.GET)
	public String getPostRoomRentAdPage() {
		return "_4u4u_PostAd/PostRoomRentAd";
	}

	@RequestMapping(value = "/PostWantedRoomAd", method = RequestMethod.GET)
	public String getPostWantedRoomAdPage() {
		return "_4u4u_PostAd/PostWantedRoomAd";
	}


	@RequestMapping(value = "/whyuse", method = RequestMethod.GET)
	public String getWhyUsePage() {
		return "_4u4u_AboutUs/why_use";
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String getContactPage() {
		return "_4u4u_AboutUs/contact";
	}
	
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String getAccountPage() {
		return "_4u4u_Account/account";
	}
	@RequestMapping(value = "/qa", method = RequestMethod.GET)
	public String getQAPage() {
		return "_4u4u_AboutUs/qa";
	}
	@RequestMapping(value = "/activity_detail", method = RequestMethod.GET)
	public String getActivityDetailPage() {
		return "_4u4u_Activity/activity_detail";
	}
	

	@RequestMapping("/")
	public String home() {
//		System.out.println("hello world");
		return "index";
	}

}

package _4u4u.controller;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import _4u4u.model.MemberBean;
import _4u4u.model.RoomRentBean;
import _4u4u.model.WantedRoomBean;
import _4u4u.service.LoginService;
import _4u4u.service.MemberService;
import _4u4u.service.RoomRentService;
import _4u4u.service.WantedRoomService;

@Controller
public class MessageController {

	MemberService memberService;
	WantedRoomService wantedRoomService;
	RoomRentService roomRentService;

	@Autowired
	public void setWantedRoomService(WantedRoomService wantedRoomService) {
		this.wantedRoomService = wantedRoomService;
	}

	@Autowired
	public void setRoomRentService(RoomRentService roomRentService) {
		this.roomRentService = roomRentService;
	}


	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}


	
	@RequestMapping(value = "/loginCheck", method = RequestMethod.GET)
	public void checkLogin(HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");

		if (mb == null) {

			try (PrintWriter pw = response.getWriter();) {
				pw.write("{\"result\":\"false\"}");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;

		}else {
			try (PrintWriter pw = response.getWriter();) {
				pw.write("{"
						+ "\"result\":\"true\""
						+ ",\"userId\":\""+mb.getEmail().trim()
						+ "\"}");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
			
		}

	}
	//確認此廣告是否為當前使用者發佈以及是否可發送訊息
	@RequestMapping(value = "/adCheck", method = RequestMethod.GET, params = { "adStyle", "adId" })
	public void checkAdContactStatus(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "adStyle") String adStyle, @RequestParam(value = "adId") Integer adId) {
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");
		
		
		if (mb == null) {

			try (PrintWriter pw = response.getWriter();) {
				pw.write("{\"result\":\"false\"}");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;

		}else {
			
			boolean checkAdOwner = memberService.checkAdOwner(mb, adStyle, adId);
				
				try (PrintWriter pw = response.getWriter();) {
					if(checkAdOwner) {
					pw.write("{"
							+ "\"result\":\"forbid\""
							+ "}");
					}else {
					pw.write("{"
								+ "\"result\":\"allow\""
								+ "}");	
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return;
		}
			

	}

	@RequestMapping(value = "/sendMessage/{memId}/{adStyle}/{adId}", method = RequestMethod.GET)
	public String getSendMessagePage(ModelMap model,	HttpServletRequest request,
			@PathVariable("memId") Integer memId,
			@PathVariable("adStyle") String adStyle,
			@PathVariable("adId") Integer adId
			) {
		
		
		
		HttpSession session = request.getSession();
		MemberBean toMemBean = memberService.queryMemberById(memId);
		MemberBean fromMemBean = (MemberBean) session.getAttribute("LoginOK");
		model.addAttribute("to",toMemBean);
		model.addAttribute("from",fromMemBean);
		if(adStyle.trim().contentEquals("0")) {
			RoomRentBean rBean = roomRentService.getAdById(adId);
			model.addAttribute("ad",rBean);
			String url = "/_4u4u/roomRentDetail?adStyle=0&adId="+adId;
			model.addAttribute("url",url);
		}else {
			WantedRoomBean wBean = wantedRoomService.getAdById(adId);
			model.addAttribute("ad",wBean);
			String url = "/_4u4u/findRoomDetail?adStyle=1&adId="+adId;
			model.addAttribute("url",url);
		}
			
		return "_4u4u_Account/sendMessage";
	}

	
}


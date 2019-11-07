package _4u4u.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import _4u4u.service.MemberService;
import _4u4u.service.MessageService;
import _4u4u.service.RoomRentService;
import _4u4u.service.WantedRoomService;

@Controller
public class MessageController {

	MemberService memberService;
	WantedRoomService wantedRoomService;
	RoomRentService roomRentService;
	MessageService messageService;
	
	@Autowired
	public void setWantedRoomService(WantedRoomService wantedRoomService) {
		this.wantedRoomService = wantedRoomService;
	}

	@Autowired
	public void setRoomRentService(RoomRentService roomRentService) {
		this.roomRentService = roomRentService;
	}

	@Autowired
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
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
			
			String contactStatus = memberService.checkAdContactStatus(mb, adStyle, adId);
				
				try (PrintWriter pw = response.getWriter();) {
					if(contactStatus.trim().contentEquals("ok")) {
					pw.write("{"
							+ "\"result\":\"ok\""
							+ "}");
					}else if(contactStatus.trim().contentEquals("adOwner")){
					pw.write("{"
								+ "\"result\":\"adOwner\""
								+ "}");	
						
					}else if(contactStatus.trim().contentEquals("earlyBird")){
					pw.write("{"
								+ "\"result\":\"earlyBird\""
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
		
		
		
		MemberBean toMemBean = memberService.queryMemberById(memId);
		model.addAttribute("to",toMemBean);
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

	@RequestMapping(value = "/myMessage", method = RequestMethod.GET)
	public String getMyMessagePage(Model model, HttpServletRequest request) {
		HttpSession session =request.getSession();
		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");
		List<Map<String, String>> contactsMap = new ArrayList<Map<String,String>>();
		contactsMap = messageService.getContacts(mb);
		int num  = contactsMap.size();
		model.addAttribute("contactsMap",contactsMap);
		model.addAttribute("totalNum",num);
		return "_4u4u_Account/myMessage";
	}
	
	@RequestMapping(value = "/historyMessages/{memId}", method = RequestMethod.GET)
	public void getHistoryMessage(Model model, HttpServletRequest request,HttpServletResponse response,
			@PathVariable("memId") Integer memId) {
		response.setCharacterEncoding("UTF-8");
		HttpSession session =request.getSession();
		MemberBean targetMb = (MemberBean) session.getAttribute("LoginOK");
		MemberBean fromMb = memberService.queryMemberById(memId);
		
		
		try (PrintWriter pw = response.getWriter();) {
			pw.write( messageService.getHistoryMessage(targetMb,fromMb));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ;
	}
	
	@RequestMapping(value = "/getNewMessageCount/{memId}", method = RequestMethod.GET)
	public void getNewMessageCount(Model model, HttpServletRequest request,HttpServletResponse response,
			@PathVariable("memId") Integer memId) {
		response.setCharacterEncoding("UTF-8");
		HttpSession session =request.getSession();
		MemberBean targetMb = (MemberBean) session.getAttribute("LoginOK");
		
		
		try (PrintWriter pw = response.getWriter();) {
			int num = messageService.getNewMessageCount(targetMb, memId);
			pw.write("{\"result\":\""+num+"\"}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ;
	}
	
	@RequestMapping(value = "/updateNewMessageCount/{memId}", method = RequestMethod.GET)
	public void updateNewMessageCount(Model model, HttpServletRequest request,HttpServletResponse response,
			@PathVariable("memId") Integer memId) {
		response.setCharacterEncoding("UTF-8");
		HttpSession session =request.getSession();
		MemberBean targetMb = (MemberBean) session.getAttribute("LoginOK");
		
		
		try (PrintWriter pw = response.getWriter();) {
			int num = messageService.updateNewMessageCount(targetMb, memId);
			if(num>0) {
				pw.write("{\"result\":\"success\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ;
	}
}


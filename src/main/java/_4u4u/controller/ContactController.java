package _4u4u.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ContactController {
	
	@RequestMapping(value = "/contactMessageSuccess", method = RequestMethod.POST)
	public String processContactMessage(Model model, HttpServletRequest request, HttpServletResponse response){
		return "redirect:/contactMessageSuccessPage";
	}
	@RequestMapping(value = "/contactMessageSuccessPage", method = RequestMethod.GET)
	public String contactMessageSuccess(Model model, HttpServletRequest request, HttpServletResponse response){
		return "/_4u4u_AboutUs/contact-success";
	}
}

package _4u4u.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import _4u4u.model.EventBean;
import _4u4u.service.EventService;

@Controller
public class EventController {
//	private static final long serialVersionUID = 1L;


	ServletContext context;

	EventService eventService;

	

	@Autowired
	public void setContext(ServletContext context) {
		this.context = context;
	}

	@Autowired
	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	@RequestMapping(value = "/_4u4u/eventGetImage", method = RequestMethod.GET)
	public void getEventImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream os = null;
		InputStream is = null;
		String fileName = null;
		String mimeType = null;
		Blob blob = null;
		try {
			// 讀取瀏覽器傳送來的主鍵
			Integer id = Integer.parseInt(request.getParameter("id").trim());
			// 讀取瀏覽器傳送來的type，以分辨要處理哪個表格
			String type = request.getParameter("type");
			switch (type.toUpperCase()) {

			case "EVENT":
//				EventService EventService = new EventServiceImpl();
				EventBean eventbean = eventService.getEventById(id);
				if (eventbean != null) {
					blob = eventbean.getImage();
					if (blob != null) {
						is = blob.getBinaryStream();
					}
					fileName = eventbean.getImageName();
				}
				break;

			default:
				break;
			}

			// 如果圖片的來源有問題，就送回預設圖片(/img/NoImage.png)
			if (is == null) {
				fileName = "NoImage.png";
				is = context.getResourceAsStream("/WEB-INF/views/img/" + fileName);
			}

			// 由圖片檔的檔名來得到檔案的MIME型態
			mimeType = context.getMimeType(fileName);
			// 設定輸出資料的MIME型態
			response.setContentType(mimeType);
			// 取得能寫出非文字資料的OutputStream物件
			os = response.getOutputStream();
			// 由InputStream讀取位元組，然後由OutputStream寫出
			int len = 0;
			byte[] bytes = new byte[8192];
			while ((len = is.read(bytes)) != -1) {
				os.write(bytes, 0, len);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberController.retrieveImage()#doGet()Exception: " + ex.getMessage());
		} finally {
			if (is != null)
				is.close();
			if (os != null)
				os.close();

		}
	}
	
	@RequestMapping(value = "/activity", method = RequestMethod.GET)
	public String getAllEvent(Model model, HttpServletRequest request, HttpServletResponse response) {
//	public String getActivityPage(Model model, HttpServletRequest request, HttpServletResponse response) {
//		request.setCharacterEncoding("UTF-8");
//		EventService eventService = new EventServiceImpl();
		List<EventBean> list = eventService.getEvents();
		request.setAttribute("eventBean", list);

		// 轉換頁面的方法
//		RequestDispatcher rd = request.getRequestDispatcher("/_4u4u_Activity/activity.jsp");
//		RequestDispatcher rd = request.getRequestDispatcher("/activity");
//		rd.forward(request, response);

		return "_4u4u_Activity/activity";
	}
	
	@RequestMapping(value = "/_4u4u/activity_detail", method = RequestMethod.GET)
	public String getEvent(Model model, HttpServletRequest request, HttpServletResponse response) {
//		List<EventBean> list = eventService.getEvents();	//取多筆資料，設<>泛型
		String strEventId = request.getParameter("eventId");	
		Integer eventId = null;		
		if (strEventId != null) {	
			eventId = Integer.parseInt(strEventId);
				} ;					
		EventBean list = eventService.getEventById(eventId);		//取單筆資料
		model.addAttribute("eventBean", list);
		
//		System.out.println(list.getAddress());
		

		// 轉換頁面的方法
//		RequestDispatcher rd = request.getRequestDispatcher("/_4u4u_Activity/activity.jsp");
//		RequestDispatcher rd = request.getRequestDispatcher("/activity");
//		rd.forward(request, response);

		return "_4u4u_Activity/activity_detail";
	}
	@RequestMapping(value = "/activity-success", method = RequestMethod.GET)
	public String getEventsDetail(Model model, HttpServletRequest request, HttpServletResponse response) {
//	public String getActivityPage(Model model, HttpServletRequest request, HttpServletResponse response) {
//		request.setCharacterEncoding("UTF-8");
//		EventService eventService = new EventServiceImpl();
//		List<EventBean> list = eventService.getEvents();
//		request.setAttribute("eventBean", list);

		// 轉換頁面的方法
//		RequestDispatcher rd = request.getRequestDispatcher("/_4u4u_Activity/activity.jsp");
//		RequestDispatcher rd = request.getRequestDispatcher("/activity");
//		rd.forward(request, response);

		return "_4u4u_Activity/activity-success";
	}
}
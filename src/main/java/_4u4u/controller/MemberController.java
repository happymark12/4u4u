package _4u4u.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import _4u4u.model.MemberBean;
import _4u4u.service.LoginService;
import _4u4u.service.MemberService;
import _4u4u_init.util.GlobalService;
import _4u4u_init.util.MailUtil;
import _4u4u_init.util.SystemUtils2018;

@Controller
public class MemberController {

	MemberService memberService;

	LoginService loginService;

	ServletContext context;
	MemberBean memberBean;

	@Autowired
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Autowired
	public void setContext(ServletContext context) {
		this.context = context;
	}

	@RequestMapping(value = "/_4u4u/getImage", method = RequestMethod.GET)
	public void getMemberImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
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

			case "MEMBER":
//					MemberService memberService = new MemberServiceImpl();
				MemberBean bean = memberService.queryMemberById(id);
				if (bean != null) {
					blob = bean.getPic();
					if (blob != null) {
						is = blob.getBinaryStream();
					}
					fileName = bean.getPicName();
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
	
	@RequestMapping(value = "/_4u4u/register.do", method = RequestMethod.POST)
	public String processRegisterMemberForm(HttpServletRequest request, HttpServletResponse response, Model model) {
		final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%!^'\"]).{8,})";
		Pattern pattern = null;
		Matcher matcher = null;
//			if (!errorMsg.isEmpty() || errors.hasErrors()) {
//				model.addAttribute("errorMsg", errorMsg);
//				return "/member/register";

		Map<String, String> errorMsg = new HashMap<String, String>();
		// 準備存放註冊成功之訊息的Map物件
		Map<String, String> msgOK = new HashMap<String, String>();
		// 註冊成功後將用response.sendRedirect()導向新的畫面，所以需要
		// session物件來存放共用資料。
		HttpSession session = request.getSession();
		model.addAttribute("MsgMap", errorMsg); // 顯示錯誤訊息
		session.setAttribute("MsgOK", msgOK); // 顯示正常訊息

		String password = "";
		String password2 = "";
		String name = "";
		String email = "";
		String gender = "";
		Blob pic = null;
		String fileName = "";
		long sizeInBytes = 0;
		InputStream is = null;
		// 取出HTTP multipart request內所有的parts
		Collection<Part> parts;
		try {
			parts = request.getParts();

			GlobalService.exploreParts(parts, request);
			// 由parts != null來判斷此上傳資料是否為HTTP multipart request
			if (parts != null) { // 如果這是一個上傳資料的表單
				for (Part p : parts) {
					String fldName = p.getName();
					String value = request.getParameter(fldName);

					// 1. 讀取使用者輸入資料
					if (p.getContentType() == null) {
						if (fldName.equals("eMail")) {
							email = value;
						} else if (fldName.equals("password")) {
							password = value;
						} else if (fldName.equalsIgnoreCase("password2")) {
							password2 = value;
						} else if (fldName.equalsIgnoreCase("name")) {
							name = value;
						} else if (fldName.equalsIgnoreCase("gender")) {
							gender = value;
						}
					} else {
						// 取出圖片檔的檔名
						fileName = GlobalService.getFileName(p);
						// 調整圖片檔檔名的長度，需要檔名中的附檔名，所以調整主檔名以免檔名太長無法寫入表格
						fileName = GlobalService.adjustFileName(fileName, GlobalService.IMAGE_FILENAME_LENGTH);

						if (fileName != null && fileName.trim().length() > 0) {

							sizeInBytes = p.getSize();
							is = p.getInputStream();

						} else {

//								errorMsg.put("errPicture", "必須挑選圖片檔");
						}
					}
				}
				// 2. 進行必要的資料轉換
				// (無)
				// 3. 檢查使用者輸入資料
				if (gender == null || gender.trim().length() == 0) {
					errorMsg.put("errorGenderEmpty", "性別必須選擇");
				}
				if (email == null || email.trim().length() == 0) {
					errorMsg.put("errorEmailEmpty", "信箱欄必須輸入");
				}
				if (password == null || password.trim().length() == 0) {
					errorMsg.put("errorPasswordEmpty", "密碼欄必須輸入");
				}
				if (password2 == null || password2.trim().length() == 0) {
					errorMsg.put("errorPassword2Empty", "密碼確認欄必須輸入");
				}
				if (password.trim().length() > 0 && password2.trim().length() > 0) {
					if (!password.trim().equals(password2.trim())) {
						errorMsg.put("errorPassword2Empty", "密碼欄必須與確認欄一致");
						errorMsg.put("errorPasswordEmpty", "");
					}
				}

				if (name == null || name.trim().length() == 0) {
					errorMsg.put("errorName", "姓名欄必須輸入");
				}

			} else {
				errorMsg.put("errTitle", "此表單不是上傳檔案的表單");
			}
			// 如果有錯誤
			if (errorMsg.isEmpty()) {
				pattern = Pattern.compile(PASSWORD_PATTERN);
				matcher = pattern.matcher(password);
				if (!matcher.matches()) {
					errorMsg.put("passwordError", "密碼至少含有一個大寫字母、小寫字母、數字與!@#$%!^'\"等四組資料組合而成，且長度不能小於八個字元");
				}
			}
			// 如果有錯誤
			if (!errorMsg.isEmpty()) {
				// 導向原來輸入資料的畫面，這次會顯示錯誤訊息

				return "_4u4u_Register/register";
			}

			// 4. 產生MemberDao物件，以便進行Business Logic運算
			// MemberDaoImpl_Jdbc類別的功能：
			// 1.檢查帳號是否已經存在，已存在的帳號不能使用，回傳相關訊息通知使用者修改
			// 2.若無問題，儲存會員的資料
//			MemberService service = new MemberServiceImpl();
			if (memberService.emailExists(email)) {
				errorMsg.put("errorIDDup", "此帳號已存在，請換新帳號");
			} else {
				// 為了配合Hibernate的版本。
				// 要在此加密，不要在 dao.saveMember(mem)進行加密
				password = GlobalService.getMD5Endocing(GlobalService.encryptString(password));
				Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
				if (is != null) {
					pic = SystemUtils2018.fileToBlob(is, sizeInBytes);
				} else {
					pic = null;
				}
				// 將所有會員資料封裝到MemberBean(類別的)物件
				MemberBean mem = new MemberBean(null, email, password, gender, pic, name, fileName, null, null, null,
						ts);
				// 呼叫MemberDao的saveMember方法
				int n = memberService.saveMember(mem);
				if (n == 1) {

					msgOK.put("InsertOK", "<Font color='red'>新增成功，請到信箱點擊驗證信以激活帳戶</Font>");
//					System.out.println(mem.getMemId());
//							mem= service.queryMemberByEmail(email);
//							session.setAttribute("LoginOK", mem);
					new Thread(new MailUtil(mem.getEmail(), "1")).start();
//							response.sendRedirect(response.encodeRedirectURL("/4u4u/"));
					return "redirect:/";
				} else {
					errorMsg.put("errorIDDup", "新增此筆資料有誤(RegisterServlet)");

				}
			}
			// 5.依照 Business Logic 運算結果來挑選適當的畫面
			if (!errorMsg.isEmpty()) {
				// 導向原來輸入資料的畫面，這次會顯示錯誤訊息
				return "_4u4u_Register/register";
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMsg.put("errorIDDup", e.getMessage());
			return "_4u4u_Register/register";
		}
		return "redirect:/";

	}

	@RequestMapping(value = "/_4u4u/login.do", method = RequestMethod.POST)
	public String processLoginForm(HttpServletRequest request, HttpServletResponse response, Model model)
			throws IOException {
//	request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		// 定義存放錯誤訊息的Map物件
		Map<String, String> errorMsgMap = new HashMap<String, String>();

		// 將errorMsgMap放入request物件內，識別字串為 "ErrorMsgKey"
		model.addAttribute("ErrorMsgKey", errorMsgMap);
		// 1. 讀取使用者輸入資料
		String userId = request.getParameter("userId");
		String password = request.getParameter("pswd");
		String rm = request.getParameter("rememberMe");
		String requestURI = (String) session.getAttribute("requestURI");
		// 2. 進行必要的資料轉換
		// 無
		// 3. 檢查使用者輸入資料
		// 如果 userId 欄位為空白，放一個錯誤訊息到 errorMsgMap 之內
		if (userId == null || userId.trim().length() == 0) {
			errorMsgMap.put("AccountEmptyError", "帳號欄必須輸入");
		}
		// 如果 password 欄位為空白，放一個錯誤訊息到 errorMsgMap 之內
		if (password == null || password.trim().length() == 0) {
			errorMsgMap.put("PasswordEmptyError", "密碼欄必須輸入");
		}

		// 如果 errorMsgMap 不是空的，表示有錯誤，交棒給login.jsp
		if (!errorMsgMap.isEmpty()) {
			return "_4u4u_Login/login";
		}

		// **********Remember Me****************************
		Cookie cookieUser = null;
		Cookie cookiePassword = null;
		Cookie cookieRememberMe = null;
		// rm存放瀏覽器送來之RememberMe的選項，如果使用者對RememberMe打勾，rm就不會是null
		if (rm != null) {
			cookieUser = new Cookie("user", userId);
			cookieUser.setMaxAge(7 * 24 * 60 * 60); // Cookie的存活期: 七天
			cookieUser.setPath(request.getContextPath());

			String encodePassword = GlobalService.encryptString(password);
			cookiePassword = new Cookie("password", encodePassword);
			cookiePassword.setMaxAge(7 * 24 * 60 * 60);
			cookiePassword.setPath(request.getContextPath());

			cookieRememberMe = new Cookie("rm", "true");
			cookieRememberMe.setMaxAge(7 * 24 * 60 * 60);
			cookieRememberMe.setPath(request.getContextPath());
		} else { // 使用者沒有對 RememberMe 打勾
			cookieUser = new Cookie("user", userId);
			cookieUser.setMaxAge(0); // MaxAge==0 表示要請瀏覽器刪除此Cookie
			cookieUser.setPath(request.getContextPath());

			String encodePassword = GlobalService.encryptString(password);
			cookiePassword = new Cookie("password", encodePassword);
			cookiePassword.setMaxAge(0);
			cookiePassword.setPath(request.getContextPath());

			cookieRememberMe = new Cookie("rm", "true");
			cookieRememberMe.setMaxAge(0);
			cookieRememberMe.setPath(request.getContextPath());
		}
		response.addCookie(cookieUser);
		response.addCookie(cookiePassword);
		response.addCookie(cookieRememberMe);
		// ********************************************

		// 4. 進行 Business Logic 運算
		// 將LoginServiceImpl類別new為物件，存放物件參考的變數為 loginService
//	LoginService loginService = new LoginServiceImpl();

		// 將密碼加密兩次，以便與存放在表格內的密碼比對
		password = GlobalService.getMD5Endocing(GlobalService.encryptString(password));
		MemberBean mb = null;
		try {
			// 呼叫 loginService物件的 checkIDPassword()，傳入userid與password兩個參數
			mb = loginService.checkEmailPassword(userId, password);
			if (mb != null) {
				// OK, 登入成功, 將mb物件放入Session範圍內，識別字串為"LoginOK"
				if (mb.getState().trim().equals("3") || mb.getState().trim().equals("4")) {
					session.setAttribute("AccountBanned", "您的帳戶已被凍結,請撥打  (02)2771-2171#1720 以便重新激活你的帳戶");
//				System.out.println(mb.getState());
//				response.sendRedirect(response.encodeRedirectURL(request.getContextPath()));
//					session.invalidate();
					return "redirect:/";
				}
				if (mb.getState().trim().equals("0")) {
					session.setAttribute("ActivateAccount", "請至信箱點擊驗證信以激活帳戶");

//					session.invalidate();
//				System.out.println(mb.getState());
//				response.sendRedirect(response.encodeRedirectURL(request.getContextPath()));
					return "redirect:/";
				}
				session.setAttribute("LoginOK", mb);
				session.setAttribute("LoginSuccess", "登入成功");
			} else {
				// NG, 登入失敗, userid與密碼的組合錯誤，放相關的錯誤訊息到 errorMsgMap 之內
				errorMsgMap.put("LoginError", "該帳號不存在或密碼錯誤");
			}
		} catch (RuntimeException ex) {
			errorMsgMap.put("LoginError", ex.getMessage());
		}

		// 5.依照 Business Logic 運算結果來挑選適當的畫面
		// 如果 errorMsgMap 是空的，表示沒有任何錯誤，交棒給下一棒
		if (errorMsgMap.isEmpty()) {
			if (requestURI != null) {

//			System.out.println("requestURI=" + requestURI + "*");
				requestURI = (requestURI.length() == 0 ? "/" : requestURI.substring(requestURI.lastIndexOf("/")));
//			response.sendRedirect(response.encodeRedirectURL(requestURI));
				return "redirect:" + requestURI;
			} else {
//			response.sendRedirect(response.encodeRedirectURL(request.getContextPath()));
				return "redirect:/";
			}
		} else {
			// 如果errorMsgMap不是空的，表示有錯誤，交棒給login.jsp
//		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
//		rd.forward(request, response);
			return "_4u4u_Login/login";
		}
	}





	@RequestMapping(value = "/ProcessMemberState.do", method = RequestMethod.GET)
	public String activateMemberAccount(@RequestParam("email") String email,@RequestParam("code") String code,HttpServletRequest req) {
		HttpSession session = req.getSession();
//		String email =  req.getParameter("email");
//		String code =  req.getParameter("code");
//		MemberService service = new MemberServiceImpl();
		if(memberService.changeMemberState(email, code)) {
			session.setAttribute("validate", "驗證成功");
		}
		MemberBean mb = memberService.queryMemberByEmail(email);
		session.setAttribute("LoginOK", mb);
//		resp.sendRedirect(resp.encodeRedirectURL("/4u4u/"));
		return  "redirect:/";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage() {
//		MemberBean mb = new MemberBean();
//		model.addAttribute("loginMb", mb);
		return "_4u4u_Login/login";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegisterPage() {
//		MemberBean mb = new MemberBean();
//		model.addAttribute("loginMb", mb);
		return "_4u4u_Register/register";
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String executeLogout() {
//		MemberBean mb = new MemberBean();
//		model.addAttribute("loginMb", mb);
		return "_4u4u_Login/logout";
	}
	
	
	@RequestMapping(value = "/_4u4u/updateMember" , method = RequestMethod.POST)
	public String updateMemberForm(Model model, HttpServletRequest request, HttpServletResponse response) {
		final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%!^'\"]).{8,})";
		Pattern pattern = null;
		Matcher matcher = null;
//			if (!errorMsg.isEmpty() || errors.hasErrors()) {
//				model.addAttribute("errorMsg", errorMsg);
//				return "/member/register";
		Map<String, String> errorMsg = new HashMap<String, String>();
		// 準備存放註冊成功之訊息的Map物件
		Map<String, String> msgOK = new HashMap<String, String>();
		// 註冊成功後將用response.sendRedirect()導向新的畫面，所以需要
		// session物件來存放共用資料。
		HttpSession session = request.getSession();
		if (!session.isNew()) {
			memberBean = (MemberBean) session.getAttribute("LoginOK");
		} else {
			memberBean = new MemberBean();
		}
		model.addAttribute("MsgMap", errorMsg); // 顯示錯誤訊息
		session.setAttribute("MsgOK", msgOK); // 顯示正常訊息
		// 得到主鍵
		String strMemId = request.getParameter("memId").trim();
		Integer memId = null;		
		if(strMemId != null) {
			memId = Integer.parseInt(strMemId);
		}
		// 得到會員狀態
		String memState = request.getParameter("state");
		String password = "";
		String password2 = "";
		String name = "";
		String email = "";
		String gender = "";
		Blob pic = null;
		String fileName = "";
		long sizeInBytes = 0;
		InputStream is = null;
		// 取出HTTP multipart request內所有的parts
		Collection<Part> parts;
		try {
			parts = request.getParts();

			GlobalService.exploreParts(parts, request);
			// 由parts != null來判斷此上傳資料是否為HTTP multipart request
			if (parts != null) { // 如果這是一個上傳資料的表單
				for (Part p : parts) {
					String fldName = p.getName();
					String value = request.getParameter(fldName);

					// 1. 讀取使用者輸入資料
					if (p.getContentType() == null) {
						if (fldName.equals("eMail")) {
							email = value;
						} else if (fldName.equals("password")) {
							password = value;
						} else if (fldName.equalsIgnoreCase("password2")) {
							password2 = value;
						} else if (fldName.equalsIgnoreCase("name")) {
							name = value;
						} else if (fldName.equalsIgnoreCase("gender")) {
							gender = value;
						}
					} else {
						// 取出圖片檔的檔名
						fileName = GlobalService.getFileName(p);
						// 調整圖片檔檔名的長度，需要檔名中的附檔名，所以調整主檔名以免檔名太長無法寫入表格
						fileName = GlobalService.adjustFileName(fileName, GlobalService.IMAGE_FILENAME_LENGTH);

						if (fileName != null && fileName.trim().length() > 0) {

							sizeInBytes = p.getSize();
							is = p.getInputStream();

						} else {

//								errorMsg.put("errPicture", "必須挑選圖片檔");
						}
					}
				}
				// 2. 進行必要的資料轉換
				// (無)
				// 3. 檢查使用者輸入資料
				if (gender == null || gender.trim().length() == 0) {
					errorMsg.put("errorGenderEmpty", "性別必須選擇");
				}
				if (email == null || email.trim().length() == 0) {
					errorMsg.put("errorEmailEmpty", "信箱欄必須輸入");
				}
				if (password == null || password.trim().length() == 0) {
					errorMsg.put("errorPasswordEmpty", "密碼欄必須輸入");
				}
				if (password2 == null || password2.trim().length() == 0) {
					errorMsg.put("errorPassword2Empty", "密碼確認欄必須輸入");
				}
				if (password.trim().length() > 0 && password2.trim().length() > 0) {
					if (!password.trim().equals(password2.trim())) {
						errorMsg.put("errorPassword2Empty", "密碼欄必須與確認欄一致");
						errorMsg.put("errorPasswordEmpty", "");
					}
				}

				if (name == null || name.trim().length() == 0) {
					errorMsg.put("errorName", "姓名欄必須輸入");
				}

			} else {
				errorMsg.put("errTitle", "此表單不是上傳檔案的表單");
			}
			// 如果有錯誤
			if (errorMsg.isEmpty()) {
				pattern = Pattern.compile(PASSWORD_PATTERN);
				matcher = pattern.matcher(password);
				if (!matcher.matches()) {
					errorMsg.put("passwordError", "密碼至少含有一個大寫字母、小寫字母、數字與!@#$%!^'\"等四組資料組合而成，且長度不能小於八個字元");
				}
			}
			// 如果有錯誤
			if (!errorMsg.isEmpty()) {
				// 導向原來輸入資料的畫面，這次會顯示錯誤訊息
				model.addAttribute("memId", memId);
				model.addAttribute("email", email);
				model.addAttribute("name", name);
				model.addAttribute("state", memState);
				model.addAttribute("gender", gender);
//				return "redirect:/_4u4u/processUpdateMember?memId="+memId+"&state="+memState;
				return "/_4u4u_Account/updateMember";
			}

			// 4. 產生MemberDao物件，以便進行Business Logic運算
			// MemberDaoImpl_Jdbc類別的功能：
			// 1.檢查帳號是否已經存在，已存在的帳號不能使用，回傳相關訊息通知使用者修改
			// 2.若無問題，儲存會員的資料
//			MemberService service = new MemberServiceImpl();
			// 更新不用判斷帳號是否重複
//			if (memberService.emailExists(email)) {
//				errorMsg.put("errorIDDup", "此帳號已存在，請換新帳號");
//			} else {
				// 為了配合Hibernate的版本。
				// 要在此加密，不要在 dao.saveMember(mem)進行加密
				password = GlobalService.getMD5Endocing(GlobalService.encryptString(password));
				Timestamp ts = memberBean.getCreatTime();
				if (is != null) {
					pic = SystemUtils2018.fileToBlob(is, sizeInBytes);
				} else {
					pic = null;
				}
				// 將會員修改的資料要顯示的存成屬性物件
				
				
				
				// 將所有會員資料封裝到MemberBean(類別的)物件
				MemberBean mem = new MemberBean(memId, email, password, gender, pic, name, fileName, memberBean.getState(), null, null,
						ts);
				// 將新的會員資料存成屬性物件
				session.setAttribute("LoginOK", mem);
				// 呼叫MemberDao的saveMember方法
				int n = memberService.updateMember(mem);
				if (n == 1) {

					msgOK.put("UpdateOK", "<Font color='red'>修改成功</Font>");
//					System.out.println(mem.getMemId());
//							mem= service.queryMemberByEmail(email);
//							session.setAttribute("LoginOK", mem);
//					new Thread(new MailUtil(mem.getEmail(), "1")).start();
//							response.sendRedirect(response.encodeRedirectURL("/4u4u/"));
					return "redirect:/account";
				} else {
					errorMsg.put("errorIDDup", "修改此筆資料有誤(RegisterServlet)");

				}
//			}
			// 5.依照 Business Logic 運算結果來挑選適當的畫面
			if (!errorMsg.isEmpty()) {
				// 導向原來輸入資料的畫面，這次會顯示錯誤訊息
				model.addAttribute("memId", memId);
				model.addAttribute("email", email);
				model.addAttribute("name", name);
				model.addAttribute("state", memState);
				model.addAttribute("gender", gender);
				return "/_4u4u_Account/updateMember";
//				return "redirect:/_4u4u/processUpdateMember?memId="+memId+"&state="+memState;
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMsg.put("errorIDDup", e.getMessage());
			model.addAttribute("memId", memId);
			model.addAttribute("email", email);
			model.addAttribute("name", name);
			model.addAttribute("state", memState);
			model.addAttribute("gender", gender);
			return "/_4u4u_Account/updateMember";
//			return "redirect:/_4u4u/processUpdateMember?memId="+memId+"&state="+memState;
		}
		return "redirect:/";
		
	}
	@RequestMapping(value="/_4u4u/processUpdateMember" ,method = RequestMethod.GET)
	public String processUpdateMember(Model model,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "redirect:/index";
		}
		String strMemId = request.getParameter("memId").trim();
		Integer memId = null;
		if(strMemId != null) {
			memId = Integer.parseInt(strMemId);
		}
		model.addAttribute("memId", memId);
		MemberBean mb = memberService.queryMemberById(memId);
		if(mb != null) {
			String email = mb.getEmail();
			model.addAttribute("email", email);
			String name = mb.getName();
			model.addAttribute("name", name);
			String password = mb.getPassword();
			model.addAttribute("password", password);
			model.addAttribute("password2", password);
			String gender =  mb.getGender();
			model.addAttribute("gender", gender);
			String imageFileName = mb.getPicName();
			model.addAttribute("imageFileName", imageFileName);
		}
		return "/_4u4u_Account/updateMember";
	}
	






}


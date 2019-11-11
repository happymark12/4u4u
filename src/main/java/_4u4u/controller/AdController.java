package _4u4u.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialClob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import _4u4u.model.FinalJsonObject;
import _4u4u.model.MemberBean;
import _4u4u.model.RoomBean;
import _4u4u.model.RoomRentBean;
import _4u4u.model.WantedRoomBean;
import _4u4u.service.EmailService;
import _4u4u.service.MemberService;
import _4u4u.service.RoomRentService;
import _4u4u.service.RoomService;
import _4u4u.service.WantedRoomService;
import _4u4u_init.ude.CantCreateAdException;
import _4u4u_init.util.ConvertTableUtil;
import _4u4u_init.util.GlobalService;

@Controller
public class AdController {

	WantedRoomService wantedRoomService;
	RoomRentService roomRentService;
	MemberService memberService;
	EmailService emailService;
	RoomService roomService;

//  修改廣告功能會用到下面兩個	
	WantedRoomBean wantedRoomBean;
	RoomRentBean roomRentBean;
//	MyAdServlet需要用到頁數	
	int pageNo = 1;

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

	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	@Autowired
	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	@RequestMapping(value = { "/_4u4u/findRoomDetail", "/_4u4u/roomRentDetail" }, method = RequestMethod.GET)
	public String getAdDetailPage(Model model, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String adStyle = request.getParameter("adStyle").trim();
		Integer adId = Integer.parseInt(request.getParameter("adId").trim());
		WantedRoomBean wrb = null;
		RoomRentBean rrb = null;

		if (adStyle.equals("0")) {
//			RoomRentService roomRentService = new RoomRentServiceImpl();
			rrb = roomRentService.getAdById(adId);
		} else {
//			WantedRoomService wantedRoomService = new WantedRoomServiceImpl();
			wrb = wantedRoomService.getAdById(adId);
		}

		if (wrb != null) {
			model.addAttribute("findRoomAd", wrb); // findroomAd

			String[] imageFileNames = null;
			if (wrb.getImages() != null && wrb.getImages().trim().length() != 0) {
				imageFileNames = wrb.getImages().trim().split(",");
			}
			if (imageFileNames != null) {
				model.addAttribute("images", imageFileNames);

			}
			String job = ConvertTableUtil.jobCodeToString(wrb.getJob());
			model.addAttribute("job", job);
			if (wrb.getSuiteQuantity() == 0 && wrb.getRoomQuantity() != 0) {
				model.addAttribute("wantedRoom", "想要" + wrb.getRoomQuantity() + "雅房");
			} else if (wrb.getRoomQuantity() == 0 && wrb.getSuiteQuantity() != 0) {
				model.addAttribute("wantedRoom", "想要" + wrb.getSuiteQuantity() + "套房");
			} else {
				model.addAttribute("wantedRoom", "想要" + wrb.getSuiteQuantity() + "套房" + wrb.getRoomQuantity() + "雅房");

			}
			// potential whole-properties target
			if (wrb.getAgreeShare()) {
				// potentialMap 1st String : roomAdId ; 2nd String : imageFileName ; 3rd String:
				// adTitle
				List<List<String>> potentialPropertiesList = new ArrayList<List<String>>();
				potentialPropertiesList = memberService
						.getPotentialWholePropertiesList(wrb.getWantedRoomAdMemId().getMemId());
				if (potentialPropertiesList.size() != 0) {
					model.addAttribute("potentialList", potentialPropertiesList);
				}

			}
			// adDetailContent
			Clob clob = wrb.getAdDescription();
			if (clob != null) {
				try (Reader r = wrb.getAdDescription().getCharacterStream();) {
					StringBuffer buffer = new StringBuffer();
					int ch, count = 0;
					while ((ch = r.read()) != -1) {
						buffer.append("" + (char) ch);
						count++;
					}
					model.addAttribute("adDescription", buffer.toString());
					if (count > 45) {
						model.addAttribute("needReadMore", "yes");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// checkInDate
			if (wrb.getCheckInDate() != null) {
				Date date = new Date(wrb.getCheckInDate().getTime());
				model.addAttribute("checkInDate", date);
			}
			// looking in
			if (wrb.getAreaCode() != null && wrb.getAreaCode().length() != 0) {
				String[] strArray = wrb.getAreaCode().trim().split(",");
				String lookInArea = "";
				for (String str : strArray) {
					lookInArea += ConvertTableUtil.addressCodeToString(str) + ",";
				}
				lookInArea = lookInArea.substring(0, lookInArea.length() - 1);
				model.addAttribute("lookInArea", lookInArea);
			}
			// 合租意願
			if (wrb.getAgreeShare() != null && wrb.getAgreeShare()) {
				model.addAttribute("buddyups", "願意合租");
			}
			// age
			if (wrb.getAge() != null && wrb.getAge().trim().length() != 0) {
				String[] ageArray = wrb.getAge().trim().split(",");
				if (ageArray.length == 2) {
					if (ageArray[0].contentEquals("null") || ageArray[1].contentEquals("null")) {
						;
					} else {
						String age = wrb.getAge().trim().replace(",", " to ");
						model.addAttribute("age", age);
					}
				} else {
					model.addAttribute("age", wrb.getAge().trim());
				}

			}
			// sexOrientation
			if (wrb.getAgreeAdCondition()) {
				String sexOrient = ConvertTableUtil.sexOrientCodeToString(wrb.getSexualOrientation());
				model.addAttribute("sexOrient", sexOrient);
			}
			// 室友偏好
			String Fjob = ConvertTableUtil.jobCodeToString(wrb.getWantedRoommatesJob());
			model.addAttribute("Fjob", Fjob);

			// flatmateAge
			if (wrb.getWantedRoommatesAge() != null && wrb.getWantedRoommatesAge().trim().length() != 0) {
				String roomMateAge = null;
				if (wrb.getWantedRoommatesAge().contains(",")) {
					String[] ageArray = wrb.getWantedRoommatesAge().trim().split(",");
					if (ageArray[0].contentEquals("null") || ageArray[1].contentEquals("null")) {
						;
					} else {
						roomMateAge = wrb.getWantedRoommatesAge().trim().replace(",", " to ");
						model.addAttribute("roomMateAge", roomMateAge);
					}
				} else {
					roomMateAge = wrb.getWantedRoommatesAge().trim();
					model.addAttribute("roomMateAge", roomMateAge);
				}
			}
			// flatmatesexOrientation
			String flatmateSexOrient = ConvertTableUtil.sexOrientCodeToString(wrb.getWantedRoommatesSex());
			model.addAttribute("flatmateSexOrient", flatmateSexOrient);

			// flatmateGender
			String gender = ConvertTableUtil.genderCodeToString(wrb.getWantedRoommatesGender());
			model.addAttribute("flatmateGender", gender);
		}

		if (rrb != null) {
			model.addAttribute("roomRentAd", rrb); // roomRentAd
			String[] imageFileNames = null;
			if (rrb.getAdImages() != null && rrb.getAdImages().trim().length() != 0) {
				imageFileNames = rrb.getAdImages().trim().split(",");
			}
			model.addAttribute("images", imageFileNames);

			// areaCode
			if (rrb.getAdAreacode() != null && rrb.getAdAreacode().trim().length() != 0) {

				String area = ConvertTableUtil.addressCodeToString(rrb.getAdAreacode());
				model.addAttribute("area", area);

				String detailAddr = rrb.getAdDetailaddress();
				String strStart = detailAddr.substring(0, 6);
				String strEnd = detailAddr.substring(6);
				String strFinal = null;
				if (strEnd.contains("橋")) {
					strFinal = strStart + strEnd.substring(0, strEnd.indexOf("橋") + 1);
				} else if (strEnd.contains("街")) {
					strFinal = strStart + strEnd.substring(0, strEnd.indexOf("街") + 1);

				} else if (strEnd.contains("路")) {
					strFinal = strStart + strEnd.substring(0, strEnd.indexOf("路") + 1);

				} else if (strEnd.contains("里")) {
					strFinal = strStart + strEnd.substring(0, strEnd.indexOf("里") + 1);
				} else {
					strFinal = strStart;
				}
//				 String query = String.format("%s",URLEncoder.encode(detailAddr,"UTF-8"));
//				 System.out.println(detailAddr);
				model.addAttribute("detailAddr", strFinal);
			}
			// adDetail

			Clob clob = rrb.getAdDetailContent();
			if (clob != null) {
				try (Reader r = rrb.getAdDetailContent().getCharacterStream();) {
					StringBuffer buffer = new StringBuffer();
					int ch, count = 0;

					while ((ch = r.read()) != -1) {
						buffer.append("" + (char) ch);
						count++;
					}

					model.addAttribute("adDescription", buffer.toString());
					if (count > 45) {
						model.addAttribute("needReadMore", "yes");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// potential buddy ups
			if (rrb.getAdRentType().trim().contentEquals("0")) {
				// potentialMap 1st Integer : MemberId ; 2nd Integer : findroomAdId
				List<List<String>> potentialList = new ArrayList<List<String>>();
				potentialList = memberService.getPotentialBuddyUps(adId);
				if (potentialList.size() != 0) {
					model.addAttribute("potentialList", potentialList);
				}

			}

			// checkInDate
			if (rrb.getAdAvailableDate() != null) {
				Date date = new Date(rrb.getAdAvailableDate().getTime());
				model.addAttribute("checkInDate", date);
			}
			// identity
			String adOwner = ConvertTableUtil.identityCodeToString(rrb.getAdIdentity().trim());
			model.addAttribute("adOwner", adOwner);
			// AgentFee
			if (rrb.getAdIdentity().trim().equals("3")) {
				if (rrb.getAdAgentFee() != null && !rrb.getAdAgentFee().trim().equals("0")) {
					model.addAttribute("agentFee", rrb.getAdAgentFee());
				}
			}
			// sexOrientation
			if (rrb.getAdCurrentPeopleNum() != null && !rrb.getAdCurrentPeopleNum().contentEquals("0")) {
				if (rrb.getAdCurAllowedSearchbySexOrient() != null && rrb.getAdCurAllowedSearchbySexOrient()) {
					if (rrb.getAdCurSexOrientation() != null) {
						String sexOrient = ConvertTableUtil.sexOrientCodeToString(rrb.getAdCurSexOrientation());
						model.addAttribute("sexOrient", sexOrient);
					}

				}
			}
			// age
			if (rrb.getAdCurAge() != null && rrb.getAdCurAge().length() != 0) {
				if (rrb.getAdCurAge().contains(",")) {
					String[] ageArray = rrb.getAdCurAge().trim().split(",");
					if (ageArray[0].contentEquals("null") || ageArray[1].contentEquals("null")) {
						;
					} else {
						String age = rrb.getAdCurAge().trim().replace(",", " to ");
						model.addAttribute("age", age);
					}
				} else {
					model.addAttribute("age", rrb.getAdCurAge());
				}
			}
			// 室友偏好
			String Fjob = ConvertTableUtil.jobCodeToString(rrb.getAdFutureJobType());
			model.addAttribute("Fjob", Fjob);
			// flatmateGender
			String gender = ConvertTableUtil.genderCodeToString(rrb.getAdFutureGender());
			model.addAttribute("flatmateGender", gender);

			// flatmateAge
			if (rrb.getAdFutureAge() != null && rrb.getAdFutureAge().trim().length() != 0) {
				String roomMateAge = null;
				if (rrb.getAdFutureAge().contains(",")) {
					String[] ageArray = rrb.getAdFutureAge().trim().split(",");
					if (ageArray[0].contentEquals("null") || ageArray[1].contentEquals("null")) {
						;
					} else {
						roomMateAge = rrb.getAdFutureAge().trim().replace(",", " to ");
						model.addAttribute("roomMateAge", roomMateAge);
					}
				} else {
					roomMateAge = rrb.getAdFutureAge().trim();
					model.addAttribute("roomMateAge", roomMateAge);
				}

			}
		}

//		RequestDispatcher rd = request.getRequestDispatcher("/_4u4u_Search/AdDetail.jsp");
//		rd.forward(request, response);
		return "_4u4u_Search/AdDetail";
	}

	@RequestMapping(value = "/_4u4u/controller/ProcessRoomRentAd.do", method = RequestMethod.POST)
	public String processRoomRentAdForm(Model model, HttpServletRequest request) throws IOException, ServletException {
		HttpSession session = request.getSession();

		// 定義存放錯誤訊息的 Collection物件
		Map<String, String> errorMessage = new HashMap<String, String>();
		Map<String, String> msgOK = new HashMap<String, String>();
		model.addAttribute("ErrorMsg", errorMessage);
		session.setAttribute("MsgOK", msgOK); // 顯示正常訊息

		Integer adId = null;
		String adRentType = null;
		Integer adRoomNum = null;
		String adToiletNum = null;
		String adBalconyNum = null;
		String adLivingRoomNum = null;
		String adIdentity = null;
		String adAgentFee = null;
		String adCurrentPeopleNum = null;
		String adHouseType = null;
		String adAreacode = null;
		String adDetailaddress = null;
		String adParkingCount = null;
		String adExtraCost = null;
		Boolean adHasElevator = null;
		String adMinimumStayLength = null;
		Date adAvailableDate = null;
		String adTotalArea = null;
		Integer adRentPrice = null;
		String adDeposit = null;
		String adNearByTransport = "";
		String adTitle = null;
		Clob adDetailContent = null;
		String adPhone = null;
		Boolean adPhoneAttachToAd = null;
		String adImages = "";
		Boolean adCook = null; // 針對整層出租
		Boolean adCurHasPet = null;
		Boolean adCurSmoke = null;
		String adCurSexOrientation = null;
		Boolean adCurAllowedSearchbySexOrient = null;
		String adCurGender = null;
		String adCurAge = null;
		Boolean adFutureSmoke = null;
		String adFutureJobType = null;
		Boolean adFuturePet = null;
		String adFutureGender = null;
		String adFutureAge = null;
		Boolean adFutureCoupleAccept = null;
		Boolean adState = true;
		Integer emailMax = null;
		Integer sendMail = 0;
		Date emailDate = new Date(System.currentTimeMillis()); // 要寄信日期
		Timestamp adCreateDate = new Timestamp(System.currentTimeMillis()); // 要寄信日期
		Timestamp adUpdateDate = adCreateDate; // 要寄信日期
		Boolean adStyle = false;
		Set<RoomBean> roomItems = null;
		String county = null;
		String district = null;

		Collection<Part> parts = request.getParts();
		GlobalService.exploreParts(parts, request);

		if (parts != null) { // 如果這是一個上傳資料的表單
			for (Part p : parts) {
				String fldName = p.getName();

				String value = null;

				value = request.getParameter(fldName);

				// 1. 讀取使用者輸入資料
				if (p.getContentType() == null) {
					if (fldName.equalsIgnoreCase("rentType")) {
						if (value.equalsIgnoreCase("room-for-rent")) {
							adRentType = "3";
						} else if (value.equalsIgnoreCase("whole-property")) {
							adRentType = "0";
						} else if (value.equalsIgnoreCase("independent-studio")) {
							adRentType = "1";
						} else {
							adRentType = "2";
						}
					} else if (fldName.equalsIgnoreCase("houseCount")) {
						if (value.equalsIgnoreCase("none")) {
							errorMessage.put("houseNumber", "房間數量欄位必須要填");
							adRoomNum = 0;
						} else {
							value = value.substring(value.length() - 1);
							adRoomNum = Integer.parseInt(value.trim());
						}
					} else if (fldName.equalsIgnoreCase("wholePropertyToilet")) {
						if (value != null && value.trim().length() != 0) {
							adToiletNum = value;
						} else {
							adToiletNum = "0";

						}
					} else if (fldName.equalsIgnoreCase("wholePropertyLivingRoom")) {
						if (value != null && value.trim().length() != 0) {
							adLivingRoomNum = value;
						} else {
							adLivingRoomNum = "0";

						}
					} else if (fldName.equalsIgnoreCase("wholePropertyBalcony")) {
						if (value != null && value.trim().length() != 0) {
							adBalconyNum = value;
						} else {
							adBalconyNum = "0";

						}
					} else if (fldName.equalsIgnoreCase("adOwner")) {
						if (value.trim().equals("default")) {
							errorMessage.put("adIdentity", "廣告者身分需要填寫");
						} else {
							if (value.equalsIgnoreCase("live-out-landlord")) {
								adIdentity = "0";
							} else if (value.equalsIgnoreCase("live-in-landlord")) {
								adIdentity = "1";
							} else if (value.equalsIgnoreCase("currentTenant")) {
								adIdentity = "2";
							} else if (value.equalsIgnoreCase("agent")) {
								adIdentity = "3";
							} else {
								adIdentity = "4";
							}

						}
					} else if (fldName.equalsIgnoreCase("currentNum")) {
						adCurrentPeopleNum = value.trim();
					} else if (fldName.equalsIgnoreCase("propertyType")) {
						if (!value.trim().equals("default")) {
							adHouseType = value.trim();
						}
					} else if (fldName.equalsIgnoreCase("county")) {
						if (value == null || value.trim().length() == 0) {
							errorMessage.put("address", "地址欄位必填");
						} else {
							county = value.trim();
						}
					} else if (fldName.equalsIgnoreCase("district")) {
						if (value == null || value.trim().length() == 0) {
							errorMessage.put("address", "地址欄位必填");
						} else {
							district = value.trim();
						}

					} else if (fldName.equalsIgnoreCase("address")) {
						if (value == null || value.trim().length() == 0) {
							errorMessage.put("address", "地址欄位必填");
						} else {
							adDetailaddress = county + district + value.trim();
						}

					} else if (fldName.equalsIgnoreCase("parkingNum")) {
						adParkingCount = value.trim();
					} else if (fldName.equalsIgnoreCase("elevator")) {
						adHasElevator = Boolean.valueOf(value.trim());
					} else if (fldName.equalsIgnoreCase("extraCost")) {
						if (value.trim().length() != 0) {
							adExtraCost = value.trim();
						}
					} else if (fldName.equalsIgnoreCase("wholePropertyArea") && adRentType.equals("0")) {
						if (value != null && value.trim().length() != 0) {
							adTotalArea = value.trim();
						} else {
//							errorMessage.put("TotalArea", "總坪數欄位必填");
						}
					} else if (fldName.equalsIgnoreCase("wholePropertyTotalRent") && adRentType.equals("0")) {
						if (value == null || value.trim().length() == 0) {
							errorMessage.put("TotalRent", "總租金欄位必填");
						} else {
							adRentPrice = Integer.parseInt(value.trim());
						}
					} else if (fldName.equalsIgnoreCase("wholePropertyDeposit") && adRentType.equals("0")) {
						adDeposit = value.trim();
					} else if (fldName.equalsIgnoreCase("minimumStay")) {
						adMinimumStayLength = value.trim();
					} else if (fldName.equalsIgnoreCase("availableDate")) {
						SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
						// 日期的格式化 將得到的字串轉成java.util.Date;
						if (value != null && value.trim().length() != 0) {

							try {
								adAvailableDate = new Date(formatDate.parse(value.trim()).getTime());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (fldName.equalsIgnoreCase("bus")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "公車站,";
						}
					} else if (fldName.equalsIgnoreCase("bus3")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "公車站,";
						}
					} else if (fldName.equalsIgnoreCase("bus4")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "公車站,";
						}
					} else if (fldName.equalsIgnoreCase("MRT")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "捷運站,";
						}
					} else if (fldName.equalsIgnoreCase("MRT3")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "捷運站,";
						}
					} else if (fldName.equalsIgnoreCase("MRT4")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "捷運站,";
						}
					} else if (fldName.equalsIgnoreCase("train")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "火車站,";
						}
					} else if (fldName.equalsIgnoreCase("train3")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "火車站,";
						}
					} else if (fldName.equalsIgnoreCase("train4")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "火車站,";
						}
					} else if (fldName.equalsIgnoreCase("adTitle")) {
						if (value.trim().length() != 0) {
							adTitle = value.trim();
						}
					} else if (fldName.equalsIgnoreCase("adDescription")) {
						if (value.trim().length() != 0) {
							try {
								adDetailContent = new SerialClob(value.trim().toCharArray());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (fldName.equalsIgnoreCase("phone")) {
						if (value != null && value.trim().length() != 0) {
							adPhone = value.trim();
						}
					} else if (fldName.equalsIgnoreCase("allowSexOrentSearch")) {
						adCurAllowedSearchbySexOrient = true;
					} else if (fldName.equalsIgnoreCase("phoneAllowAttachAd")) {
						adPhoneAttachToAd = true;
					} else if (fldName.equalsIgnoreCase("emailMax")) {
						emailMax = Integer.parseInt(value.trim());
					} else if (fldName.equalsIgnoreCase("Cpet") && adRentType.equals("3")
							&& !adCurrentPeopleNum.equals("0")) {
						adCurHasPet = Boolean.parseBoolean(value.trim());
					} else if (fldName.equalsIgnoreCase("Csmoke") && adRentType.equals("3")
							&& !adCurrentPeopleNum.equals("0")) {
						adCurSmoke = Boolean.parseBoolean(value.trim());
					} else if (fldName.equalsIgnoreCase("CsexOrient") && adRentType.equals("3")
							&& !adCurrentPeopleNum.equals("0")) {
						adCurSexOrientation = value.trim();
					} else if (fldName.equalsIgnoreCase("Cgender") && adRentType.equals("3")
							&& !adCurrentPeopleNum.equals("0")) {
						adCurGender = value.trim();
					} else if (fldName.equalsIgnoreCase("allowSexOrentSearch") && adRentType.equals("3")
							&& !adCurrentPeopleNum.equals("0")) {
						adCurAllowedSearchbySexOrient = Boolean.parseBoolean(value.trim());
					} else if (fldName.equalsIgnoreCase("Cage") && adRentType.equals("3")
							&& !adCurrentPeopleNum.equals("0")) {
						adCurAge = value.trim().equals("-") ? null : value.trim();
					} else if (fldName.equalsIgnoreCase("Cage2") && adRentType.equals("3")
							&& !adCurrentPeopleNum.equals("0")) {
						adCurAge = value.trim().equals("-") ? adCurAge + "," + null : adCurAge + "," + value.trim();
					} else if (fldName.equalsIgnoreCase("Fsmoke")) {
						adFutureSmoke = Boolean.parseBoolean(value.trim());
					} else if (fldName.equalsIgnoreCase("Fjob")) {
						adFutureJobType = value.trim();
					} else if (fldName.equalsIgnoreCase("Fpet")) {
						adFuturePet = Boolean.parseBoolean(value.trim());
					} else if (fldName.equalsIgnoreCase("Fgender")) {
						adFutureGender = value.trim();
					} else if (fldName.equalsIgnoreCase("Fminage") && adRentType.equals("3")) {
						adFutureAge = value.trim().equals("default") ? null : value.trim();
					} else if (fldName.equalsIgnoreCase("Fmaxage") && adRentType.equals("3")) {
						adFutureAge = value.trim().equals("default") ? adFutureAge + "," + null
								: adFutureAge + "," + value.trim();
					} else if (fldName.equalsIgnoreCase("couple") && adRentType.equals("3")) {
						adFutureCoupleAccept = Boolean.parseBoolean(value.trim());
					}

				}
			} // for loop end

			adBalconyNum = adBalconyNum == null ? "0" : adBalconyNum;
			adLivingRoomNum = adLivingRoomNum == null ? "0" : adLivingRoomNum;
			adToiletNum = adToiletNum == null ? "0" : adToiletNum;
			adAgentFee = adAgentFee == null ? "0" : adAgentFee;
			adPhoneAttachToAd = adPhoneAttachToAd == null ? false : adPhoneAttachToAd;

			// 0:整層出租 3:出租房間(有公共空間) 1:獨立套房 2:分租套房
			if (adRentType.equals("0")) {
				adCurAllowedSearchbySexOrient = adCurAllowedSearchbySexOrient == null ? false
						: adCurAllowedSearchbySexOrient;
				adCook = true;
			}

			if (!adRentType.equals("3") || adCurrentPeopleNum.equals("0")) {
				adFutureAge = null;
				adFutureCoupleAccept = null;
				adCurHasPet = null;
				adCurSmoke = null;
				adCurSexOrientation = null;
				adCurGender = null;
				adCurAge = null;
				adCurAllowedSearchbySexOrient = null;
			} else if (adRentType.equals("3") && !adCurrentPeopleNum.equals("0")) {
				adCurAllowedSearchbySexOrient = adCurAllowedSearchbySexOrient == null ? false
						: adCurAllowedSearchbySexOrient;
			}

			adAreacode = ConvertTableUtil.addressStringToCode(county + district);
			if (adNearByTransport.trim().length() != 0) {
				adNearByTransport = adNearByTransport.substring(0, adNearByTransport.length() - 1);
			} else {
				adNearByTransport = null;
			}

		} // if bracket end

		if (!errorMessage.isEmpty()) {
			return "_4u4u_PostAd/PostRoomRentAd";
		}

		if (parts != null) { // 如果這是一個上傳資料的表單
			if (!new File("d:\\images\\roomRentAd").exists()) {
				new File("d:\\images\\roomRentAd").mkdirs();
			}

			for (Part p : parts) {
				if (p.getContentType() != null) {
					String fileName = GlobalService.getFileName(p);
					long sizeInBytes = 0;
					InputStream is = null;
					if (fileName != null && fileName.trim().length() > 0) {
						fileName = System.currentTimeMillis()
								+ GlobalService.getFileName(p).substring(GlobalService.getFileName(p).indexOf("."));
						adImages += fileName + ",";
						sizeInBytes = p.getSize();
						is = p.getInputStream();
						int len = 0;
						byte[] byteArray = new byte[(int) sizeInBytes];
						File uploadFile = new File("d:\\images\\roomRentAd", fileName);
						try (FileOutputStream fos = new FileOutputStream(uploadFile);) {
							while ((len = is.read(byteArray)) != -1) {
								fos.write(byteArray, 0, len);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						adImages = null;
					}
				}
			}
			if (adImages != null && adImages.trim().length() != 0) {
				adImages = adImages.substring(0, adImages.length() - 1);
			}
		}
		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");

		RoomRentBean roomRentAd = new RoomRentBean(adId, adRentType, adRoomNum, adToiletNum, adBalconyNum,
				adLivingRoomNum, adIdentity, adAgentFee, adCurrentPeopleNum, adHouseType, adAreacode, adDetailaddress,
				adParkingCount, adExtraCost, adHasElevator, adMinimumStayLength, adAvailableDate, adTotalArea,
				adRentPrice, adDeposit, adNearByTransport, adTitle, adDetailContent, adPhone, adPhoneAttachToAd,
				adImages, adCook, adCurHasPet, adCurSmoke, adCurSexOrientation, adCurAllowedSearchbySexOrient,
				adCurGender, adCurAge, adFutureSmoke, adFutureJobType, adFuturePet, adFutureGender, adFutureAge,
				adFutureCoupleAccept, adUpdateDate, adCreateDate, adState, emailMax, sendMail, emailDate, adStyle, mb);

		// 處理房間
		if (parts != null && adRoomNum != 0) { // 如果這是一個上傳資料的表單
			roomItems = new LinkedHashSet<>();

			for (int i = 1; i <= adRoomNum; i++) {
				RoomBean rb = new RoomBean();
				Boolean balcony = null;
				Boolean duplexApartment = null;
				Boolean wash = null;
				Boolean icebox = null;
				Boolean four = null;
				Boolean gas = null;
				Boolean tv = null;
				Boolean wardrobe = null;
				Boolean sofa = null;
				Boolean heater = null;
				Boolean broadband = null;
				Boolean desk = null;
				Boolean chair = null;
				Boolean singlebed = null;
				Boolean doublebed = null;
				Boolean coldair = null;
				for (Part p : parts) {
					String fldName = p.getName();

					String value = null;

					value = request.getParameter(fldName);

					// 1. 讀取使用者輸入資料
					if (p.getContentType() == null) {
						if (fldName.equalsIgnoreCase("roomType" + i)) {
							rb.setRoomType(value.trim());
						} else if (fldName.equalsIgnoreCase("floor" + i)) {
							if (value != null && value.trim().length() != 0) {
								rb.setRentFloor(value.trim());
							} else {
								rb.setRentFloor(null);
							}
						} else if (fldName.equalsIgnoreCase("totalFloor" + i)) {
							if (value != null && value.trim().length() != 0) {
								rb.setRentTotalFloor(value.trim());
							} else {
								rb.setRentTotalFloor(null);
							}
						} else if (fldName.equalsIgnoreCase("area" + i)) {
							if (value != null && value.trim().length() != 0) {
								rb.setArea(value.trim());
							} else {
								rb.setArea(null);
							}
						} else if (fldName.equalsIgnoreCase("roomRentPrice" + i)) {
							if (value != null && value.trim().length() != 0) {
								rb.setRentPrice(Integer.parseInt(value.trim()));
							} else {
								errorMessage.put("rentPrice", "租金欄位不能空白");
							}
						} else if (fldName.equalsIgnoreCase("roomDeposit" + i)) {

							rb.setDeposit(value.trim());

						} else if (fldName.equalsIgnoreCase("canCook" + i)) {

							rb.setAllowCook(Boolean.parseBoolean(value.trim()));
						} else if (fldName.equalsIgnoreCase("balcony" + i)) {
							balcony = Boolean.parseBoolean(value.trim());
							rb.setHasBalcony(balcony);
						} else if (fldName.equalsIgnoreCase("duplexApartment" + i)) {
							duplexApartment = Boolean.parseBoolean(value.trim());
							rb.setHasDuplex(duplexApartment);
						} else if (fldName.equalsIgnoreCase("wash" + i)) {
							wash = Boolean.parseBoolean(value.trim());
							rb.setHasWash(wash);
						} else if (fldName.equalsIgnoreCase("icebox" + i)) {
							icebox = Boolean.parseBoolean(value.trim());
							rb.setHasIceBox(icebox);
						} else if (fldName.equalsIgnoreCase("four" + i)) {
							four = Boolean.parseBoolean(value.trim());
							rb.setHas4(four);
						} else if (fldName.equalsIgnoreCase("gas" + i)) {
							gas = Boolean.parseBoolean(value.trim());
							rb.setHasGas(gas);
						} else if (fldName.equalsIgnoreCase("tv" + i)) {
							tv = Boolean.parseBoolean(value.trim());
							rb.setHasTV(tv);
						} else if (fldName.equalsIgnoreCase("wardrobe" + i)) {
							wardrobe = Boolean.parseBoolean(value.trim());
							rb.setHasWardrobe(wardrobe);
						} else if (fldName.equalsIgnoreCase("sofa" + i)) {
							sofa = Boolean.parseBoolean(value.trim());
							rb.setHasSofa(sofa);
						} else if (fldName.equalsIgnoreCase("heater" + i)) {
							heater = Boolean.parseBoolean(value.trim());
							rb.setHasHeater(heater);
						} else if (fldName.equalsIgnoreCase("broadband" + i)) {
							broadband = Boolean.parseBoolean(value.trim());
							rb.setHasBroadBand(broadband);
						} else if (fldName.equalsIgnoreCase("desk" + i)) {
							desk = Boolean.parseBoolean(value.trim());
							rb.setHasDesk(desk);
						} else if (fldName.equalsIgnoreCase("chair" + i)) {
							chair = Boolean.parseBoolean(value.trim());
							rb.setHasChair(chair);
						} else if (fldName.equalsIgnoreCase("singlebed" + i)) {
							singlebed = Boolean.parseBoolean(value.trim());
							rb.setHasSingleBed(singlebed);
						} else if (fldName.equalsIgnoreCase("doublebed" + i)) {
							doublebed = Boolean.parseBoolean(value.trim());
							rb.setHasDoubleBed(doublebed);
						} else if (fldName.equalsIgnoreCase("coldair" + i)) {
							coldair = Boolean.parseBoolean(value.trim());
							rb.setHasColdAir(coldair);
						}

					} // if bracket end

				} // for loop end
				if (balcony == null) {
					rb.setHasBalcony(false);
				}
				if (duplexApartment == null) {
					rb.setHasDuplex(false);
				}
				if (wash == null) {
					rb.setHasWash(false);
				}
				if (icebox == null) {
					rb.setHasIceBox(false);
				}
				if (four == null) {
					rb.setHas4(false);
				}
				if (gas == null) {
					rb.setHasGas(false);
				}
				if (tv == null) {
					rb.setHasTV(false);
				}
				if (wardrobe == null) {
					rb.setHasWardrobe(false);
				}
				if (sofa == null) {
					rb.setHasSofa(false);
				}
				if (heater == null) {
					rb.setHasHeater(false);
				}
				if (broadband == null) {
					rb.setHasBroadBand(false);
				}
				if (desk == null) {
					rb.setHasDesk(false);
				}
				if (chair == null) {
					rb.setHasChair(false);
				}
				if (singlebed == null) {
					rb.setHasSingleBed(false);
				}
				if (doublebed == null) {
					rb.setHasDoubleBed(false);
				}
				if (coldair == null) {
					rb.setHasColdAir(false);
				}
				rb.setRoomState(true);
				rb.setRoomAd(roomRentAd);
				roomItems.add(rb);
			} // for loop end
		} // if loop end
		if (!errorMessage.isEmpty()) {
			return "_4u4u_PostAd/PostRoomRentAd";
		}

		// 設定1方有個多
		roomRentAd.setRoomItems(roomItems);

		try {
//			RoomRentService service = new RoomRentServiceImpl();
//			MemberService memberService = new MemberServiceImpl();

			///////////// 判斷會員狀態可否發文 ///////////////////
			String memberState = memberService.memberState(mb);
			boolean isExistEffectiveAd = roomRentService.isExistEffectiveAd(mb);

			if (!memberState.equals("1") && !memberState.equals("2")) {
				throw new CantCreateAdException("不是一般會員也不是VIP會員");
			} else if (isExistEffectiveAd && memberState.equals("1")) {
				throw new CantCreateAdException("已達到發佈廣告上限，請升級成VIP享有發佈多則廣告的功能");
			}
			////////////////////////////////////////////////////////
			int n = roomRentService.saveRoomRentAd(roomRentAd);
			emailService.sendMatchEmail(roomRentAd); // 寄出符合信

			////////////////// 印出符合的信件 ////////////////////
			List<WantedRoomBean> list = roomRentService.findingMatchWantedRoomAd(roomRentAd);
			for (WantedRoomBean wantedRoomBean : list) {
				System.err.println("找到以下符合廣告的信件");
				System.err.println(
						"廣告編號為： " + wantedRoomBean.getFindRoomId() + "   廣告標題為： " + wantedRoomBean.getAdTitle());
			}
			////////////////////////////////////////////////////////

			if (n == 1) {
				msgOK.put("InsertAdOK", "<Font color='red'>發佈成功</Font>");
//				response.sendRedirect(response.encodeRedirectURL("/4u4u/"));
				return "redirect:/";
			} else {
				errorMessage.put("errorInsert", "新增此筆資料有誤(ProcessRoomRentAd)");

			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage.put("exception", e.getMessage());
			// 產生 RequestDispatcher 物件 rd
			return "_4u4u_PostAd/PostRoomRentAd";
		}
		if (!errorMessage.isEmpty()) {
			return "_4u4u_PostAd/PostRoomRentAd";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/_4u4u/controller/ProcessWantedRoomAd.do", method = RequestMethod.POST)
	public String processWantedRoomAdForm(Model model, HttpServletRequest request)
			throws IOException, ServletException {
		HttpSession session = request.getSession();

		// 定義存放錯誤訊息的 Collection物件
		Map<String, String> errorMessage = new HashMap<String, String>();
		Map<String, String> msgOK = new HashMap<String, String>();
		model.addAttribute("ErrorMsg", errorMessage);
		session.setAttribute("MsgOK", msgOK); // 顯示正常訊息
		// 設定輸入資料的編碼
		// 讀取使用者所輸入，由瀏覽器送來的peopleNum_gender欄位內的資料，注意大小寫
		String peopleNum_gender = null; // 廣告者性別及人數
		Integer suiteNum = null;
		String agreeShareStr = null;
		String countyArea = null;
		Integer nonSuiteNum = null;
		Boolean agreeShare = null; // 合租意願
		String areaCode = null; // 地區(二級搜索) e.x:台南市歸仁區
		Integer rentBudget = null; // 租金預算
		Date checkInDate = null; // 可入住日期
		String liveTime = null; // 居住時間
		// 找房 想要的設施
		Boolean washingMachine = null; // 洗衣機
		Boolean refrigerator = null; // 冰箱
		Boolean tv = null; // 電視
		Boolean airConditioning = null; // 冷氣
		Boolean waterHeater = null; // 熱水器
		Boolean internet = null; // 網路
		Boolean fourthTV = null; // 第四台
		Boolean gas = null; // 天然瓦斯
		Boolean wardrobe = null; // 衣櫃
		Boolean sofa = null; // 沙發
		Boolean table = null; // 桌子
		Boolean chair = null; // 椅子
		Boolean parking = null; // 停車位
		Boolean balcony = null; // 陽台
		Boolean singleBed = null; // 單人床
		Boolean doubleBed = null; // 雙人床
		Boolean cook = null; // 開火
		String age = null; // 年齡
		String job = null; // 職業
		Boolean smoke = null; // 抽菸
		Boolean pet = null; // 養寵
		String sexualOrientation = null; // 性向
		Boolean agreeAdSearchCondition = null; // 同意(性向)成為廣告搜尋條件
		// 找房 想要的室友
		String wantedRoommatesGender = null; // 室友性別
		String wantedRoommatesAge = null; // 室友年齡
		String wantedRoommatesJob = null; // 室友職業
		Boolean roommatesSmoke = null; // 室友有無抽菸
		Boolean roommatesPet = null; // 室友有無養寵
		String wantedRoommatesSex = null; // 室友性向
		String hasAirConditioning = null;
		String hasRefrigerator = null;
		// 找房 廣告詳情
		String adTitle = null; // 廣告標題
		Clob adDescription = null; // 廣告現況描述
		String phone = null; // 手機(電話)
		String images = ""; // 上傳的照片(存進資料庫是圖片網址)
		Integer emailMax = null; // 上限幾封信
		Boolean adState = true; // 廣告是否失效
		Integer findRoomId = null;
		Integer sendMail = 0; // 本日已寄出幾封email
		Date emailDate = new Date(System.currentTimeMillis()); // 要寄信日期
		Timestamp adCreateDate = new Timestamp(System.currentTimeMillis()); // 要寄信日期
		Timestamp adUpdateDate = adCreateDate; // 要寄信日期
		Boolean adStyle = true; // 廣告類型
		String areaStrToCode = null;
		String county = null;
		String cid = null;
		String severalRooms = null;
		String severalSuites = null;
		String hasWashingMachine = null;
		String userAge = null;
		String ageMin = null;
		String ageMax = null;
		String hasInternet = null;
		String hasWaterHeater = null;
		String hasFourthTV = null;
		String hasGas = null;
		String hasWardrobe = null;
		String hasSofa = null;
		String hasTable = null;
		String hasChair = null;
		String hasParking = null;
		String hasBalcony = null;
		String hasSingleBed = null;
		String hasDoubleBed = null;
		String roommatesAgeMin = null;
		String roommatesAgeMax = null;
		String wantedRoommatesSmoke = null;
		String wantedRoommatesPet = null;
		String description = null;
		String emailMaxStr = null;
		String hasTV = null;
		String allowSmoke = null;
		String allowCook = null;
		String allowPet = null;
		String agreeAdCondition = null;
		String[] districts = null;
		String budget = null;
		Collection<Part> parts = request.getParts();
		GlobalService.exploreParts(parts, request);

		if (parts != null) { // 如果這是一個上傳資料的表單
			for (Part p : parts) {
				String fldName = p.getName();
				String[] valueArray = null;
				String value = null;
				if (fldName.equalsIgnoreCase("districtList")) {
					valueArray = request.getParameterValues(fldName);
				} else {
					value = request.getParameter(fldName);
				}
				// 1. 讀取使用者輸入資料
				if (p.getContentType() == null) {
					if (fldName.equals("peopleNum_gender")) {
						peopleNum_gender = value;
					} else if (fldName.equals("severalSuites")) {
						severalSuites = value;
					} else if (fldName.equals("hasDoubleBed")) {
						hasDoubleBed = value;
					} else if (fldName.equalsIgnoreCase("hasSingleBed")) {
						hasSingleBed = value;
					} else if (fldName.equalsIgnoreCase("allowCook")) {
						allowCook = value;
					} else if (fldName.equalsIgnoreCase("severalRooms")) {
						severalRooms = value;
					} else if (fldName.equalsIgnoreCase("agreeShare")) {
						agreeShareStr = value;
					} else if (fldName.equalsIgnoreCase("districtList")) {
						districts = valueArray;
					} else if (fldName.equalsIgnoreCase("budget")) {
						budget = value;
					} else if (fldName.equalsIgnoreCase("county")) {
						county = value;
					} else if (fldName.equalsIgnoreCase("hasBalcony")) {
						hasBalcony = value;
					} else if (fldName.equalsIgnoreCase("checkInDate")) {
						cid = value;
					} else if (fldName.equalsIgnoreCase("liveTime")) {
						liveTime = value;
					} else if (fldName.equalsIgnoreCase("hasWashingMachine")) {
						hasWashingMachine = value;
					} else if (fldName.equalsIgnoreCase("hasRefrigerator")) {
						hasRefrigerator = value;
					} else if (fldName.equalsIgnoreCase("hasTV")) {
						hasTV = value;
					} else if (fldName.equalsIgnoreCase("hasParking")) {
						hasParking = value;
					} else if (fldName.equalsIgnoreCase("hasChair")) {
						hasChair = value;
					} else if (fldName.equalsIgnoreCase("hasTable")) {
						hasTable = value;
					} else if (fldName.equalsIgnoreCase("hasFourthTV")) {
						hasFourthTV = value;
					} else if (fldName.equalsIgnoreCase("hasAirConditioning")) {
						hasAirConditioning = value;
					} else if (fldName.equalsIgnoreCase("hasWaterHeater")) {
						hasWaterHeater = value;
					} else if (fldName.equalsIgnoreCase("hasInternet")) {
						hasInternet = value;
					} else if (fldName.equalsIgnoreCase("agreeAdCondition")) {
						agreeAdCondition = value;
					} else if (fldName.equalsIgnoreCase("wantedRoommatesGender")) {
						wantedRoommatesGender = value;
					} else if (fldName.equalsIgnoreCase("roommatesAgeMin")) {
						roommatesAgeMin = value;
					} else if (fldName.equalsIgnoreCase("roommatesAgeMax")) {
						roommatesAgeMax = value;
					} else if (fldName.equalsIgnoreCase("wantedRoommatesSmoke")) {
						wantedRoommatesSmoke = value;
					} else if (fldName.equalsIgnoreCase("wantedRoommatesPet")) {
						wantedRoommatesPet = value;
					} else if (fldName.equalsIgnoreCase("wantedRoommatesJob")) {
						wantedRoommatesJob = value;
					} else if (fldName.equalsIgnoreCase("adDescription")) {
						description = value;
					} else if (fldName.equalsIgnoreCase("wantedRoommatesSex")) {
						wantedRoommatesSex = value;
					} else if (fldName.equalsIgnoreCase("phone")) {
						phone = value;
					} else if (fldName.equalsIgnoreCase("emailMax")) {
						emailMaxStr = value;
					} else if (fldName.equalsIgnoreCase("sexualOrientation")) {
						sexualOrientation = value;
					} else if (fldName.equalsIgnoreCase("job")) {
						job = value;
					} else if (fldName.equalsIgnoreCase("adTitle")) {
						adTitle = value;
					} else if (fldName.equalsIgnoreCase("hasWardrobe")) {
						hasWardrobe = value;
					} else if (fldName.equalsIgnoreCase("hasSofa")) {
						hasSofa = value;
					} else if (fldName.equalsIgnoreCase("age")) {
						userAge = value;
					} else if (fldName.equalsIgnoreCase("ageMin")) {
						ageMin = value;
					} else if (fldName.equalsIgnoreCase("ageMax")) {
						ageMax = value;
					} else if (fldName.equalsIgnoreCase("allowSmoke")) {
						allowSmoke = value;
					} else if (fldName.equalsIgnoreCase("allowPet")) {
						allowPet = value;
					} else if (fldName.equalsIgnoreCase("hasGas")) {
						hasGas = value;
					}

				}
			}

			// 檢查使用者所輸入的資料
			if (peopleNum_gender.trim().equals("default")) {
				errorMessage.put("peopleGender", "性別人數欄位必填");
			}
			// 讀取使用者輸入,由瀏覽器送來的roomType欄位內的資料
			// 在jsp檔是由severalSuites和severalRooms組成
			// 檢查使用者所輸入的資料
			if (severalSuites.trim().length() == 0) {
				severalSuites = "0";
				suiteNum = Integer.parseInt(severalSuites);
			} else {
				suiteNum = Integer.parseInt(severalSuites);
			}

			// 檢查使用者所輸入的資料
			if (severalRooms.trim().length() == 0) {
				severalRooms = "0";
				nonSuiteNum = Integer.parseInt(severalRooms);
			} else {
				nonSuiteNum = Integer.parseInt(severalRooms);
			}

			if (suiteNum == 0 && nonSuiteNum == 0) {
				errorMessage.put("roomNum", "房間數量欄位必填");
			}

			// 讀取使用者輸入,由瀏覽器送來的agreeShare欄位內的資料(非必填)checkbox
			if (agreeShareStr == null || agreeShareStr.trim().length() == 0) {
				agreeShareStr = "false";
			} else {
				agreeShareStr = "true";
			}

			agreeShare = Boolean.parseBoolean(agreeShareStr);
			// 讀取使用者輸入,由瀏覽器送來的county欄位內的資料
			// 讀取使用者輸入,由瀏覽器送來的district(多選checkbox)欄位內的資料
			// areaCode由欄位county和district(多選)組成

			if (county == null || county.trim().length() == 0) {
				county = null;
			}
			// user如果没打勾的话
			// request.getParameterValues("district")会接收到null值
			if (districts != null && districts.length > 0) {
				areaCode = "";
				for (int i = 0; i < districts.length; i++) {
					countyArea = county + districts[i];
					areaStrToCode = ConvertTableUtil.addressStringToCode(countyArea) + ",";
					areaCode += areaStrToCode;
				}
				areaCode = areaCode.substring(0, areaCode.length() - 1);
			}

			// 讀取使用者輸入,由瀏覽器送來的budget欄位內的資料
			// 檢查使用者所輸入的資料
//		Integer rentBudget = null;		
			if (budget == null || budget.trim().length() == 0) {
				errorMessage.put("budget", "預算欄位為必填");
			} else {
				try {
					rentBudget = Integer.parseInt(budget.trim());
				} catch (NumberFormatException e1) {
					rentBudget = 0;
					e1.printStackTrace();
				}
			}
			// 讀取使用者輸入,由瀏覽器送來的checkInDate欄位內的資料

			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			// 日期的格式化 將得到的字串轉成java.util.Date;
			if (cid == null || cid.trim().length() == 0) {
				checkInDate = null;
			} else {
				try {
					checkInDate = new Date(formatDate.parse((String) cid).getTime());
				} catch (ParseException e2) {
					e2.printStackTrace();
				}
			}
			// 讀取使用者輸入,由瀏覽器送來的liveTime欄位內的資料
//		String liveTime = request.getParameter("liveTime");

			if (liveTime.trim().equals("小於3個月")) {
				liveTime = "0";
			} else if (liveTime.trim().equals("3個月")) {
				liveTime = "1";
			} else if (liveTime.trim().equals("半年")) {
				liveTime = "2";
			} else if (liveTime.trim().equals("1年")) {
				liveTime = "3";
			} else {
				liveTime = "4";
			}

			// 想要的設施欄位 checkbox 因存進資料庫是分成個別的欄位去存 所以欄位名稱就直接取不相同了
			// 讀取瀏覽器中的hasWashingMachine欄位

			if (hasWashingMachine == null || hasWashingMachine.trim().length() == 0) {
				washingMachine = false;
			} else {
				washingMachine = true;
			}
//		Boolean washingMachine = null;
			// 讀取瀏覽器中的hasRefrigerator欄位

			if (hasRefrigerator == null || hasRefrigerator.trim().length() == 0) {
				refrigerator = false;
			} else {
				refrigerator = true;
			}

			if (hasTV == null || hasTV.trim().length() == 0) {
				tv = false;
			} else {
				tv = true;
			}

			// 讀取使用者輸入,由瀏覽器送來的hasAirConditioning欄位內的資料

			if (hasAirConditioning == null || hasAirConditioning.trim().length() == 0) {
				airConditioning = false;
			} else {
				airConditioning = true;
			}

			if (hasWaterHeater == null || hasWaterHeater.trim().length() == 0) {
				waterHeater = false;
			} else {
				waterHeater = true;
			}
			// 讀取使用者輸入,由瀏覽器送來的hasInternet欄位內的資料

			if (hasInternet == null || hasInternet.trim().length() == 0) {
				internet = false;
			} else {
				internet = true;
			}

			if (hasFourthTV == null || hasFourthTV.trim().length() == 0) {
				fourthTV = false;
			} else {
				fourthTV = true;
			}

			if (hasGas == null || hasGas.trim().length() == 0) {
				gas = false;
			} else {
				gas = true;
			}

			if (hasWardrobe == null || hasWardrobe.trim().length() == 0) {
				wardrobe = false;
			} else {
				wardrobe = true;
			}

			if (hasSofa == null || hasSofa.trim().length() == 0) {
				sofa = false;
			} else {
				sofa = true;
			}

			// 讀取使用者輸入,由瀏覽器送來的hasTable欄位內的資料

			if (hasTable == null || hasTable.trim().length() == 0) {
				table = false;
			} else {
				table = true;
			}

			if (hasChair == null || hasChair.trim().length() == 0) {
				chair = false;
			} else {
				chair = true;
			}

			if (hasParking == null || hasParking.trim().length() == 0) {
				parking = false;
			} else {
				parking = true;
			}

			if (hasBalcony == null || hasBalcony.trim().length() == 0) {
				balcony = false;
			} else {
				balcony = true;
			}

			if (hasSingleBed == null || hasSingleBed.trim().length() == 0) {
				singleBed = false;
			} else {
				singleBed = true;
			}

			if (hasDoubleBed == null || hasDoubleBed.trim().length() == 0) {
				doubleBed = false;
			} else {
				doubleBed = true;
			}

			cook = Boolean.parseBoolean(allowCook);
			// 讀取使用者輸入,由瀏覽器送來的age欄位內的資料(依照peopleNum_gender的value動態新增)

			if (peopleNum_gender.trim().equals("1男") || peopleNum_gender.trim().equals("1女")) {

				if (userAge.trim().equals("default")) {
					age = null;
				} else {
					age = userAge;
				}

			} else if (peopleNum_gender.trim().equals("1男1女") || peopleNum_gender.trim().equals("2男")
					|| peopleNum_gender.trim().equals("2女")) {

				if (ageMin.trim().equals("default")) {
					ageMin = null;
				}
				if (ageMax.trim().equals("default")) {
					ageMax = null;
				}

				age = ageMin + "," + ageMax;
			}

			// 讀取使用者輸入,由瀏覽器送來的job欄位內的資料
			if (job.trim().equals("學生")) {
				job = "0";
			} else if (job.trim().equals("上班族")) {
				job = "1";
			} else if (job.trim().equals("混合")) {
				job = "2";
			} else if (job.trim().equals("其他")) {
				job = "3";
			}
			// 讀取使用者輸入,由瀏覽器送來的allowSmoke欄位內的資料

			smoke = Boolean.parseBoolean(allowSmoke);
			// 讀取使用者輸入,由瀏覽器送來的allowPet欄位內的資料

			pet = Boolean.parseBoolean(allowPet);
			// 讀取使用者輸入,由瀏覽器送來的sexualOrientation欄位內的資料
			if (sexualOrientation.trim().equals("不透漏")) {
				sexualOrientation = "0";
			} else if (sexualOrientation.trim().equals("異性戀")) {
				sexualOrientation = "1";
			} else if (sexualOrientation.trim().equals("同性戀")) {
				sexualOrientation = "2";
			} else if (sexualOrientation.trim().equals("雙性戀")) {
				sexualOrientation = "3";
			} else if (sexualOrientation.trim().equals("混合")) {
				sexualOrientation = "4";
			}
			// 讀取使用者輸入,由瀏覽器送來的agreeAdCondition欄位內的資料

			if (agreeAdCondition == null || agreeAdCondition.trim().length() == 0) {
				agreeAdSearchCondition = false;
			} else {
				agreeAdSearchCondition = true;
			}

			if (wantedRoommatesGender.trim().equals("不介意")) {
				wantedRoommatesGender = "2";
			} else if (wantedRoommatesGender.trim().equals("男")) {
				wantedRoommatesGender = "0";
			} else if (wantedRoommatesGender.trim().equals("女")) {
				wantedRoommatesGender = "1";
			}
			// 讀取使用者輸入,由瀏覽器送來的wantedRoommatesAge欄位內的資料
			// wantedRoommatesAge欄位 由roommatesAgeMin和roommatesAgeMax組成

			if (roommatesAgeMin.trim().equals("default") && roommatesAgeMax.trim().equals("default")) {

				wantedRoommatesAge = null + "," + null;
			} else if (!roommatesAgeMin.trim().equals("default") && roommatesAgeMax.trim().equals("default")) {
				wantedRoommatesAge = roommatesAgeMin + "," + null;
			} else if (roommatesAgeMin.trim().equals("default") && !roommatesAgeMax.trim().equals("default")) {
				wantedRoommatesAge = null + "," + roommatesAgeMax;
			} else {
				wantedRoommatesAge = roommatesAgeMin + "," + roommatesAgeMax;
			}
			// 讀取使用者輸入,由瀏覽器送來的wantedRoommatesJob欄位內的資料

			if (wantedRoommatesJob.trim().equals("學生")) {
				wantedRoommatesJob = "0";
			} else if (wantedRoommatesJob.trim().equals("上班族")) {
				wantedRoommatesJob = "1";
			} else if (wantedRoommatesJob.trim().equals("不介意")) {
				wantedRoommatesJob = "4";
			}
			// 讀取使用者輸入,由瀏覽器送來的wantedRoommatesSmoke欄位內的資料

			roommatesSmoke = Boolean.parseBoolean(wantedRoommatesSmoke);
			// 讀取使用者輸入,由瀏覽器送來的wantedRoommatesPet欄位內的資料

			roommatesPet = Boolean.parseBoolean(wantedRoommatesPet);
			// 讀取使用者輸入,由瀏覽器送來的wantedRoommatesSex欄位內的資料
			if (wantedRoommatesSex.trim().equals("不介意")) {
				wantedRoommatesSex = "5";
			} else if (wantedRoommatesSex.trim().equals("異性戀")) {
				wantedRoommatesSex = "1";
			} else if (wantedRoommatesSex.trim().equals("同性戀")) {
				wantedRoommatesSex = "2";
			} else if (wantedRoommatesSex.trim().equals("雙性戀")) {
				wantedRoommatesSex = "3";
			} else if (wantedRoommatesSex.trim().equals("混合")) {
				wantedRoommatesSex = "4";
			}
			// 讀取使用者輸入,由瀏覽器送來的adTitle欄位內的資料

			if (adTitle.trim().length() == 0) {
				adTitle = null;
			}
			// 取使用者輸入,由瀏覽器送來的adDescription欄位內的資料

			if (description.trim().length() == 0) {

			} else {
				try {
					adDescription = new SerialClob(description.toCharArray());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			// 讀取使用者輸入,由瀏覽器送來的phone欄位內的資料

			if (phone.trim().length() == 0) {
				phone = null;
			}
			// 讀取使用者輸入,由瀏覽器送來的images欄位內的資料

			// 讀取使用者輸入,由瀏覽器送來的emailMax欄位內的資料

			// 當user有選emailMax欄位時emailAlertInstant必為true存進去資料庫,反之
			if (emailMaxStr.trim().equals("default")) {
				emailMaxStr = "12";
			}
			try {
				emailMax = Integer.parseInt(emailMaxStr);
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			}
		}
		// 如果有錯誤,呼叫view元件,送回錯誤訊息
		if (!errorMessage.isEmpty()) {
//			RequestDispatcher rd = request.getRequestDispatcher("/PostWantedRoomAd");
//			rd.forward(request, response);
			return "_4u4u_PostAd/PostWantedRoomAd";
		}

		if (parts != null) { // 如果這是一個上傳資料的表單
			if (!new File("d:\\images\\wantedRoomAd").exists()) {
				new File("d:\\images\\wantedRoomAd").mkdirs();
			}
			for (Part p : parts) {
				if (p.getContentType() != null) {
					String fileName = GlobalService.getFileName(p);
					long sizeInBytes = 0;
					InputStream is = null;
					if (fileName != null && fileName.trim().length() > 0) {
						fileName = System.currentTimeMillis()
								+ GlobalService.getFileName(p).substring(GlobalService.getFileName(p).indexOf("."));
						images += fileName + ",";
						sizeInBytes = p.getSize();
						is = p.getInputStream();
						int len = 0;
						byte[] byteArray = new byte[(int) sizeInBytes];
						File uploadFile = new File("d:\\images\\wantedRoomAd", fileName);
						try (FileOutputStream fos = new FileOutputStream(uploadFile);) {
							while ((len = is.read(byteArray)) != -1) {
								fos.write(byteArray, 0, len);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						images = null;
					}
				}
			}
			if (images != null && images.trim().length() != 0) {
				images = images.substring(0, images.length() - 1);
			}
		}

		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");
//		MemberBean member = new MemberDaoImpl().getMemberByEmail(mb.getEmail());
		// WantedRoomBean扮演封裝輸入資料的角色
		WantedRoomBean wantedRoomBean = new WantedRoomBean(findRoomId, peopleNum_gender, suiteNum, nonSuiteNum,
				agreeShare, areaCode, rentBudget, checkInDate, liveTime, washingMachine, refrigerator, tv,
				airConditioning, waterHeater, internet, fourthTV, gas, wardrobe, sofa, table, chair, parking, balcony,
				singleBed, doubleBed, cook, age, job, smoke, pet, sexualOrientation, agreeAdSearchCondition,
				wantedRoommatesGender, wantedRoommatesAge, wantedRoommatesJob, roommatesSmoke, roommatesPet,
				wantedRoommatesSex, adTitle, adDescription, phone, images, emailMax, adState, sendMail, emailDate,
				adStyle, adCreateDate, adUpdateDate, mb);

		try {
//			WantedRoomService service = new WantedRoomServiceImpl();
//			MemberService memberService = new MemberServiceImpl();

			///////////// 判斷會員狀態可否發文 ///////////////////
			String memberState = memberService.memberState(mb);
			boolean isExistAd = wantedRoomService.isExistAd(mb);

			if (!memberState.equals("1") && !memberState.equals("2")) {
				throw new CantCreateAdException("不是一般會員也不是VIP會員");
			} else if (isExistAd) {
				throw new CantCreateAdException("找房廣告只能發佈一則");
			}
			////////////////////////////////////////////////////////
			int n = wantedRoomService.saveFindRoomAd(wantedRoomBean);
			emailService.sendMatchEmail(wantedRoomBean); // 寄出符合信

			////////////////// 印出符合的信件 ////////////////////
			List<RoomRentBean> list = wantedRoomService.findingMatchRoomAd(wantedRoomBean);
			for (RoomRentBean roomRentBean : list) {
				System.err.println("找到以下符合廣告的信件");
				System.err.println("廣告編號為： " + roomRentBean.getAdId() + "   廣告標題為： " + roomRentBean.getAdTitle());
			}
			////////////////////////////////////////////////////////

			if (n == 1) {
				msgOK.put("InsertAdOK", "<Font color='red'>發佈成功</Font>");
//				response.sendRedirect(response.encodeRedirectURL("/4u4u/"));
				return "redirect:/";
			} else {
				errorMessage.put("errorInsert", "新增此筆資料有誤(ProcessWantedRoomAd)");

			}
		} catch (CantCreateAdException e) {
			e.printStackTrace();
			errorMessage.put("exception", e.getMessage());
			// 產生 RequestDispatcher 物件 rd
			return "_4u4u_PostAd/PostWantedRoomAd";
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage.put("exception", "資料庫存取錯誤:" + e.getMessage());
			// 產生 RequestDispatcher 物件 rd
			return "_4u4u_PostAd/PostWantedRoomAd";

		}
		if (!errorMessage.isEmpty()) {
			return "_4u4u_PostAd/PostWantedRoomAd";

		}
		return "redirect:/";
	}

	@RequestMapping(value = { "/_4u4u/controller/ProcessSearchAjax.do",
			"/_4u4u/controller/ProcessSearchRoomRentVip.do",
			"/_4u4u/controller/ProcessSearchFindRoomVip.do"
			}, method = RequestMethod.GET)
	public void executeAjaxSearch(Model model, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");

		if (request.getServletPath().substring(request.getServletPath().lastIndexOf("/") + 1)
				.contentEquals("ProcessSearchAjax.do")) {
			String url = request.getParameter("url");
			String[] queryArray = url.substring(url.indexOf("?") + 1).split("&");
			Map<String, String> conditionMap = new HashMap<String, String>();
			for (String result : queryArray) {
				String[] tempArray = result.split("=");
				conditionMap.put(tempArray[0], tempArray[1]);
			}
			if (conditionMap.get("searchType") != null && conditionMap.get("searchType").contentEquals("0")) {
//				RoomRentService service = new RoomRentServiceImpl();
				try (PrintWriter pw = response.getWriter();) {
//					System.out.println(service.getAjaxSearchJsonData(conditionMap));
					pw.write(roomRentService.getAjaxSearchJsonData(conditionMap));
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (conditionMap.get("searchType") != null && (conditionMap.get("searchType").contentEquals("1")
					|| conditionMap.get("searchType").contentEquals("2"))) {

//				WantedRoomService service = new WantedRoomServiceImpl();

				try (PrintWriter pw = response.getWriter();) {
					pw.write(wantedRoomService.getAjaxSearchJsonData(conditionMap));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			return;
		} else if (request.getServletPath().substring(request.getServletPath().lastIndexOf("/") + 1)
				.contentEquals("ProcessSearchRoomRentVip.do")) {
//			RoomRentService service = new RoomRentServiceImpl();
			try (PrintWriter pw = response.getWriter();) {
				pw.write(roomRentService.getVIPAdsJsonData());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}else if (request.getServletPath().substring(request.getServletPath().lastIndexOf("/") + 1)
				.contentEquals("ProcessSearchFindRoomVip.do")) {
//			RoomRentService service = new RoomRentServiceImpl();
			try (PrintWriter pw = response.getWriter();) {
				pw.write(wantedRoomService.getVIPAdsJsonData());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}

	}

	@RequestMapping(value = "/saveAd", method = RequestMethod.GET, params = { "adStyle", "adId" })
	public void saveLikeAd(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "adStyle") String adStyle, @RequestParam(value = "adId") Integer adId) {

		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");

		if (mb == null) {

			try (PrintWriter pw = response.getWriter();) {
				pw.write("需要登入");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;

		}
		if (mb != null) {
			try {
				int result = memberService.saveLikeAd(mb, adStyle, adId);
				if (result == 1) {
					try (PrintWriter pw = response.getWriter();) {
						pw.write("取消儲存廣告");
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if (result == -1) {
					try (PrintWriter pw = response.getWriter();) {
						pw.write("同一人");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				try (PrintWriter pw = response.getWriter();) {
					pw.write("錯誤");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

	}

	@RequestMapping(value = "/cancelSavedAd", method = RequestMethod.GET, params = { "adStyle", "adId" })
	public void cancelSavedAd(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "adStyle") String adStyle, @RequestParam(value = "adId") Integer adId) {

		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");

		if (mb != null) {
			if (memberService.cancelSavedAd(mb, adStyle, adId)) {
				try (PrintWriter pw = response.getWriter();) {
					pw.write("儲存廣告");
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {

				try (PrintWriter pw = response.getWriter();) {
					pw.write("錯誤");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}

	@RequestMapping(value = "/checkButtonState", method = RequestMethod.GET, params = { "adStyle", "adId" })
	public void checkAdDetailButtonState(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "adStyle") String adStyle, @RequestParam(value = "adId") Integer adId) {

		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");
		if (mb == null) {
			try (PrintWriter pw = response.getWriter();) {
				String test = new Gson().toJson(new ArrayList<String>());
				pw.write(test);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}

		if (mb != null) {
			String result = memberService.checkAdDetailButtonState(mb, adStyle, adId);
			try (PrintWriter pw = response.getWriter();) {
				pw.write(result);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@RequestMapping(value = "/showInterest", method = RequestMethod.GET, params = { "adStyle", "adId" })
	public void saveInterestedAd(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "adStyle") String adStyle, @RequestParam(value = "adId") Integer adId) {

		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");

		if (mb == null) {

			try (PrintWriter pw = response.getWriter();) {
				pw.write("需要登入");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;

		}
		if (mb != null) {

			if (adStyle.contentEquals("0")) {
				if (!wantedRoomService.isExistEffectiveAd(mb)) {

					try (PrintWriter pw = response.getWriter();) {
						pw.write("您需要找房廣告");
					} catch (Exception e) {
						e.printStackTrace();
					}
					return;
				}

			} else {

				if (!roomRentService.isExistEffectiveAd(mb)) {

					try (PrintWriter pw = response.getWriter();) {
						pw.write("您需要租房廣告");
					} catch (Exception e) {
						e.printStackTrace();
					}
					return;

				}
			}
			try {
				int result = memberService.saveInterestedAd(mb, adStyle, adId);
				if (result == 1) {
					try (PrintWriter pw = response.getWriter();) {
						pw.write("取消感興趣");
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if (result == -1) {
					try (PrintWriter pw = response.getWriter();) {
						pw.write("同一人");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				try (PrintWriter pw = response.getWriter();) {
					pw.write("錯誤");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}

	}

	@RequestMapping(value = "/cancelShowInterest", method = RequestMethod.GET, params = { "adStyle", "adId" })
	public void cancelInterestedAd(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "adStyle") String adStyle, @RequestParam(value = "adId") Integer adId) {

		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");

		if (mb != null) {

			if (memberService.cancelInterestedAd(mb, adStyle, adId)) {
				try (PrintWriter pw = response.getWriter();) {
					pw.write("感興趣");
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {

				try (PrintWriter pw = response.getWriter();) {
					pw.write("錯誤");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}

	@RequestMapping(value = "/deleteInterestedAd", method = RequestMethod.GET, params = { "adStyle", "adId" })
	public void deleteInterestedAdOnly(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "adStyle") String adStyle, @RequestParam(value = "adId") Integer adId) {

		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");

		if (mb != null) {

			if (memberService.deleteInterestedAdOnly(adStyle, adId)) {
				try (PrintWriter pw = response.getWriter();) {
					pw.write("成功");
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {

				try (PrintWriter pw = response.getWriter();) {
					pw.write("錯誤");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}

	@RequestMapping(value = "/savedAd", method = RequestMethod.GET)
	public String getSavedAdPage() {
		return "_4u4u_Account/savedAd";
	}

	@RequestMapping(value = "/savedAd", method = RequestMethod.POST, params = { "adStyle", "curPage", "sortOption" })
	public void getSavedAds(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "adStyle") String adStyle, @RequestParam(value = "curPage") Integer curPage,
			@RequestParam(value = "sortOption", defaultValue = "0") String sortOption) {

		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");

		if (mb == null) {

			try (PrintWriter pw = response.getWriter();) {
				Gson gson = new Gson();
				String jsonStr = gson.toJson(new ArrayList<FinalJsonObject>());
				pw.write(jsonStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;

		}
		if (mb != null) {
			if (adStyle.trim().contentEquals("0")) {
				try (PrintWriter pw = response.getWriter();) {
					pw.write(roomRentService.getAjaxSavedAdJsonData(mb, curPage, sortOption));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try (PrintWriter pw = response.getWriter();) {
					pw.write(wantedRoomService.getAjaxSavedAdJsonData(mb, curPage, sortOption));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

	}

	@RequestMapping(value = "/interestedAd", method = RequestMethod.GET)
	public String getInterestedAdPage() {
		return "_4u4u_Account/whoInterestedMyAd";
	}

	@RequestMapping(value = "/interestedAd", method = RequestMethod.POST, params = { "adStyle", "curPage" })
	public void getInterestedAds(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "adStyle") String adStyle, @RequestParam(value = "curPage") Integer curPage) {

		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");

		if (mb == null) {

			try (PrintWriter pw = response.getWriter();) {
				Gson gson = new Gson();
				String jsonStr = gson.toJson(new ArrayList<FinalJsonObject>());
				pw.write(jsonStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;

		}
		if (mb != null) {
			if (adStyle.trim().contentEquals("0")) {
				try (PrintWriter pw = response.getWriter();) {
					pw.write(roomRentService.getAjaxInterestedAdJsonData(mb, curPage));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try (PrintWriter pw = response.getWriter();) {
					pw.write(wantedRoomService.getAjaxInterestedAdJsonData(mb, curPage));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

	}

	@RequestMapping(value = "/_4u4u/UpdateWantedRoomAdServlet", method = RequestMethod.POST)
	public String UpdateWantedRoomAd(Model model, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		if (!session.isNew()) {
			wantedRoomBean = (WantedRoomBean) session.getAttribute("findRoomAd");
		} else {
			wantedRoomBean = new WantedRoomBean();
		}
		// 定義存放錯誤訊息的 Collection物件
		Map<String, String> errorMessage = new HashMap<String, String>();
		Map<String, String> msgOK = new HashMap<String, String>();
		model.addAttribute("ErrorMsg", errorMessage);
		session.setAttribute("MsgOK", msgOK); // 顯示正常訊息
		// 讀取使用者所輸入，由瀏覽器送來的peopleNum_gender欄位內的資料，注意大小寫
		String peopleNum_gender = null; // 廣告者性別及人數
		Integer suiteNum = null;
		String agreeShareStr = null;
		String countyArea = null;
		Integer nonSuiteNum = null;
		Boolean agreeShare = null; // 合租意願
		String areaCode = null; // 地區(二級搜索) e.x:台南市歸仁區
		Integer rentBudget = null; // 租金預算
		Date checkInDate = null; // 可入住日期
		String liveTime = null; // 居住時間
		// 找房 想要的設施
		Boolean washingMachine = null; // 洗衣機
		Boolean refrigerator = null; // 冰箱
		Boolean tv = null; // 電視
		Boolean airConditioning = null; // 冷氣
		Boolean waterHeater = null; // 熱水器
		Boolean internet = null; // 網路
		Boolean fourthTV = null; // 第四台
		Boolean gas = null; // 天然瓦斯
		Boolean wardrobe = null; // 衣櫃
		Boolean sofa = null; // 沙發
		Boolean table = null; // 桌子
		Boolean chair = null; // 椅子
		Boolean parking = null; // 停車位
		Boolean balcony = null; // 陽台
		Boolean singleBed = null; // 單人床
		Boolean doubleBed = null; // 雙人床
		Boolean cook = null; // 開火
		String age = null; // 年齡
		String job = null; // 職業
		Boolean smoke = null; // 抽菸
		Boolean pet = null; // 養寵
		String sexualOrientation = null; // 性向
		Boolean agreeAdSearchCondition = null; // 同意(性向)成為廣告搜尋條件
		// 找房 想要的室友
		String wantedRoommatesGender = null; // 室友性別
		String wantedRoommatesAge = null; // 室友年齡
		String wantedRoommatesJob = null; // 室友職業
		Boolean roommatesSmoke = null; // 室友有無抽菸
		Boolean roommatesPet = null; // 室友有無養寵
		String wantedRoommatesSex = null; // 室友性向
		String hasAirConditioning = null;
		String hasRefrigerator = null;
		// 找房 廣告詳情
		String adTitle = null; // 廣告標題
		Clob adDescription = null; // 廣告現況描述
		String phone = null; // 手機(電話)
		String images = ""; // 上傳的照片(存進資料庫是圖片網址)
		Integer emailMax = null; // 上限幾封信
		Boolean adState = true; // 廣告是否失效
		Integer findRoomId = null;
		Integer sendMail = 0; // 本日已寄出幾封email
		Date emailDate = new Date(System.currentTimeMillis()); // 要寄信日期
		Timestamp adCreateDate = wantedRoomBean.getAdCreateDate(); // 創建日期永不更動
		Timestamp adUpdateDate = new Timestamp(System.currentTimeMillis());
		; // 更新日期
		Boolean adStyle = true; // 廣告類型
		String areaStrToCode = null;
		String county = null;
		String cid = null;
		String severalRooms = null;
		String severalSuites = null;
		String hasWashingMachine = null;
		String userAge = null;
		String ageMin = null;
		String ageMax = null;
		String hasInternet = null;
		String hasWaterHeater = null;
		String hasFourthTV = null;
		String hasGas = null;
		String hasWardrobe = null;
		String hasSofa = null;
		String hasTable = null;
		String hasChair = null;
		String hasParking = null;
		String hasBalcony = null;
		String hasSingleBed = null;
		String hasDoubleBed = null;
		String roommatesAgeMin = null;
		String roommatesAgeMax = null;
		String wantedRoommatesSmoke = null;
		String wantedRoommatesPet = null;
		String description = null;
		String emailMaxStr = null;
		String hasTV = null;
		String allowSmoke = null;
		String allowCook = null;
		String allowPet = null;
		String agreeAdCondition = null;
		String[] districts = null;
		String budget = null;
		Collection<Part> parts = request.getParts();
		GlobalService.exploreParts(parts, request);

		if (parts != null) { // 如果這是一個上傳資料的表單
			for (Part p : parts) {
				String fldName = p.getName();
				String[] valueArray = null;
				String value = null;
				if (fldName.equalsIgnoreCase("districtList")) {
					valueArray = request.getParameterValues(fldName);
				} else {
					value = request.getParameter(fldName);
				}
				// 1. 讀取使用者輸入資料
				if (p.getContentType() == null) {
					if (fldName.equals("peopleNum_gender")) {
						peopleNum_gender = value;
					} else if (fldName.equals("severalSuites")) {
						severalSuites = value;
					} else if (fldName.equals("hasDoubleBed")) {
						hasDoubleBed = value;
					} else if (fldName.equalsIgnoreCase("hasSingleBed")) {
						hasSingleBed = value;
					} else if (fldName.equalsIgnoreCase("allowCook")) {
						allowCook = value;
					} else if (fldName.equalsIgnoreCase("severalRooms")) {
						severalRooms = value;
					} else if (fldName.equalsIgnoreCase("agreeShare")) {
						agreeShareStr = value;
					} else if (fldName.equalsIgnoreCase("districtList")) {
						districts = valueArray;
					} else if (fldName.equalsIgnoreCase("budget")) {
						budget = value;
					} else if (fldName.equalsIgnoreCase("county")) {
						county = value;
					} else if (fldName.equalsIgnoreCase("hasBalcony")) {
						hasBalcony = value;
					} else if (fldName.equalsIgnoreCase("checkInDate")) {
						cid = value;
					} else if (fldName.equalsIgnoreCase("liveTime")) {
						liveTime = value;
					} else if (fldName.equalsIgnoreCase("hasWashingMachine")) {
						hasWashingMachine = value;
					} else if (fldName.equalsIgnoreCase("hasRefrigerator")) {
						hasRefrigerator = value;
					} else if (fldName.equalsIgnoreCase("hasTV")) {
						hasTV = value;
					} else if (fldName.equalsIgnoreCase("hasParking")) {
						hasParking = value;
					} else if (fldName.equalsIgnoreCase("hasChair")) {
						hasChair = value;
					} else if (fldName.equalsIgnoreCase("hasTable")) {
						hasTable = value;
					} else if (fldName.equalsIgnoreCase("hasFourthTV")) {
						hasFourthTV = value;
					} else if (fldName.equalsIgnoreCase("hasAirConditioning")) {
						hasAirConditioning = value;
					} else if (fldName.equalsIgnoreCase("hasWaterHeater")) {
						hasWaterHeater = value;
					} else if (fldName.equalsIgnoreCase("hasInternet")) {
						hasInternet = value;
					} else if (fldName.equalsIgnoreCase("agreeAdCondition")) {
						agreeAdCondition = value;
					} else if (fldName.equalsIgnoreCase("wantedRoommatesGender")) {
						wantedRoommatesGender = value;
					} else if (fldName.equalsIgnoreCase("roommatesAgeMin")) {
						roommatesAgeMin = value;
					} else if (fldName.equalsIgnoreCase("roommatesAgeMax")) {
						roommatesAgeMax = value;
					} else if (fldName.equalsIgnoreCase("wantedRoommatesSmoke")) {
						wantedRoommatesSmoke = value;
					} else if (fldName.equalsIgnoreCase("wantedRoommatesPet")) {
						wantedRoommatesPet = value;
					} else if (fldName.equalsIgnoreCase("wantedRoommatesJob")) {
						wantedRoommatesJob = value;
					} else if (fldName.equalsIgnoreCase("adDescription")) {
						description = value;
					} else if (fldName.equalsIgnoreCase("wantedRoommatesSex")) {
						wantedRoommatesSex = value;
					} else if (fldName.equalsIgnoreCase("phone")) {
						phone = value;
					} else if (fldName.equalsIgnoreCase("emailMax")) {
						emailMaxStr = value;
					} else if (fldName.equalsIgnoreCase("sexualOrientation")) {
						sexualOrientation = value;
					} else if (fldName.equalsIgnoreCase("job")) {
						job = value;
					} else if (fldName.equalsIgnoreCase("adTitle")) {
						adTitle = value;
					} else if (fldName.equalsIgnoreCase("hasWardrobe")) {
						hasWardrobe = value;
					} else if (fldName.equalsIgnoreCase("hasSofa")) {
						hasSofa = value;
					} else if (fldName.equalsIgnoreCase("age")) {
						userAge = value;
					} else if (fldName.equalsIgnoreCase("ageMin")) {
						ageMin = value;
					} else if (fldName.equalsIgnoreCase("ageMax")) {
						ageMax = value;
					} else if (fldName.equalsIgnoreCase("allowSmoke")) {
						allowSmoke = value;
					} else if (fldName.equalsIgnoreCase("allowPet")) {
						allowPet = value;
					} else if (fldName.equalsIgnoreCase("hasGas")) {
						hasGas = value;
					}
//					else if(fldName.equalsIgnoreCase("pageNo")) {
//						pageNo = value;
//					}

				}
			}

			// 檢查使用者所輸入的資料
			if (peopleNum_gender.trim().equals("default")) {
				errorMessage.put("peopleGender", "性別人數欄位必填");
			}
			model.addAttribute("peopleNum_gender", peopleNum_gender);
			// 讀取使用者輸入,由瀏覽器送來的roomType欄位內的資料
			// 在jsp檔是由severalSuites和severalRooms組成
			// 檢查使用者所輸入的資料
			if (severalSuites.trim().length() == 0) {
				severalSuites = "0";
				suiteNum = Integer.parseInt(severalSuites);
			} else {
				suiteNum = Integer.parseInt(severalSuites);
			}
			model.addAttribute("suiteNum", suiteNum);
			// 檢查使用者所輸入的資料
			if (severalRooms.trim().length() == 0) {
				severalRooms = "0";
				nonSuiteNum = Integer.parseInt(severalRooms);
			} else {
				nonSuiteNum = Integer.parseInt(severalRooms);
			}
			model.addAttribute("nonSuiteNum", nonSuiteNum);

			if (suiteNum == 0 && nonSuiteNum == 0) {
				errorMessage.put("roomNum", "房間數量欄位必填");
			}

			// 讀取使用者輸入,由瀏覽器送來的agreeShare欄位內的資料(非必填)checkbox
			if (agreeShareStr == null || agreeShareStr.trim().length() == 0) {
				agreeShareStr = "false";
			} else {
				agreeShareStr = "true";
			}

			agreeShare = Boolean.parseBoolean(agreeShareStr);
			model.addAttribute("agreeShare", agreeShare);
			// 讀取使用者輸入,由瀏覽器送來的county欄位內的資料
			// 讀取使用者輸入,由瀏覽器送來的district(多選checkbox)欄位內的資料
			// areaCode由欄位county和district(多選)組成

			if (county == null || county.trim().length() == 0) {
				county = null;
			}
			// user如果没打勾的话
			// request.getParameterValues("district")会接收到null值
			if (districts != null && districts.length > 0) {
				areaCode = "";
				for (int i = 0; i < districts.length; i++) {
					countyArea = county + districts[i];
					areaStrToCode = ConvertTableUtil.addressStringToCode(countyArea) + ",";
					areaCode += areaStrToCode;
				}
				areaCode = areaCode.substring(0, areaCode.length() - 1);
			}
			model.addAttribute("area", ConvertTableUtil.addressCodeToString(areaCode));
			// 讀取使用者輸入,由瀏覽器送來的budget欄位內的資料
			// 檢查使用者所輸入的資料
//		Integer rentBudget = null;		
			if (budget == null || budget.trim().length() == 0) {
				errorMessage.put("budget", "預算欄位為必填");
			} else {
				try {
					rentBudget = Integer.parseInt(budget.trim());
				} catch (NumberFormatException e1) {
					rentBudget = 0;
					e1.printStackTrace();
				}
			}
			model.addAttribute("budget", rentBudget);
			// 讀取使用者輸入,由瀏覽器送來的checkInDate欄位內的資料

			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			// 日期的格式化 將得到的字串轉成java.util.Date;
			if (cid == null || cid.trim().length() == 0) {
				checkInDate = null;
			} else {
				try {
					checkInDate = new Date(formatDate.parse((String) cid).getTime());
				} catch (ParseException e2) {
					e2.printStackTrace();
				}
			}
			model.addAttribute("checkInDate", checkInDate);
			// 讀取使用者輸入,由瀏覽器送來的liveTime欄位內的資料
//		String liveTime = request.getParameter("liveTime");

			if (liveTime.trim().equals("小於3個月")) {
				liveTime = "0";
			} else if (liveTime.trim().equals("3個月")) {
				liveTime = "1";
			} else if (liveTime.trim().equals("半年")) {
				liveTime = "2";
			} else if (liveTime.trim().equals("1年")) {
				liveTime = "3";
			} else {
				liveTime = "4";
			}
			model.addAttribute("liveTime", liveTime);
			// 想要的設施欄位 checkbox 因存進資料庫是分成個別的欄位去存 所以欄位名稱就直接取不相同了
			// 讀取瀏覽器中的hasWashingMachine欄位

			if (hasWashingMachine == null || hasWashingMachine.trim().length() == 0) {
				washingMachine = false;
			} else {
				washingMachine = true;
			}
//		Boolean washingMachine = null;
			// 讀取瀏覽器中的hasRefrigerator欄位
			model.addAttribute("washingMachine", washingMachine);
			if (hasRefrigerator == null || hasRefrigerator.trim().length() == 0) {
				refrigerator = false;
			} else {
				refrigerator = true;
			}
			model.addAttribute("refrigerator", refrigerator);
			if (hasTV == null || hasTV.trim().length() == 0) {
				tv = false;
			} else {
				tv = true;
			}
			model.addAttribute("tv", tv);
			// 讀取使用者輸入,由瀏覽器送來的hasAirConditioning欄位內的資料

			if (hasAirConditioning == null || hasAirConditioning.trim().length() == 0) {
				airConditioning = false;
			} else {
				airConditioning = true;
			}
			model.addAttribute("airConditioning", airConditioning);

			if (hasWaterHeater == null || hasWaterHeater.trim().length() == 0) {
				waterHeater = false;
			} else {
				waterHeater = true;
			}
			model.addAttribute("waterHeater", waterHeater);
			// 讀取使用者輸入,由瀏覽器送來的hasInternet欄位內的資料

			if (hasInternet == null || hasInternet.trim().length() == 0) {
				internet = false;
			} else {
				internet = true;
			}
			model.addAttribute("internet", internet);
			if (hasFourthTV == null || hasFourthTV.trim().length() == 0) {
				fourthTV = false;
			} else {
				fourthTV = true;
			}
			model.addAttribute("fourthTV", fourthTV);
			if (hasGas == null || hasGas.trim().length() == 0) {
				gas = false;
			} else {
				gas = true;
			}
			model.addAttribute("gas", gas);

			if (hasWardrobe == null || hasWardrobe.trim().length() == 0) {
				wardrobe = false;
			} else {
				wardrobe = true;
			}
			model.addAttribute("wardrobe", wardrobe);

			if (hasSofa == null || hasSofa.trim().length() == 0) {
				sofa = false;
			} else {
				sofa = true;
			}
			model.addAttribute("sofa", sofa);
			// 讀取使用者輸入,由瀏覽器送來的hasTable欄位內的資料

			if (hasTable == null || hasTable.trim().length() == 0) {
				table = false;
			} else {
				table = true;
			}
			model.addAttribute("table", table);

			if (hasChair == null || hasChair.trim().length() == 0) {
				chair = false;
			} else {
				chair = true;
			}
			model.addAttribute("chair", chair);

			if (hasParking == null || hasParking.trim().length() == 0) {
				parking = false;
			} else {
				parking = true;
			}
			model.addAttribute("parking", parking);

			if (hasBalcony == null || hasBalcony.trim().length() == 0) {
				balcony = false;
			} else {
				balcony = true;
			}
			model.addAttribute("balcony", balcony);
			if (hasSingleBed == null || hasSingleBed.trim().length() == 0) {
				singleBed = false;
			} else {
				singleBed = true;
			}
			model.addAttribute("singleBed", singleBed);

			if (hasDoubleBed == null || hasDoubleBed.trim().length() == 0) {
				doubleBed = false;
			} else {
				doubleBed = true;
			}
			model.addAttribute("doubleBed", doubleBed);

			cook = Boolean.parseBoolean(allowCook);
			model.addAttribute("cook", cook);
			// 讀取使用者輸入,由瀏覽器送來的age欄位內的資料(依照peopleNum_gender的value動態新增)

			if (peopleNum_gender.trim().equals("1男") || peopleNum_gender.trim().equals("1女")) {

				if (userAge.trim().equals("default")) {
					age = null;
				} else {
					age = userAge;
				}
				model.addAttribute("age", age);
			} else if (peopleNum_gender.trim().equals("1男1女") || peopleNum_gender.trim().equals("2男")
					|| peopleNum_gender.trim().equals("2女")) {

				if (ageMin.trim() == null) {
					ageMin = null;
				}
				if (ageMax.trim() == null) {
					ageMax = null;
				}

				age = ageMin + "," + ageMax;
				model.addAttribute("age", age);
			}

			// 讀取使用者輸入,由瀏覽器送來的job欄位內的資料
			if (job.trim().equals("學生")) {
				job = "0";
			} else if (job.trim().equals("上班族")) {
				job = "1";
			} else if (job.trim().equals("混合")) {
				job = "2";
			} else if (job.trim().equals("其他")) {
				job = "3";
			}
			model.addAttribute("job", job);
			// 讀取使用者輸入,由瀏覽器送來的allowSmoke欄位內的資料

			smoke = Boolean.parseBoolean(allowSmoke);
			model.addAttribute("smoke", smoke);
			// 讀取使用者輸入,由瀏覽器送來的allowPet欄位內的資料

			pet = Boolean.parseBoolean(allowPet);
			model.addAttribute("pet", pet);
			// 讀取使用者輸入,由瀏覽器送來的sexualOrientation欄位內的資料
			if (sexualOrientation.trim().equals("不透漏")) {
				sexualOrientation = "0";
			} else if (sexualOrientation.trim().equals("異性戀")) {
				sexualOrientation = "1";
			} else if (sexualOrientation.trim().equals("同性戀")) {
				sexualOrientation = "2";
			} else if (sexualOrientation.trim().equals("雙性戀")) {
				sexualOrientation = "3";
			} else if (sexualOrientation.trim().equals("混合")) {
				sexualOrientation = "4";
			}
			model.addAttribute("sexualOrientation", sexualOrientation);
			// 讀取使用者輸入,由瀏覽器送來的agreeAdCondition欄位內的資料

			if (agreeAdCondition == null || agreeAdCondition.trim().length() == 0) {
				agreeAdSearchCondition = false;
			} else {
				agreeAdSearchCondition = true;
			}
			model.addAttribute("agreeAdSearchCondition", agreeAdSearchCondition);
			if (wantedRoommatesGender.trim().equals("不介意")) {
				wantedRoommatesGender = "2";
			} else if (wantedRoommatesGender.trim().equals("男")) {
				wantedRoommatesGender = "0";
			} else if (wantedRoommatesGender.trim().equals("女")) {
				wantedRoommatesGender = "1";
			}
			model.addAttribute("wantedRoommatesGender", wantedRoommatesGender);
			// 讀取使用者輸入,由瀏覽器送來的wantedRoommatesAge欄位內的資料
			// wantedRoommatesAge欄位 由roommatesAgeMin和roommatesAgeMax組成

			if (roommatesAgeMin.trim().equals("default") && roommatesAgeMax.trim().equals("default")) {

				wantedRoommatesAge = null + "," + null;
			} else if (!roommatesAgeMin.trim().equals("default") && roommatesAgeMax.trim().equals("default")) {
				wantedRoommatesAge = roommatesAgeMin + "," + null;
			} else if (roommatesAgeMin.trim().equals("default") && !roommatesAgeMax.trim().equals("default")) {
				wantedRoommatesAge = null + "," + roommatesAgeMax;
			} else {
				wantedRoommatesAge = roommatesAgeMin + "," + roommatesAgeMax;
			}
			model.addAttribute("wantedRoommatesAge", wantedRoommatesAge);
			// 讀取使用者輸入,由瀏覽器送來的wantedRoommatesJob欄位內的資料

			if (wantedRoommatesJob.trim().equals("學生")) {
				wantedRoommatesJob = "0";
			} else if (wantedRoommatesJob.trim().equals("上班族")) {
				wantedRoommatesJob = "1";
			} else if (wantedRoommatesJob.trim().equals("不介意")) {
				wantedRoommatesJob = "4";
			}
			model.addAttribute("wantedRoommatesJob", wantedRoommatesJob);
			// 讀取使用者輸入,由瀏覽器送來的wantedRoommatesSmoke欄位內的資料

			roommatesSmoke = Boolean.parseBoolean(wantedRoommatesSmoke);
			model.addAttribute("roommatesSmoke", roommatesSmoke);
			// 讀取使用者輸入,由瀏覽器送來的wantedRoommatesPet欄位內的資料

			roommatesPet = Boolean.parseBoolean(wantedRoommatesPet);
			model.addAttribute("roommatesPet", roommatesPet);
			// 讀取使用者輸入,由瀏覽器送來的wantedRoommatesSex欄位內的資料
			if (wantedRoommatesSex.trim().equals("不介意")) {
				wantedRoommatesSex = "5";
			} else if (wantedRoommatesSex.trim().equals("異性戀")) {
				wantedRoommatesSex = "1";
			} else if (wantedRoommatesSex.trim().equals("同性戀")) {
				wantedRoommatesSex = "2";
			} else if (wantedRoommatesSex.trim().equals("雙性戀")) {
				wantedRoommatesSex = "3";
			} else if (wantedRoommatesSex.trim().equals("混合")) {
				wantedRoommatesSex = "4";
			}
			model.addAttribute("wantedRoommatesSex", wantedRoommatesSex);
			// 讀取使用者輸入,由瀏覽器送來的adTitle欄位內的資料

			if (adTitle.trim().length() == 0) {
				adTitle = null;
			}
			model.addAttribute("adTitle", adTitle);
			// 取使用者輸入,由瀏覽器送來的adDescription欄位內的資料

			if (description.trim().length() == 0) {

			} else {
				try {
					adDescription = new SerialClob(description.toCharArray());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			model.addAttribute("adDescription", adDescription);
			// 讀取使用者輸入,由瀏覽器送來的phone欄位內的資料

			if (phone.trim().length() == 0) {
				phone = null;
			}
			// 讀取使用者輸入,由瀏覽器送來的images欄位內的資料

			// 讀取使用者輸入,由瀏覽器送來的emailMax欄位內的資料

			// 當user有選emailMax欄位時emailAlertInstant必為true存進去資料庫,反之
			model.addAttribute("phone", phone);
			if (emailMaxStr.trim().equals("default")) {
				emailMaxStr = "12";
			}
			try {
				emailMax = Integer.parseInt(emailMaxStr);
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			}
		}
		model.addAttribute("emailMax", emailMax);
		// 如果有錯誤,呼叫view元件,送回錯誤訊息
		if (!errorMessage.isEmpty()) {
//			RequestDispatcher rd = request.getRequestDispatcher("/_4u4u_PostAd/UpdateWantedRoomAd.jsp");
//			rd.forward(request, response);
//			return;
		}

		if (parts != null) { // 如果這是一個上傳資料的表單
			if (!new File("d:\\images\\wantedRoomAd").exists()) {
				new File("d:\\images\\wantedRoomAd").mkdirs();
			}
			for (Part p : parts) {
				if (p.getContentType() != null) {
					String fileName = GlobalService.getFileName(p);
					long sizeInBytes = 0;
					InputStream is = null;
					if (fileName != null && fileName.trim().length() > 0) {
						fileName = System.currentTimeMillis()
								+ GlobalService.getFileName(p).substring(GlobalService.getFileName(p).indexOf("."));
						images += fileName + ",";
						sizeInBytes = p.getSize();
						try {
							is = p.getInputStream();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						int len = 0;
						byte[] byteArray = new byte[(int) sizeInBytes];
						File uploadFile = new File("d:\\images\\wantedRoomAd", fileName);
						try (FileOutputStream fos = new FileOutputStream(uploadFile);) {
							while ((len = is.read(byteArray)) != -1) {
								fos.write(byteArray, 0, len);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						images = null;
					}
				}
			}
			if (images != null && images.trim().length() != 0) {
				images = images.substring(0, images.length() - 1);
			}
		}
		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");
		String strMemId = request.getParameter("memId");
		Integer memId = null;
		if (strMemId != null) {
			memId = Integer.parseInt(request.getParameter("memId"));
		}
		Integer iAdStyle = null;
		if (adStyle == true) {
			iAdStyle = 1;
		}
//		MemberBean member = new MemberDaoImpl().getMemberByEmail(mb.getEmail());
		// WantedRoomBean扮演封裝輸入資料的角色
		findRoomId = wantedRoomBean.getFindRoomId();

		WantedRoomBean wantedRoomAds = new WantedRoomBean(findRoomId, peopleNum_gender, suiteNum, nonSuiteNum,
				agreeShare, areaCode, rentBudget, checkInDate, liveTime, washingMachine, refrigerator, tv,
				airConditioning, waterHeater, internet, fourthTV, gas, wardrobe, sofa, table, chair, parking, balcony,
				singleBed, doubleBed, cook, age, job, smoke, pet, sexualOrientation, agreeAdSearchCondition,
				wantedRoommatesGender, wantedRoommatesAge, wantedRoommatesJob, roommatesSmoke, roommatesPet,
				wantedRoommatesSex, adTitle, adDescription, phone, images, emailMax, adState, sendMail, emailDate,
				adStyle, adCreateDate, adUpdateDate, mb);

		try {
//			WantedRoomService service = new WantedRoomServiceImpl();
//			MemberService memberService = new MemberServiceImpl();

			///////////// 判斷會員狀態可否修改廣告 ///////////////////
			///////////// 失效的廣告也能修改 所以不判斷是否已有[有效廣告] ///////////////////
			String memberState = memberService.memberState(mb);
//			boolean isExistEffectiveAd = service.isExistEffectiveAd(mb);

			if (!memberState.equals("1") && !memberState.equals("2")) {
				throw new CantCreateAdException("不是一般會員也不是VIP會員");
			}
//			else if(isExistEffectiveAd) {
//				throw new CantCreateAdException("找房廣告只能發佈一則");
//			}
			////////////////////////////////////////////////////////

			int n = wantedRoomService.updateFindRoomAd(wantedRoomAds);
			if (n == 1) {
				msgOK.put("UpdateOK", "<Font color='red'>修改成功</Font>");
//				response.sendRedirect(response.encodeRedirectURL("/4u4u/_4u4u/MyAdServlet?memId="+memId+"&adStyle="+iAdStyle+"&adId="+findRoomId));
//				response.sendRedirect(response.encodeRedirectURL("/4u4u/_4u4u_Account/MyAd.jsp"));
//				response.sendRedirect(response.encodeRedirectURL("/_4u4u_Account/MyAd.jsp?adStyle=" + adStyle + "adId=" + adId));
				return "redirect:/_4u4u/MyAdServlet?memId=" + memId + "&adStyle=" + iAdStyle + "&adId=" + findRoomId;
			} else {
				errorMessage.put("errorUpdate", "修改此筆資料有誤(ProcessWantedRoomAd)");

			}
		} catch (CantCreateAdException e) {
			e.printStackTrace();
			errorMessage.put("exception", e.getMessage());
//			response.sendRedirect(response.encodeRedirectURL("/_4u4u_PostAd/UpdateWantedRoomAd.jsp"));
			return "/_4u4u_PostAd/UpdateWantedRoomAd";
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage.put("exception", "資料庫存取錯誤:" + e.getMessage());
			// 產生 RequestDispatcher 物件 rd
//			RequestDispatcher rd = request.getRequestDispatcher("/_4u4u_PostAd/UpdateWantedRoomAd.jsp");
			// 請容器代為呼叫下一棒程式
			return "/_4u4u_PostAd/UpdateWantedRoomAd";
		}
		if (!errorMessage.isEmpty()) {
//			RequestDispatcher rd = request.getRequestDispatcher("/_4u4u_PostAd/UpdateWantedRoomAd.jsp");
//			rd.forward(request,response);
			return "/_4u4u_PostAd/UpdateWantedRoomAd";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/_4u4u/UpdateRoomRentAdServlet", method = RequestMethod.POST)
	public String UpdateRoomRentAd(Model model, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		if (!session.isNew()) {
			roomRentBean = (RoomRentBean) session.getAttribute("RoomRentAd");
		} else {
			roomRentBean = new RoomRentBean();
		}

		// 定義存放錯誤訊息的 Collection物件
		Map<String, String> errorMessage = new HashMap<String, String>();
		Map<String, String> msgOK = new HashMap<String, String>();
		model.addAttribute("ErrorMsg", errorMessage);
		session.setAttribute("MsgOK", msgOK); // 顯示正常訊息

		Integer adId = null;
		String adRentType = null;
		Integer adRoomNum = null;
		String adToiletNum = null;
		String adBalconyNum = null;
		String adLivingRoomNum = null;
		String adIdentity = null;
		String adAgentFee = null;
		String adCurrentPeopleNum = null;
		String adHouseType = null;
		String adAreacode = null;
		String adDetailaddress = null;
		String adParkingCount = null;
		String adExtraCost = null;
		Boolean adHasElevator = null;
		String adMinimumStayLength = null;
		Date adAvailableDate = null;
		String adTotalArea = null;
		Integer adRentPrice = null;
		String adDeposit = null;
		String adNearByTransport = "";
		String adTitle = null;
		Clob adDetailContent = null;
		String adPhone = null;
		Boolean adPhoneAttachToAd = null;
		String adImages = "";
		Boolean adCook = null; // 針對整層出租
		Boolean adCurHasPet = null;
		Boolean adCurSmoke = null;
		String adCurSexOrientation = null;
		Boolean adCurAllowedSearchbySexOrient = null;

		String adCurGender = null;
		String adCurAge = null;
		Boolean adFutureSmoke = null;
		String adFutureJobType = null;
		Boolean adFuturePet = null;
		String adFutureGender = null;
		String adFutureAge = null;
		Boolean adFutureCoupleAccept = null;
		Boolean adState = true;
		Integer emailMax = null;
		Integer sendMail = 0;
		Date emailDate = new Date(System.currentTimeMillis()); // 要寄信日期
		Timestamp adCreateDate = roomRentBean.getAdCreateDate(); // 創建日期(不更動)取得之前的創建日期
		Timestamp adUpdateDate = new Timestamp(System.currentTimeMillis()); // 更新日期
		Boolean adStyle = false;
		String county = null;
//		Set<RoomBean> roomItems = null;
//		String district = null;

		Collection<Part> parts = request.getParts();
		GlobalService.exploreParts(parts, request);

		if (parts != null) { // 如果這是一個上傳資料的表單
			for (Part p : parts) {
				String fldName = p.getName();

				String value = null;

				value = request.getParameter(fldName);

				// 1. 讀取使用者輸入資料
				if (p.getContentType() == null) {
					if (fldName.equalsIgnoreCase("rentType")) {
						if (value.equalsIgnoreCase("出租房間(有公共空間)")) {
							adRentType = "3";
						} else if (value.equalsIgnoreCase("整層住家")) {
							adRentType = "0";
						} else if (value.equalsIgnoreCase("獨立套房")) {
							adRentType = "1";
						} else {
							adRentType = "2";
						}
					} else if (fldName.equalsIgnoreCase("houseCount")) {
						if (value.equalsIgnoreCase("none")) {
							errorMessage.put("houseNumber", "房間數量欄位必須要填");
							adRoomNum = 0;
						} else {
							value = value.substring(value.length() - 1);
							adRoomNum = Integer.parseInt(value.trim());
						}
					} else if (fldName.equalsIgnoreCase("wholePropertyToilet")) {
						if (value != null && value.trim().length() != 0) {
							adToiletNum = value;
						} else {
							adToiletNum = "0";

						}
					} else if (fldName.equalsIgnoreCase("wholePropertyLivingRoom")) {
						if (value != null && value.trim().length() != 0) {
							adLivingRoomNum = value;
						} else {
							adLivingRoomNum = "0";

						}
					} else if (fldName.equalsIgnoreCase("wholePropertyBalcony")) {
						if (value != null && value.trim().length() != 0) {
							adBalconyNum = value;
						} else {
							adBalconyNum = "0";

						}
					} else if (fldName.equalsIgnoreCase("adOwner")) {
						if (value.trim().equals("default")) {
							errorMessage.put("adIdentity", "廣告者身分需要填寫");
						} else {
							if (value.equalsIgnoreCase("live-out-landlord")) {
								adIdentity = "0";
							} else if (value.equalsIgnoreCase("live-in-landlord")) {
								adIdentity = "1";
							} else if (value.equalsIgnoreCase("currentTenant")) {
								adIdentity = "2";
							} else if (value.equalsIgnoreCase("agent")) {
								adIdentity = "3";
							} else {
								adIdentity = "4";
							}

						}
					} else if (fldName.equalsIgnoreCase("currentNum")) {
						adCurrentPeopleNum = value.trim();
					} else if (fldName.equalsIgnoreCase("propertyType")) {
						if (!value.trim().equals("default")) {
							if (value.trim().equals("公寓")) {
								adHouseType = "0";
							} else if (value.trim().equals("透天厝")) {
								adHouseType = "1";
							} else if (value.trim().equals("電梯大樓")) {
								adHouseType = "2";
							} else if (value.trim().equals("華廈")) {
								adHouseType = "3";
							}
						}
					} else if (fldName.equalsIgnoreCase("county")) {
						if (value == null || value.trim().length() == 0) {
							errorMessage.put("address", "地址欄位必填");
						} else {
							county = value.trim();
						}
					} else if (fldName.equalsIgnoreCase("address")) {
						if (value == null || value.trim().length() == 0) {
							errorMessage.put("address", "地址欄位必填");
						} else {
							adDetailaddress = county + value.trim();
						}

					} else if (fldName.equalsIgnoreCase("parkingNum")) {
						adParkingCount = value.trim();
					} else if (fldName.equalsIgnoreCase("elevator")) {
						adHasElevator = Boolean.valueOf(value.trim());
					} else if (fldName.equalsIgnoreCase("extraCost")) {
						if (value.trim().length() != 0) {
							adExtraCost = value.trim();
						}
					} else if (fldName.equalsIgnoreCase("wholePropertyArea") && adRentType.equals("0")) {
						if (value != null && value.trim().length() != 0) {
							adTotalArea = value.trim();
						} else {
//							errorMessage.put("TotalArea", "總坪數欄位必填");
						}
					} else if (fldName.equalsIgnoreCase("wholePropertyTotalRent") && adRentType.equals("0")) {
						if (value == null || value.trim().length() == 0) {
							errorMessage.put("TotalRent", "總租金欄位必填");
						} else {
							adRentPrice = Integer.parseInt(value.trim());
						}
					} else if (fldName.equalsIgnoreCase("wholePropertyDeposit") && adRentType.equals("0")) {
						adDeposit = value.trim();
					} else if (fldName.equalsIgnoreCase("minimumStay")) {
						adMinimumStayLength = value.trim();
					} else if (fldName.equalsIgnoreCase("availableDate")) {
						SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
						// 日期的格式化 將得到的字串轉成java.util.Date;
						if (value != null && value.trim().length() != 0) {

							try {
								adAvailableDate = new Date(formatDate.parse(value.trim()).getTime());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (fldName.equalsIgnoreCase("bus")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "公車站,";
						}
					} else if (fldName.equalsIgnoreCase("bus3")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "公車站,";
						}
					} else if (fldName.equalsIgnoreCase("bus4")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "公車站,";
						}
					} else if (fldName.equalsIgnoreCase("MRT")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "捷運站,";
						}
					} else if (fldName.equalsIgnoreCase("MRT3")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "捷運站,";
						}
					} else if (fldName.equalsIgnoreCase("MRT4")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "捷運站,";
						}
					} else if (fldName.equalsIgnoreCase("train")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "火車站,";
						}
					} else if (fldName.equalsIgnoreCase("train3")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "火車站,";
						}
					} else if (fldName.equalsIgnoreCase("train4")) {
						if (value != null && value.trim().length() != 0) {
							adNearByTransport += value.trim() + "火車站,";
						}
					} else if (fldName.equalsIgnoreCase("adTitle")) {
						if (value.trim().length() != 0) {
							adTitle = value.trim();
						}
					} else if (fldName.equalsIgnoreCase("adDescription")) {
						if (value.trim().length() != 0) {
							try {
								adDetailContent = new SerialClob(value.trim().toCharArray());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (fldName.equalsIgnoreCase("phone")) {
						if (value != null && value.trim().length() != 0) {
							adPhone = value.trim();
						}
					} else if (fldName.equalsIgnoreCase("allowSexOrentSearch")) {
						adCurAllowedSearchbySexOrient = true;
					} else if (fldName.equalsIgnoreCase("phoneAllowAttachAd")) {
						adPhoneAttachToAd = true;
					} else if (fldName.equalsIgnoreCase("emailMax")) {
						emailMax = Integer.parseInt(value.trim());
					} else if (fldName.equalsIgnoreCase("Cpet") && adRentType.equals("3")
							&& !adCurrentPeopleNum.equals("0")) {
						adCurHasPet = Boolean.parseBoolean(value.trim());
					} else if (fldName.equalsIgnoreCase("Csmoke") && adRentType.equals("3")
							&& !adCurrentPeopleNum.equals("0")) {
						adCurSmoke = Boolean.parseBoolean(value.trim());
					} else if (fldName.equalsIgnoreCase("CsexOrient") && adRentType.equals("3")
							&& !adCurrentPeopleNum.equals("0")) {
						adCurSexOrientation = value.trim();
					} else if (fldName.equalsIgnoreCase("Cgender") && adRentType.equals("3")
							&& !adCurrentPeopleNum.equals("0")) {
						adCurGender = value.trim();
					} else if (fldName.equalsIgnoreCase("allowSexOrentSearch") && adRentType.equals("3")
							&& !adCurrentPeopleNum.equals("0")) {
						adCurAllowedSearchbySexOrient = Boolean.parseBoolean(value.trim());
					} else if (fldName.equalsIgnoreCase("Cage") && adRentType.equals("3")
							&& !adCurrentPeopleNum.equals("0")) {
						adCurAge = value.trim().equals("-") ? null : value.trim();
					} else if (fldName.equalsIgnoreCase("Cage2") && adRentType.equals("3")
							&& !adCurrentPeopleNum.equals("0")) {
						adCurAge = value.trim().equals("-") ? adCurAge + "," + null : adCurAge + "," + value.trim();
					} else if (fldName.equalsIgnoreCase("Fsmoke")) {
						adFutureSmoke = Boolean.parseBoolean(value.trim());
					} else if (fldName.equalsIgnoreCase("Fjob")) {
						adFutureJobType = value.trim();
					} else if (fldName.equalsIgnoreCase("Fpet")) {
						adFuturePet = Boolean.parseBoolean(value.trim());
					} else if (fldName.equalsIgnoreCase("Fgender")) {
						adFutureGender = value.trim();
					} else if (fldName.equalsIgnoreCase("Fminage") && adRentType.equals("3")) {
						adFutureAge = value.trim().equals("default") ? null : value.trim();
					} else if (fldName.equalsIgnoreCase("Fmaxage") && adRentType.equals("3")) {
						adFutureAge = value.trim().equals("default") ? adFutureAge + "," + null
								: adFutureAge + "," + value.trim();
					} else if (fldName.equalsIgnoreCase("couple") && adRentType.equals("3")) {
						adFutureCoupleAccept = Boolean.parseBoolean(value.trim());
					}

				}
			} // for loop end

			adBalconyNum = adBalconyNum == null ? "0" : adBalconyNum;
			adLivingRoomNum = adLivingRoomNum == null ? "0" : adLivingRoomNum;
			adToiletNum = adToiletNum == null ? "0" : adToiletNum;
			adAgentFee = adAgentFee == null ? "0" : adAgentFee;
			adPhoneAttachToAd = adPhoneAttachToAd == null ? false : adPhoneAttachToAd;

			// 0:整層出租 3:出租房間(有公共空間) 1:獨立套房 2:分租套房
			if (adRentType.equals("0")) {
				adCurAllowedSearchbySexOrient = adCurAllowedSearchbySexOrient == null ? false
						: adCurAllowedSearchbySexOrient;
				adCook = true;
			}

			if (!adRentType.equals("3") || adCurrentPeopleNum.equals("0")) {
				adFutureAge = null;
				adFutureCoupleAccept = null;
				adCurHasPet = null;
				adCurSmoke = null;
				adCurSexOrientation = null;
				adCurGender = null;
				adCurAge = null;
				adCurAllowedSearchbySexOrient = null;
			} else if (adRentType.equals("3") && !adCurrentPeopleNum.equals("0")) {
				adCurAllowedSearchbySexOrient = adCurAllowedSearchbySexOrient == null ? false
						: adCurAllowedSearchbySexOrient;
			}

			adAreacode = ConvertTableUtil.addressStringToCode(county);
			if (adNearByTransport.trim().length() != 0) {
				adNearByTransport = adNearByTransport.substring(0, adNearByTransport.length() - 1);
			} else {
				adNearByTransport = null;
			}

		} // if bracket end

		if (parts != null) { // 如果這是一個上傳資料的表單
			if (!new File("d:\\images\\roomRentAd").exists()) {
				new File("d:\\images\\roomRentAd").mkdirs();
			}

			for (Part p : parts) {
				if (p.getContentType() != null) {
					String fileName = GlobalService.getFileName(p);
					long sizeInBytes = 0;
					InputStream is = null;
					if (fileName != null && fileName.trim().length() > 0) {
						fileName = System.currentTimeMillis()
								+ GlobalService.getFileName(p).substring(GlobalService.getFileName(p).indexOf("."));
						adImages += fileName + ",";
						sizeInBytes = p.getSize();
						is = p.getInputStream();
						int len = 0;
						byte[] byteArray = new byte[(int) sizeInBytes];
						File uploadFile = new File("d:\\images\\roomRentAd", fileName);
						try (FileOutputStream fos = new FileOutputStream(uploadFile);) {
							while ((len = is.read(byteArray)) != -1) {
								fos.write(byteArray, 0, len);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						adImages = null;
					}
				}
			}
			if (adImages != null && adImages.trim().length() != 0) {
				adImages = adImages.substring(0, adImages.length() - 1);
			}
		}
		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");
		String strAdId = request.getParameter("adId");
		if (strAdId != null) {
			adId = Integer.parseInt(strAdId);
		}
		RoomRentBean roomRentAd = new RoomRentBean(adId, adRentType, adRoomNum, adToiletNum, adBalconyNum,
				adLivingRoomNum, adIdentity, adAgentFee, adCurrentPeopleNum, adHouseType, adAreacode, adDetailaddress,
				adParkingCount, adExtraCost, adHasElevator, adMinimumStayLength, adAvailableDate, adTotalArea,
				adRentPrice, adDeposit, adNearByTransport, adTitle, adDetailContent, adPhone, adPhoneAttachToAd,
				adImages, adCook, adCurHasPet, adCurSmoke, adCurSexOrientation, adCurAllowedSearchbySexOrient,
				adCurGender, adCurAge, adFutureSmoke, adFutureJobType, adFuturePet, adFutureGender, adFutureAge,
				adFutureCoupleAccept, adUpdateDate, adCreateDate, adState, emailMax, sendMail, emailDate, adStyle, mb);

		if (parts != null && adRoomNum != 0) { // 如果這是一個上傳資料的表單
			List<RoomBean> roomList = roomService.getRoomsByFk(roomRentBean);// 得到此則廣告含有幾間房間
//			System.out.println("一則租房廣告有幾間房間:" + roomList.size() + "間"); 
			int i = 1;
			for (Iterator<RoomBean> iterator = roomList.iterator(); iterator.hasNext();) {
				RoomBean rb = (RoomBean) iterator.next();
				Boolean roomState = null;// 有效為true 失效為false
				String roomType = null;
				Boolean balcony = null;
				Boolean duplexApartment = null;
				Boolean wash = null;
				Boolean icebox = null;
				Boolean four = null;
				Boolean gas = null;
				Boolean tv = null;
				Boolean wardrobe = null;
				Boolean sofa = null;
				Boolean heater = null;
				Boolean broadband = null;
				Boolean desk = null;
				Boolean chair = null;
				Boolean singlebed = null;
				Boolean doublebed = null;
				Boolean coldair = null;
				for (Part p : parts) {
					String fldName = p.getName();
					String value = null;
					value = request.getParameter(fldName);
					// 1. 讀取使用者輸入資料
					if (p.getContentType() == null) {
						if (fldName.equalsIgnoreCase("roomState" + i)) {
							roomState = Boolean.parseBoolean(value.trim());
							rb.setRoomState(roomState);
						} else if (fldName.equalsIgnoreCase("roomType" + i)) {
							if (value.trim().equals("套房")) {
								roomType = "0";
							} else {
								roomType = "1";
							}
							rb.setRoomType(roomType);
						} else if (fldName.equalsIgnoreCase("floor" + i)) {
							if (value != null && value.trim().length() != 0) {
								rb.setRentFloor(value.trim());
							} else {
								rb.setRentFloor(null);
							}
						} else if (fldName.equalsIgnoreCase("totalFloor" + i)) {
							if (value != null && value.trim().length() != 0) {
								rb.setRentTotalFloor(value.trim());
							} else {
								rb.setRentTotalFloor(null);
							}
						} else if (fldName.equalsIgnoreCase("area" + i)) {
							if (value != null && value.trim().length() != 0) {
								rb.setArea(value.trim());
							} else {
								rb.setArea(null);
							}
						} else if (fldName.equalsIgnoreCase("roomRentPrice" + i)) {
							if (value != null && value.trim().length() != 0) {
								rb.setRentPrice(Integer.parseInt(value.trim()));
							} else {
								errorMessage.put("rentPrice", "租金欄位不能空白");
							}
						} else if (fldName.equalsIgnoreCase("roomDeposit" + i)) {
							rb.setDeposit(value.trim());
						} else if (fldName.equalsIgnoreCase("canCook" + i)) {
							rb.setAllowCook(Boolean.parseBoolean(value.trim()));
						} else if (fldName.equalsIgnoreCase("balcony" + i)) {
							balcony = Boolean.parseBoolean(value.trim());
							rb.setHasBalcony(balcony);
						} else if (fldName.equalsIgnoreCase("duplexApartment" + i)) {
							duplexApartment = Boolean.parseBoolean(value.trim());
							rb.setHasDuplex(duplexApartment);
						} else if (fldName.equalsIgnoreCase("wash" + i)) {
							wash = Boolean.parseBoolean(value.trim());
							rb.setHasWash(wash);
						} else if (fldName.equalsIgnoreCase("icebox" + i)) {
							icebox = Boolean.parseBoolean(value.trim());
							rb.setHasIceBox(icebox);
						} else if (fldName.equalsIgnoreCase("four" + i)) {
							four = Boolean.parseBoolean(value.trim());
							rb.setHas4(four);
						} else if (fldName.equalsIgnoreCase("gas" + i)) {
							gas = Boolean.parseBoolean(value.trim());
							rb.setHasGas(gas);
						} else if (fldName.equalsIgnoreCase("tv" + i)) {
							tv = Boolean.parseBoolean(value.trim());
							rb.setHasTV(tv);
						} else if (fldName.equalsIgnoreCase("wardrobe" + i)) {
							wardrobe = Boolean.parseBoolean(value.trim());
							rb.setHasWardrobe(wardrobe);
						} else if (fldName.equalsIgnoreCase("sofa" + i)) {
							sofa = Boolean.parseBoolean(value.trim());
							rb.setHasSofa(sofa);
						} else if (fldName.equalsIgnoreCase("heater" + i)) {
							heater = Boolean.parseBoolean(value.trim());
							rb.setHasHeater(heater);
						} else if (fldName.equalsIgnoreCase("broadband" + i)) {
							broadband = Boolean.parseBoolean(value.trim());
							rb.setHasBroadBand(broadband);
						} else if (fldName.equalsIgnoreCase("desk" + i)) {
							desk = Boolean.parseBoolean(value.trim());
							rb.setHasDesk(desk);
						} else if (fldName.equalsIgnoreCase("chair" + i)) {
							chair = Boolean.parseBoolean(value.trim());
							rb.setHasChair(chair);
						} else if (fldName.equalsIgnoreCase("singlebed" + i)) {
							singlebed = Boolean.parseBoolean(value.trim());
							rb.setHasSingleBed(singlebed);
						} else if (fldName.equalsIgnoreCase("doublebed" + i)) {
							doublebed = Boolean.parseBoolean(value.trim());
							rb.setHasDoubleBed(doublebed);
						} else if (fldName.equalsIgnoreCase("coldair" + i)) {
							coldair = Boolean.parseBoolean(value.trim());
							rb.setHasColdAir(coldair);
						}
					} // if bracket end

				} // for loop end
				if (balcony == null) {
					rb.setHasBalcony(false);
				}
				if (duplexApartment == null) {
					rb.setHasDuplex(false);
				}
				if (wash == null) {
					rb.setHasWash(false);
				}
				if (icebox == null) {
					rb.setHasIceBox(false);
				}
				if (four == null) {
					rb.setHas4(false);
				}
				if (gas == null) {
					rb.setHasGas(false);
				}
				if (tv == null) {
					rb.setHasTV(false);
				}
				if (wardrobe == null) {
					rb.setHasWardrobe(false);
				}
				if (sofa == null) {
					rb.setHasSofa(false);
				}
				if (heater == null) {
					rb.setHasHeater(false);
				}
				if (broadband == null) {
					rb.setHasBroadBand(false);
				}
				if (desk == null) {
					rb.setHasDesk(false);
				}
				if (chair == null) {
					rb.setHasChair(false);
				}
				if (singlebed == null) {
					rb.setHasSingleBed(false);
				}
				if (doublebed == null) {
					rb.setHasDoubleBed(false);
				}
				if (coldair == null) {
					rb.setHasColdAir(false);
				}
				if (roomState == null) {
					rb.setRoomState(true);
				}
				roomService.updateRoom(rb);// 修改房間
				i++;
			} // for loop end
		} // if loop end

		if (!errorMessage.isEmpty()) {
			return "_4u4u_PostAd/UpdateRoomRentAd";
		}

		try {
//			RoomRentService service = new RoomRentServiceImpl();
//			MemberService memberService = new MemberServiceImpl();

			///////////// 判斷會員狀態可否發文 ///////////////////
			///////////// 失效廣告也能修改故不判斷 ///////////////////
			String memberState = memberService.memberState(mb);
//			boolean isExistEffectiveAd = service.isExistEffectiveAd(mb);
			if (!memberState.equals("1") && !memberState.equals("2")) {
				throw new CantCreateAdException("不是一般會員也不是VIP會員");
			}
//			else if(isExistEffectiveAd && memberState.equals("1")) {
//				throw new CantCreateAdException("已達到發佈廣告上限，請升級成VIP享有發佈多則廣告的功能");
//			}
			Integer iAdStyle = null;
			if (adStyle == false) {
				iAdStyle = 0;
			}
			String strMemId = request.getParameter("adId");
			Integer memId = null;
			if (strMemId != null) {
				memId = Integer.parseInt(strMemId);
			}
//			String strPageNo = request.getParameter("pageNo");
//			Integer pageNo = null;
//			if(strPageNo == null) {
//				pageNo = 1;
//			}
//			if(strPageNo != null) {
//				pageNo = Integer.parseInt(strPageNo);
//			}
			/////////////////////// 修改廣告///////////////////////
			int n = roomRentService.updateRoomRentAd(roomRentAd);
			emailService.sendMatchEmail(roomRentAd); // 寄出符合信

			////////////////// 印出符合的信件 ////////////////////
			List<WantedRoomBean> list = roomRentService.findingMatchWantedRoomAd(roomRentAd);
			for (WantedRoomBean wantedRoomBean : list) {
				System.err.println("找到以下符合廣告的信件");
				System.err.println(
						"廣告編號為： " + wantedRoomBean.getFindRoomId() + "   廣告標題為： " + wantedRoomBean.getAdTitle());
			}
			////////////////////////////////////////////////////////
			if (n == 1) {
				msgOK.put("UpdateOK", "<Font color='red'>修改成功</Font>");
//				response.sendRedirect(response.encodeRedirectURL("/4u4u/_4u4u/MyAdServlet?memId="+memId+"&adStyle="+iAdStyle+"&adId="+adId));
				return "redirect:/_4u4u/MyAdServlet?memId=" + memId + "&adStyle=" + iAdStyle + "&adId=" + adId;
			} else {
				errorMessage.put("errorUpdate", "修改此筆資料有誤(ProcessRoomRentAd)");
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage.put("exception", e.getMessage());
			// 產生 RequestDispatcher 物件 rd
//			RequestDispatcher rd = request.getRequestDispatcher("/_4u4u_PostAd/UpdateRoomRentAd.jsp");
			// 請容器代為呼叫下一棒程式
//			rd.forward(request, response);
			return "/_4u4u_PostAd/UpdateRoomRentAd";
		}
		if (!errorMessage.isEmpty()) {
//			RequestDispatcher rd = request.getRequestDispatcher("/_4u4u_PostAd/UpdateRoomRentAd.jsp");
//			rd.forward(request,response);
			return "/_4u4u_PostAd/UpdateRoomRentAd";
		}
		return "redirect:/";
	}

	@RequestMapping(value = { "/_4u4u/deleteFindRoomAd", "/_4u4u/deleteRoomRentAd" }, method = RequestMethod.GET)
	public String DeleteAd(Model model, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String memId = request.getParameter("memId").trim();
		// String memState = request.getParameter("state").trim();
		String strAdStyle = request.getParameter("adStyle").trim();
		Integer adStyle = null;
		if (strAdStyle != null) {
			adStyle = Integer.parseInt(strAdStyle);
		}
		String strAdId = request.getParameter("adId").trim();
		Integer adId = null;
		int n = 0;
		if (strAdId != null) {
			try {
				adId = Integer.parseInt(strAdId);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		// 如果adStyle = 1 =>找房廣告 adStyle = 0 =>租房廣告
		if (adStyle == 1) {
			// WantedRoomService service = new WantedRoomServiceImpl();
			n = wantedRoomService.deleteFindRoomAd(adId);
		} else {
			// RoomRentService service = new RoomRentServiceImpl();
			n = roomRentService.deleteRoomRentAd(adId);
		}
		if (n == 1) {
			session.setAttribute("AdDeleteMsg", "<Font color='red'>刪除成功</Font>");
		} else {
			session.setAttribute("AdDeleteMsg", "<Font color='red'>刪除失敗</Font>");
		}
		// response.sendRedirect(
//	    response.encodeRedirectURL("/4u4u/_4u4u/MyAdServlet?memId="+memId+"&adStyle="+adStyle+"&adId="+adId));
		// response.sendRedirect(response.encodeRedirectURL("/4u4u/_4u4u/account.jsp?memId="+memId));
		return "redirect:/_4u4u/MyAdServlet?memId=" + memId + "&adStyle=" + adStyle + "&adId=" + adId;
	}

	@RequestMapping(value = { "/_4u4u/ProcessUpdateWantedRoomAd",
			"/_4u4u/ProcessUpdateRoomRentAd" }, method = RequestMethod.GET)
	public String ProcessUpdateAd(Model model, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		if (session == null) {
//	response.sendRedirect(request.getContextPath() + "/index.jsp");
			return "redirect:/index";
		}
		String adStyle = request.getParameter("adStyle").trim();
		String strAdId = request.getParameter("adId").trim();
		Integer adId = null;
		if (strAdId != null) {
			adId = Integer.parseInt(strAdId);
		}
		model.addAttribute("adId", adId);
		WantedRoomBean wrb = null;
		RoomRentBean rrb = null;
		if (adStyle.equals("0")) {
//	RoomRentService roomRentService = new RoomRentServiceImpl();
			rrb = roomRentService.getAdById(adId);
		} else {
//	WantedRoomService wantedRoomService = new WantedRoomServiceImpl();
			wrb = wantedRoomService.getAdById(adId);
		}
		model.addAttribute("adStyle", adStyle);

		if (wrb != null) {
			session.setAttribute("findRoomAd", wrb); // findRoomAd

			// 幾間套房幾間雅房
//	if (wrb.getSuiteQuantity() == 0 && wrb.getRoomQuantity() != 0) {
//		model.addAttribute("wantedRoom", "想要" + wrb.getRoomQuantity() + "雅房");
//	} else if (wrb.getRoomQuantity() == 0 && wrb.getSuiteQuantity() != 0) {
//		model.addAttribute("wantedRoom", "想要" + wrb.getSuiteQuantity() + "套房");
//	} else {
//		model.addAttribute("wantedRoom", "想要" + wrb.getSuiteQuantity() + "套房" + wrb.getRoomQuantity() + "雅房");
//
//	}
			Integer suiteQuantity = wrb.getSuiteQuantity();
			model.addAttribute("suiteQuantity", suiteQuantity);
			Integer roomQuantity = wrb.getRoomQuantity();
			model.addAttribute("roomQuantity", roomQuantity);
			// 性別人數
			if (wrb.getPeopleNumGender() != null) {
				String peopleGenderNum = wrb.getPeopleNumGender();
				model.addAttribute("peopleNumGender", peopleGenderNum);
			}
			// 合租意願
			if (wrb.getAgreeShare() != null) {
				Boolean agreeShare = wrb.getAgreeShare();
				model.addAttribute("agreeShare", agreeShare);
			}
			// 想要居住地區
			// looking in
			if (wrb.getAreaCode() != null && wrb.getAreaCode().length() != 0) {
				String[] strArray = wrb.getAreaCode().trim().split(",");
				String lookInArea = "";
				for (String str : strArray) {
					lookInArea += ConvertTableUtil.addressCodeToString(str) + ",";
				}
				lookInArea = lookInArea.substring(0, lookInArea.length() - 1);
				model.addAttribute("lookInArea", lookInArea);
			}
			// 租金預算
			if (wrb.getBudget() != null) {
				Integer budget = wrb.getBudget();
				model.addAttribute("budget", budget);
			}
			// checkInDate
			if (wrb.getCheckInDate() != null) {
				Date date = new Date(wrb.getCheckInDate().getTime());
				model.addAttribute("checkInDate", date);
			}
			// liveTime
			if (wrb.getLiveTime() != null) {
				String liveTime = ConvertTableUtil.minimumStayCodeToString(wrb.getLiveTime());
				model.addAttribute("liveTime", liveTime);
			}
			// hasWashMachine
			if (wrb.getHasWashMachine() != null) {
				Boolean washMachine = wrb.getHasWashMachine();
				model.addAttribute("washMachine", washMachine);
			}
			// hasRefrigerator
			if (wrb.getHasRefrigerator() != null) {
				Boolean refrigerator = wrb.getHasRefrigerator();
				model.addAttribute("refrigerator", refrigerator);
			}
			// hasTV
			if (wrb.getHasTV() != null) {
				Boolean tv = wrb.getHasTV();
				model.addAttribute("TV", tv);
			}
			// hasAirConditioning
			if (wrb.getHasAirConditioning() != null) {
				Boolean airConditioning = wrb.getHasAirConditioning();
				model.addAttribute("airConditioning", airConditioning);
			}
			// hasWaterHeater
			if (wrb.getHasWaterHeater() != null) {
				Boolean waterHeater = wrb.getHasWaterHeater();
				model.addAttribute("waterHeater", waterHeater);
			}
			// hasInternet
			if (wrb.getHasInternet() != null) {
				Boolean internet = wrb.getHasInternet();
				model.addAttribute("internet", internet);
			}
			// hasFourthTV
			if (wrb.getHasFourthTV() != null) {
				Boolean fourthTV = wrb.getHasFourthTV();
				model.addAttribute("fourthTV", fourthTV);
			}
			// hasGas
			if (wrb.getHasGas() != null) {
				Boolean gas = wrb.getHasGas();
				model.addAttribute("gas", gas);
			}
			// hasWardrobe
			if (wrb.getHasWardrobe() != null) {
				Boolean wardrobe = wrb.getHasWardrobe();
				model.addAttribute("wardrobe", wardrobe);
			}
			// hasSofa
			if (wrb.getHasSofa() != null) {
				Boolean sofa = wrb.getHasSofa();
				model.addAttribute("sofa", sofa);
			}
			// hasTable
			if (wrb.getHasTable() != null) {
				Boolean table = wrb.getHasTable();
				model.addAttribute("table", table);
			}
			// hasChair
			if (wrb.getHasChair() != null) {
				Boolean chair = wrb.getHasChair();
				model.addAttribute("chair", chair);
			}
			// hasParking
			if (wrb.getHasParking() != null) {
				Boolean parking = wrb.getHasParking();
				model.addAttribute("parking", parking);
			}
			// hasBalcony
			if (wrb.getHasBalcony() != null) {
				Boolean balcony = wrb.getHasBalcony();
				model.addAttribute("balcony", balcony);
			}
			// hasSingleBed
			if (wrb.getHasSingleBed() != null) {
				Boolean singleBed = wrb.getHasSingleBed();
				model.addAttribute("singleBed", singleBed);
			}
			// hasDoubleBed
			if (wrb.getHasDoubleBed() != null) {
				Boolean doubleBed = wrb.getHasDoubleBed();
				model.addAttribute("doubleBed", doubleBed);
			}
			// allowCook
			Boolean cook = wrb.getHasDoubleBed();
			model.addAttribute("cook", cook);
			// age
			if (wrb.getAge() != null) {
				if (wrb.getAge().contains(",") == true) {
					String[] ages = wrb.getAge().split(",");
					if (ages.length >= 2) {
						String ageMin = ages[0];
						model.addAttribute("ageMin", ageMin);
						String ageMax = ages[1];
						model.addAttribute("ageMax", ageMax);
					} else if (ages.length > 0) {
						model.addAttribute("ageMin", ages[0]);
						model.addAttribute("ageMax", null);
					} else {
						model.addAttribute("ageMin", null);
						model.addAttribute("ageMax", null);
					}
				} else {
					String age = wrb.getAge();
					model.addAttribute("age", age);
				}
			}
			// job
			String job = ConvertTableUtil.jobCodeToString(wrb.getJob());
			model.addAttribute("job", job);
			// allowSmoke
			Boolean smoke = wrb.getAllowSmoke();
			model.addAttribute("smoke", smoke);
			// allowPet
			Boolean pet = wrb.getAllowPet();
			model.addAttribute("pet", pet);
			// sexOrientation
			if (wrb.getAgreeAdCondition()) {
				String sexOrient = ConvertTableUtil.sexOrientCodeToString(wrb.getSexualOrientation());
				model.addAttribute("sexOrient", sexOrient);
			}
			// agreeAdCondition
			if (wrb.getAgreeAdCondition() != null) {
				Boolean agreeAdCondition = wrb.getAgreeAdCondition();
				model.addAttribute("agreeAdCondition", agreeAdCondition);
			}
			// 室友偏好
			// flatmateGender
			String gender = ConvertTableUtil.genderCodeToString(wrb.getWantedRoommatesGender());
			model.addAttribute("flatmateGender", gender);
			// flatmateAge
			if (wrb.getWantedRoommatesAge() != null && wrb.getWantedRoommatesAge().trim().length() != 0) {
				String roomMateAge = null;
				if (wrb.getWantedRoommatesAge().contains(",")) {

					roomMateAge = wrb.getWantedRoommatesAge().trim().replace(",", " to ");
				} else {
					roomMateAge = wrb.getWantedRoommatesAge().trim();
				}
				model.addAttribute("roomMateAge", roomMateAge);
			}
			// flatmateJob
			String flatmateJob = ConvertTableUtil.jobCodeToString(wrb.getWantedRoommatesJob());
			model.addAttribute("flatmateJob", flatmateJob);
			// flatmateSmoke
			Boolean flatmateSmoke = wrb.getWantedRoommatesSmoke();
			model.addAttribute("flatmateSmoke", flatmateSmoke);
			// flatmatePet
			Boolean flatmatePet = wrb.getWantedRoommatesPet();
			model.addAttribute("flatmatePet", flatmatePet);
			// flatmatesexOrientation
			String flatmateSexOrient = ConvertTableUtil.sexOrientCodeToString(wrb.getWantedRoommatesSex());
			model.addAttribute("flatmateSexOrient", flatmateSexOrient);
			// 廣告細節
			// adTitle
			String adTitle = wrb.getAdTitle();
			model.addAttribute("adTitle", adTitle);
			// adDetailContent
			Clob clob = wrb.getAdDescription();
			if (clob != null) {
				try (Reader r = wrb.getAdDescription().getCharacterStream();) {
					StringBuffer buffer = new StringBuffer();
					int ch, count = 0;
					while ((ch = r.read()) != -1) {
						buffer.append("" + (char) ch);
						count++;
					}
					model.addAttribute("adDescription", buffer.toString());
					if (count > 45) {
						model.addAttribute("needReadMore", "yes");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// phone
			if (wrb.getPhone() != null) {
				String phone = wrb.getPhone();
				model.addAttribute("phone", phone);
			}
			// image
			String[] imageFileNames = null;
			if (wrb.getImages() != null && wrb.getImages().trim().length() != 0) {
				imageFileNames = wrb.getImages().trim().split(",");
			}
			if (imageFileNames != null) {
				model.addAttribute("images", imageFileNames);

			}
			// emailMax
			if (wrb.getEmailMax() != null) {
				Integer emailMax = wrb.getEmailMax();
				model.addAttribute("emailMax", emailMax);
			}

		}
		if (rrb != null) {
			session.setAttribute("RoomRentAd", rrb); // RoomRentAd
			// adRentType
			String adRentType = ConvertTableUtil.rentCodeToString(rrb.getAdRentType());
			model.addAttribute("adRentType", adRentType);
			// adRoomNum
			Integer adRoomNum = rrb.getAdRoomNum();
			model.addAttribute("adRoomNum", adRoomNum);
			// adToiletNum
			String adToiletNum = rrb.getAdToiletNum();
			model.addAttribute("adToiletNum", adToiletNum);
			// adBalconyNum
			String adBalconyNum = rrb.getAdBalconyNum();
			model.addAttribute("adBalconyNum", adBalconyNum);
			// adLivingRoomNum
			String adLivingRoomNum = rrb.getAdLivingRoomNum();
			model.addAttribute("adLivingRoomNum", adLivingRoomNum);
			// adOwner 廣告者身分
			// identity
			String adOwner = ConvertTableUtil.identityCodeToString(rrb.getAdIdentity().trim());
			model.addAttribute("adOwner", adOwner);
			// AgentFee 仲介費
			if (rrb.getAdIdentity().trim().equals("3")) {
				if (rrb.getAdAgentFee() != null && !rrb.getAdAgentFee().trim().equals("0")) {
					model.addAttribute("agentFee", rrb.getAdAgentFee());
				}
			}
			// adCurrentPeopleNum
			String adCurrentPeopleNum = rrb.getAdCurrentPeopleNum();
			model.addAttribute("adCurrentPeopleNum", adCurrentPeopleNum);
			// adHouseType住宅種類
			if (rrb.getAdHouseType() != null) {
				String adHouseType = ConvertTableUtil.houseCodeToString(rrb.getAdHouseType());
				model.addAttribute("adHouseType", adHouseType);
			}
			// areaCode
			if (rrb.getAdAreacode() != null && rrb.getAdAreacode().trim().length() != 0) {

				String area = ConvertTableUtil.addressCodeToString(rrb.getAdAreacode());
				model.addAttribute("area", area);

				String detailAddr = rrb.getAdDetailaddress();
//		String strStart = detailAddr.substring(0, 6);
				String strFinal = detailAddr.substring(area.length());

//		 String query = String.format("%s",URLEncoder.encode(detailAddr,"UTF-8"));
//		 System.out.println(detailAddr);
				model.addAttribute("detailAddr", strFinal);
			}
			// adTotalArea 總坪數
			String adTotalArea = rrb.getAdTotalArea();
			model.addAttribute("adTotalArea", adTotalArea);
			// adParkingCount
			String adParkingCount = rrb.getAdParkingCount();
			model.addAttribute("adParkingCount", adParkingCount);
			// adHasElevator
			Boolean adHasElevator = rrb.getAdHasElevator();
			model.addAttribute("adHasElevator", adHasElevator);
			// adExtraCost
			if (rrb.getAdExtraCost() != null) {
				String adExtraCost = rrb.getAdExtraCost();
				model.addAttribute("adExtraCost", adExtraCost);
			}
			// adTotalRentPrice
			if(adRentType == "整層住家") {
				if(rrb.getAdRentPrice() != null) {
					Integer adRentPrice = rrb.getAdRentPrice();
					model.addAttribute("adRentPrice", adRentPrice);
				}				
			}
			// adMinimumStayLength
			String adMinimumStayLength = rrb.getAdMinimumStayLength();
			model.addAttribute("adMinimumStayLength", adMinimumStayLength);
			// adAvailableDate checkInDate
			if (rrb.getAdAvailableDate() != null) {
				Date date = new Date(rrb.getAdAvailableDate().getTime());
				model.addAttribute("checkInDate", date);
			}

			// 室友偏好
			String Fjob = ConvertTableUtil.jobCodeToString(rrb.getAdFutureJobType());
			model.addAttribute("Fjob", Fjob);
			// flatmateGender
			String gender = ConvertTableUtil.genderCodeToString(rrb.getAdFutureGender());
			model.addAttribute("flatmateGender", gender);

			// image
			String[] imageFileNames = null;
			if (rrb.getAdImages() != null && rrb.getAdImages().trim().length() != 0) {
				imageFileNames = rrb.getAdImages().trim().split(",");
			}
			model.addAttribute("images", imageFileNames);

			if (rrb.getAdFutureAge() != null) {
				String[] age = rrb.getAdFutureAge().split(",");
				if (age.length >= 2) {
					String ageMin = age[0];
					model.addAttribute("ageMin", ageMin);
					String ageMax = age[1];
					model.addAttribute("ageMax", ageMax);
				} else {
					String age1 = rrb.getAdFutureAge();
					model.addAttribute("ageMin", age1);
					model.addAttribute("ageMax", null);
				}
			}

			String adTitle = rrb.getAdTitle();
			model.addAttribute("adTitle", adTitle);
			// adDescription
			Clob clob = rrb.getAdDetailContent();
			if (clob != null) {
				try (Reader r = rrb.getAdDetailContent().getCharacterStream();) {
					StringBuffer buffer = new StringBuffer();
					int ch, count = 0;
					while ((ch = r.read()) != -1) {
						buffer.append("" + (char) ch);
						count++;
					}
					model.addAttribute("adDescription", buffer.toString());
					if (count > 45) {
						model.addAttribute("needReadMore", "yes");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// phone
			String phone = rrb.getAdPhone();
			model.addAttribute("phone", phone);
			// acceptCouple
			Boolean acceptCouple = rrb.getAdFutureCoupleAccept();
//	System.out.println("是否接受情侶"+acceptCouple);
			model.addAttribute("acceptCouple", acceptCouple);
			// Cpet
			Boolean cPet = rrb.getAdCurHasPet();
			model.addAttribute("Cpet", cPet);
			// Csmoke
			Boolean cSmoke = rrb.getAdCurSmoke();
			model.addAttribute("CSmoke", cSmoke);
			// allowSexOrentSearch
			Boolean allowSexOrentSearch = rrb.getAdCurAllowedSearchbySexOrient();
			model.addAttribute("allowSexOrentSearch", allowSexOrentSearch);
//	取出房間資訊 一個廣告可有多個房間 裝room的list
			Set<RoomBean> roomSet = rrb.getRoomItems();
			List<String> roomTypeList = new ArrayList<>();
			List<Boolean> roomStateList = new ArrayList<>();
			List<Boolean> balconyList = new ArrayList<>();
			List<Boolean> duplexList = new ArrayList<>();
			List<Boolean> washList = new ArrayList<>();
			List<Boolean> iceboxList = new ArrayList<>();
			List<Boolean> fourList = new ArrayList<>();
			List<Boolean> gasList = new ArrayList<>();
			List<Boolean> tvList = new ArrayList<>();
			List<Boolean> wardrobeList = new ArrayList<>();
			List<Boolean> sofaList = new ArrayList<>();
			List<Boolean> heaterList = new ArrayList<>();
			List<Boolean> broadbandList = new ArrayList<>();
			List<Boolean> deskList = new ArrayList<>();
			List<Boolean> chairList = new ArrayList<>();
			List<Boolean> singlebedList = new ArrayList<>();
			List<Boolean> doublebedList = new ArrayList<>();
			List<Boolean> coldairList = new ArrayList<>();
			for (Iterator<RoomBean> iterator = roomSet.iterator(); iterator.hasNext();) {
				RoomBean rb = (RoomBean) iterator.next();

				String roomType = ConvertTableUtil.roomCodeToString(rb.getRoomType());
				roomTypeList.add(roomType);

				Boolean roomState = rb.getRoomState();
				roomStateList.add(roomState);

				Boolean balcony = rb.getHasBalcony();
				balconyList.add(balcony);

				Boolean duplex = rb.getHasDuplex();
				duplexList.add(duplex);

				Boolean wash = rb.getHasWash();
				washList.add(wash);

				Boolean icebox = rb.getHasIceBox();
				iceboxList.add(icebox);

				Boolean four = rb.getHas4();
				fourList.add(four);

				Boolean gas = rb.getHasGas();
				gasList.add(gas);

				Boolean tv = rb.getHasTV();
				tvList.add(tv);

				Boolean wardrobe = rb.getHasWardrobe();
				wardrobeList.add(wardrobe);

				Boolean sofa = rb.getHasSofa();
				sofaList.add(sofa);

				Boolean heater = rb.getHasHeater();
				heaterList.add(heater);

				Boolean broadband = rb.getHasBroadBand();
				broadbandList.add(broadband);

				Boolean desk = rb.getHasDesk();
				deskList.add(desk);

				Boolean chair = rb.getHasChair();
				chairList.add(chair);

				Boolean singlebed = rb.getHasSingleBed();
				singlebedList.add(singlebed);

				Boolean doublebed = rb.getHasDoubleBed();
				doublebedList.add(doublebed);

				Boolean coldair = rb.getHasColdAir();
				coldairList.add(coldair);
			}
			model.addAttribute("roomType", roomTypeList);
			model.addAttribute("roomState", roomStateList);
			model.addAttribute("balcony", balconyList);
			model.addAttribute("duplex", duplexList);
			model.addAttribute("wash", washList);
			model.addAttribute("icebox", iceboxList);
			model.addAttribute("four", fourList);
			model.addAttribute("gas", gasList);
			model.addAttribute("tv", tvList);
			model.addAttribute("wardrobe", wardrobeList);
			model.addAttribute("sofa", sofaList);
			model.addAttribute("heater", heaterList);
			model.addAttribute("broadband", broadbandList);
			model.addAttribute("desk", deskList);
			model.addAttribute("chair", chairList);
			model.addAttribute("singlebed", singlebedList);
			model.addAttribute("doublebed", doublebedList);
			model.addAttribute("coldair", coldairList);
		}

		if (adStyle.equals("0")) {
//	RequestDispatcher rd = request.getRequestDispatcher("/_4u4u_PostAd/UpdateRoomRentAd.jsp");
//	rd.forward(request, response);
			return "/_4u4u_PostAd/UpdateRoomRentAd";
		} else {
//	RequestDispatcher rd = request.getRequestDispatcher("/_4u4u_PostAd/UpdateWantedRoomAd.jsp");
//	rd.forward(request, response);
			return "/_4u4u_PostAd/UpdateWantedRoomAd";
		}
	}

	@RequestMapping(value = { "/_4u4u/ChangeFindRoomAdState",
			"/_4u4u/ChangeRoomRentAdState" }, method = RequestMethod.GET)
	public String ChangeAdState(Model model, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String errorMsg = null;
		String strAdStyle = request.getParameter("adStyle").trim();
		Integer adStyle = null;
		if (strAdStyle != null) {
			adStyle = Integer.parseInt(strAdStyle);
		}
		String strAdId = request.getParameter("adId").trim();
		Integer adId = null;
		Boolean RoomRentAdState = null;
//		Boolean WantedRoomAdState = null;
		if (strAdId != null) {
			adId = Integer.parseInt(strAdId);
		}
		String strMemId = request.getParameter("memId").trim();
		Integer memId = null;
		if (strMemId != null) {
			memId = Integer.parseInt(strMemId);
		}
//		判斷adStyle 是0還1 0為租房廣告 1為找房廣告
		if (adStyle == 0) {
//			依主鍵得到會員資料 找房廣告要判斷會員的狀態
//			MemberService mereberService = new MemberServiceImpl();
			MemberBean memberBean = memberService.queryMemberById(memId);
//			RoomRentService service = new RoomRentServiceImpl();
			RoomRentBean roomRentBean = roomRentService.getAdById(adId);
			RoomRentAdState = roomRentBean.getAdState();
			if (RoomRentAdState == false) {
				Boolean hasExistEffectiveAd = roomRentService.isExistEffectiveAd(memberBean);
//				System.out.println(hasExistEffectiveAd);
//				System.out.println(memberBean.getState());
				if (hasExistEffectiveAd == true && memberBean.getState().equals("1")) {
					errorMsg = "<Font color='red'>已經有1則或是1則以上的租房廣告存在</Font>";
					session.setAttribute("errorMsg", errorMsg);
				}
			}
//			System.out.println("修改前的租房廣告的State=" + RoomRentAdState);
			roomRentService.changeAdState(roomRentBean, memberBean);
//			RoomRentAdState = roomRentBean.getAdState();
//			System.out.println("修改後的租房廣告的State=" + RoomRentAdState);
		} else if (adStyle == 1) {
//			WantedRoomService service = new WantedRoomServiceImpl();
			WantedRoomBean wantedRoomBean = wantedRoomService.getAdById(adId);
//			WantedRoomAdState = wantedRoomBean.getAdState();
//			System.out.println("修改前的找房廣告的AdState=" + WantedRoomAdState);			;			
			wantedRoomService.changeAdState(wantedRoomBean);
//			WantedRoomAdState = wantedRoomBean.getAdState();
//			System.out.println("修改後的找房廣告的AdState=" + WantedRoomAdState);
		}
//		判斷adStyle 是0還1 0為租房廣告 1為找房廣告 選擇跳轉頁面
		if (adStyle == 1) {
//			RequestDispatcher rd = request.getRequestDispatcher("/_4u4u_Account/myFindRoomAd.jsp");
//			rd.forward(request, response);
			return "redirect:/_4u4u/MyAdServlet?memId=" + memId + "&adStyle=" + adStyle + "&adId=" + adId;
		} else {
//			RequestDispatcher rd = request.getRequestDispatcher("/_4u4u_Account/myRoomRentAd.jsp");
//			rd.forward(request, response);
			return "redirect:/_4u4u/MyAdServlet?memId" + memId + "&adStyle=" + adStyle + "&adId=" + adId;
		}

	}

	@RequestMapping(value = "/_4u4u/MyAdServlet", method = RequestMethod.GET)
	public String ProcessMyAd(Model model, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("UTF-8");
		// 先取出session物件
		HttpSession session = request.getSession(false);
		// 紀錄目前請求的RequestURI,以便使用者登入成功後能夠回到原本的畫面
		String requestURI = request.getRequestURI();
		// System.out.println("requestURI=" + requestURI);
		// 如果session物件不存在
		if (session == null || session.isNew()) {
			// 請使用者登入
//			response.sendRedirect(response.encodeRedirectURL("/4u4u/_4u4u_Login/login.jsp"));
			return "redirect:/login";
		}
		session.setAttribute("requestURI", requestURI);
		// 此時session物件存在，讀取session物件內的LoginOK
		// 以檢查使用者是否登入。
		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");
		if (mb == null) {
//			response.sendRedirect(response.encodeRedirectURL("/4u4u/_4u4u_Login/login.jsp"));
			return "redirect:/login";
		}
//		頁碼
		String pageNoStr = request.getParameter("pageNo");
		if (pageNoStr == null) {
			pageNo = 1;
		} else {
			try {
				pageNo = Integer.parseInt(pageNoStr.trim());
			} catch (NumberFormatException e) {
				pageNo = 1;
			}
		}
		String strAdStyle = request.getParameter("adStyle");
//		預設的adStyle = 0 以租房廣告優先
		Integer adStyle = 0;
		if (strAdStyle != null) {
			adStyle = Integer.parseInt(strAdStyle);
		}
		model.addAttribute("adStyle", adStyle);

//		判斷adStyle == 0 or 1 0=>租房廣告 1=>找房廣告
		if (adStyle == 1) {
//			WantedRoomService service = new WantedRoomServiceImpl();
//			依照會員id查會員自己發的找房廣告
//			model.addAttribute("service", service);
			List<WantedRoomBean> wantedRoomAds = wantedRoomService.getPageAdByFk(pageNo, mb);
//			將處理好的 找房廣告放入list裡面
			List<WantedRoomBean> list = new ArrayList<>();
//			放入想要幾間套雅房
			List<String> wantedRoom = new ArrayList<>();
//			存放 廣告敘述的list
			List<String> adDetailList = new ArrayList<>();
//			存放 工作job的list
			List<String> jobList = new ArrayList<>();
//			存放 廣告顯示早鳥 或直接聯繫的list
			List<String> adStateList = new ArrayList<>();
//			存放 圖片的list

			for (Iterator<WantedRoomBean> iterator = wantedRoomAds.iterator(); iterator.hasNext();) {
				WantedRoomBean wrb = (WantedRoomBean) iterator.next();
//				廣告標題 直接取出
//				String adTitle = wrb.getAdTitle();
//				租金預算 直接取出
//				Integer budget = wrb.getBudget();
//				性別人數 直接取出
//				String peopleNumGender = wrb.getPeopleNumGender();
//				model.addAttribute("peopleNumGender", peopleNumGender);

//				判斷會員狀態(一班會員的話)顯示廣告是否為早鳥還是直接聯繫 早鳥在7天後也要變 可直接聯繫									
				if (mb.getState().equals("1")) {
					// 現在時間
					Long nowTime = System.currentTimeMillis();
					// 廣告創建時間
					Long adCreateTime = wrb.getAdCreateDate().getTime();
					// 相減得到的毫秒數/1000*60*60*24 得到天數 大於7天顯示可直接聯繫
					Long adDay = (nowTime - adCreateTime) / (1000 * 60 * 60 * 24);
//					System.out.println("adDay=" + adDay);
					if (adDay >= 7) {
						String adState = "可直接聯繫";
						adStateList.add(adState);
					} else {
						String adState = "早鳥";
						adStateList.add(adState);
					}
				} else {
					String adState = "可直接聯繫";
					adStateList.add(adState);
				}

//				工作職業
				String job = ConvertTableUtil.jobCodeToString(wrb.getJob());
				jobList.add(job);

//				年齡				
				if (wrb.getAge() != null && wrb.getAge().trim().length() != 0) {
					String[] ageArray = wrb.getAge().trim().split(",");
					if (ageArray.length == 2) {
						if (ageArray[0].contentEquals("null") || ageArray[1].contentEquals("null")) {
							;
						} else {
							String age = wrb.getAge().trim().replace(",", " - ");
							wrb.setAge(age);
//							model.addAttribute("age", age);
						}
					} else {
						wrb.setAge(wrb.getAge().trim());
//						model.addAttribute("age", wrb.getAge().trim());
					}

				}

//				圖片 只取第一張出來顯示
				String[] imageFileNames = null;
				String imageFileName = null;

				if (wrb.getImages() != null && wrb.getImages().trim().length() != 0) {
					imageFileNames = wrb.getImages().trim().split(",");
					imageFileName = imageFileNames[0];
//					model.addAttribute("adImages", imageFileName);
//					存放 圖片檔名的屬性物件
					wrb.setImages(imageFileName);

				} else {
					wrb.setImages(null);
				}

				if (wrb.getSuiteQuantity() == 0 && wrb.getRoomQuantity() != 0) {
					wantedRoom.add(wrb.getRoomQuantity() + "雅房");//
				} else if (wrb.getRoomQuantity() == 0 && wrb.getSuiteQuantity() != 0) {
					wantedRoom.add(wrb.getSuiteQuantity() + "套房");//
				} else {
					wantedRoom.add(wrb.getSuiteQuantity() + "套房" + wrb.getRoomQuantity() + "雅房");
				}
				// adDetailContent
				Clob clob = wrb.getAdDescription();
				if (clob != null) {
					try (Reader r = wrb.getAdDescription().getCharacterStream();) {
						StringBuffer buffer = new StringBuffer();
						int ch, count = 0;
						while ((ch = r.read()) != -1) {
							buffer.append("" + (char) ch);
							count++;
						}
						adDetailList.add(buffer.toString());
						if (count > 45) {
							model.addAttribute("needReadMore", "yes");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				// looking in
				if (wrb.getAreaCode() != null && wrb.getAreaCode().length() != 0) {
					String[] strArray = wrb.getAreaCode().trim().split(",");
					String lookInArea = "";
					for (String str : strArray) {
						lookInArea += ConvertTableUtil.addressCodeToString(str) + ",";
					}
					lookInArea = lookInArea.substring(0, lookInArea.length() - 1);
					wrb.setAreaCode(lookInArea);
//					model.addAttribute("lookInArea", lookInArea);
				}
				list.add(wrb);
			}
//			存放 頁面的屬性物件
			model.addAttribute("pageNo", pageNo);
//			存放 總頁數 屬性物件
			model.addAttribute("totalPages", wantedRoomService.getMyAdTotalPagesByFk(mb));
//			存放 想要幾間套雅房的 屬性物件
			model.addAttribute("wantedRoom", wantedRoom);
//			存放 廣告詳細敘述的 屬性物件
			model.addAttribute("adDetail", adDetailList);
//			存放 工作職業的屬性物件
			model.addAttribute("job", jobList);
//			存放 租房廣告的屬性物件
			model.addAttribute("wantedRoomAd", list);
//			存放 廣告顯示早鳥或是直接聯繫的屬性物件
			model.addAttribute("adState", adStateList);
		} else {
//			依照會員id查會員自己發的租房廣告
			List<RoomRentBean> RoomRentAds = roomRentService.getPageAdByFk(pageNo, mb);
//			List<RoomRentBean> RoomRentAds = service.getAllRoomRentAd();
			// 將select到的物件用for迴圈處理放到list裡面
			List<RoomRentBean> list = new ArrayList<>();
			// 專門存廣告細節描述的list
			List<String> adDetailList = new ArrayList<>();
			// 專門存放照片的list
			String[] imageFileNames = null;
			String imageFileName = null;

			for (Iterator<RoomRentBean> iterator = RoomRentAds.iterator(); iterator.hasNext();) {
				RoomRentBean rrb = (RoomRentBean) iterator.next();

				// rentPrice
				Iterator<RoomBean> itr = rrb.getRoomItems().iterator();
				List<Integer> rentPriceList = new ArrayList<Integer>();
				Integer suiteQuantity = 0;
				Integer nonSuiteQuantity = 0;
				while (itr.hasNext()) {
					RoomBean room = itr.next();
					rentPriceList.add(room.getRentPrice());
					if (room.getRoomType().equals("0")) {
						suiteQuantity++;
					} else {
						nonSuiteQuantity++;
					}
				}
				// 暫時借adDeposit來存rentPrice

				Collections.sort(rentPriceList);
				if (!rrb.getAdRentType().contentEquals("0")) {
					if (rentPriceList.size() > 1) {
						rrb.setAdDeposit(
								"$" + rentPriceList.get(0) + "-$" + rentPriceList.get(rentPriceList.size() - 1));
					} else {
						rrb.setAdDeposit("$" + rentPriceList.get(0));
					}
				}
				if (rrb.getAdRentType().contentEquals("0")) {
					String livingRoom = rrb.getAdLivingRoomNum();
					String toilet = rrb.getAdToiletNum();
					String balcony = rrb.getAdBalconyNum();
					String roomType = "";
					if (livingRoom.contentEquals("0")) {

						if (toilet.contentEquals("0")) {

							if (!balcony.contentEquals("0")) {
								roomType = balcony + "陽台";
							}

						} else {
							if (balcony.contentEquals("0")) {
								roomType = toilet + "衛";
							} else {
								roomType = toilet + "衛" + balcony + "陽台";
							}

						}

					} else {
						if (toilet.contentEquals("0")) {

							if (!balcony.contentEquals("0")) {
								roomType = livingRoom + "廳" + balcony + "陽台";
							} else {
								roomType = livingRoom + "廳";
							}

						} else {
							if (balcony.contentEquals("0")) {
								roomType = livingRoom + "廳" + toilet + "衛";
							} else {
								roomType = livingRoom + "廳" + toilet + "衛" + balcony + "陽台";
							}

						}

					}
					// 拿adCurage來暫存 roomType與格局
					rrb.setAdCurAge(rrb.getAdRoomNum() + "房" + roomType);
				} else {
					if (suiteQuantity == 0) {
						rrb.setAdCurAge(nonSuiteQuantity + "雅房");
					} else {
						if (nonSuiteQuantity != 0) {
							rrb.setAdCurAge(suiteQuantity + "套房" + nonSuiteQuantity + "雅房");
						} else {
							rrb.setAdCurAge(suiteQuantity + "套房");
						}

					}

				}

				// areaCode
				if (rrb.getAdAreacode() != null && rrb.getAdAreacode().trim().length() != 0) {

					String area = ConvertTableUtil.addressCodeToString(rrb.getAdAreacode());
					rrb.setAdAreacode(area);
					System.out.println("地區 = " + area);

					String detailAddr = rrb.getAdDetailaddress();
					String strStart = detailAddr.substring(0, 6);
					String strEnd = detailAddr.substring(6);
					String strFinal = null;
					if (strEnd.contains("橋")) {
						strFinal = strStart + strEnd.substring(0, strEnd.indexOf("橋") + 1);
					} else if (strEnd.contains("街")) {
						strFinal = strStart + strEnd.substring(0, strEnd.indexOf("街") + 1);

					} else if (strEnd.contains("路")) {
						strFinal = strStart + strEnd.substring(0, strEnd.indexOf("路") + 1);

					} else if (strEnd.contains("里")) {
						strFinal = strStart + strEnd.substring(0, strEnd.indexOf("里") + 1);
					} else {
						strFinal = detailAddr;
					}
//					 String query = String.format("%s",URLEncoder.encode(detailAddr,"UTF-8"));
//					 System.out.println(detailAddr);
					rrb.setAdDetailaddress(strFinal);
				}
//				出租方式rentType
				String rentType = rrb.getAdRentType();
				if (rentType.equals("0")) {
					rentType = "整層出租";
				} else if (rentType.equals("1")) {
					rentType = "獨立套房";
				} else if (rentType.equals("2")) {
					rentType = "分租套房";
				} else if (rentType.equals("3")) {
					rentType = "出租房間(有公共空間)";
				}
				rrb.setAdRentType(rentType);
//				租金 先判斷rentType 在jsp去判斷取出來
//				Integer adRentPrice = rrb.getAdRentPrice();
//				model.addAttribute("adRentPrice", adRentPrice);
//				System.out.println("租金=" + adRentPrice);
				if (mb.getState().equals("1")) {
					// 現在時間
					Long nowTime = System.currentTimeMillis();
					// 廣告創建時間
					Long adCreateTime = rrb.getAdCreateDate().getTime();
					// 相減得到的毫秒數/1000*60*60*24 得到天數 大於7天顯示可直接聯繫
					Long adDay = (nowTime - adCreateTime) / (1000 * 60 * 60 * 24);
//					System.out.println("adDay=" + adDay);
					if (adDay >= 7) {
						String adState = "可直接聯繫";
						// 拿這個欄位暫時儲存早鳥還是可直接聯繫
						rrb.setAdPhone(adState);
					} else {
						String adState = "早鳥";
						rrb.setAdPhone(adState);
					}
				} else {
					String adState = "可直接聯繫";
					rrb.setAdPhone(adState);
				}
//				adTitle 直接取出
//				String adTitle = rrb.getAdTitle();				
				// 專門存放圖片檔名的list(只顯示一張圖 所以用檔名)

				if (rrb.getAdImages() != null && rrb.getAdImages().trim().length() != 0) {
					imageFileNames = rrb.getAdImages().trim().split(",");
					imageFileName = imageFileNames[0];
//					model.addAttribute("adImage", imageFileName);
					rrb.setAdImages(imageFileName.trim());

				} else {
					rrb.setAdImages(null);
				}

//				 //adDetail				
				Clob clob = rrb.getAdDetailContent();
				if (clob != null) {
					try (Reader r = rrb.getAdDetailContent().getCharacterStream();) {
						StringBuffer buffer = new StringBuffer();
						int ch, count = 0;

						while ((ch = r.read()) != -1) {
							buffer.append("" + (char) ch);
							count++;
						}
						adDetailList.add(buffer.toString());
						if (count > 45) {
							model.addAttribute("needReadMore", "yes");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				list.add(rrb);
			}
			// 頁數 屬性物件
			model.addAttribute("pageNo", pageNo);
			// 總頁數 屬性物件
			model.addAttribute("totalPages", roomRentService.getMyAdTotalPagesByFk(mb));
			// 廣告細節的屬性物件
			model.addAttribute("adDetail", adDetailList);
			// 租房廣告的屬性物件
			model.addAttribute("roomRentAd", list);

		}

		// 交由MyAd.jsp來顯示某頁的找房廣告資料
//		response.sendRedirect(response.encodeRedirectURL("/_4u4u_Account/MyAd.jsp?adStyle=" + adStyle + "adId=" + adId));
		if (adStyle == 1) {
//			RequestDispatcher rd = request.getRequestDispatcher("/_4u4u_Account/myFindRoomAd.jsp");
//			rd.forward(request, response);
			return "/_4u4u_Account/myFindRoomAd";
		} else {
//			RequestDispatcher rd = request.getRequestDispatcher("/_4u4u_Account/myRoomRentAd.jsp");
//			rd.forward(request, response);
			return "/_4u4u_Account/myRoomRentAd";
		}

	}

}

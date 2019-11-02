package _4u4u.model;

import java.io.Serializable;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
// 本類別存放訂單資料
@Entity
@Table(name="RoomAd")
public class RoomRentBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer adId;
	private String 	adRentType;
	private Integer adRoomNum; 
	private String	adToiletNum; 
	private String  adBalconyNum;
	private String  adLivingRoomNum;
	private String  adIdentity;
	private String  adAgentFee;
	private String  adCurrentPeopleNum;
	private String  adHouseType;
	private String  adAreacode; 
	private String  adDetailaddress;
	private String adParkingCount;
	private String adExtraCost;
	private Boolean adHasElevator;
	private String adMinimumStayLength;
	private Date adAvailableDate;
	private String adTotalArea;
	private Integer adRentPrice;
	private String adDeposit;
	private String adNearByTransport;
	private String adTitle;
	private Clob adDetailContent;
	private String adPhone;
	private Boolean adPhoneAttachToAd;
	private String adImages;
	private Boolean adCook;
	private Boolean adCurHasPet;
	private Boolean adCurSmoke;
	private String adCurSexOrientation;
	private Boolean adCurAllowedSearchbySexOrient;
	private String adCurGender;
	private String adCurAge;
	private Boolean adFutureSmoke;
	private String adFutureJobType;
	private Boolean adFuturePet;
	private String adFutureGender;
	private String adFutureAge;
	private Boolean adFutureCoupleAccept;
	private Timestamp adUpdateDate;
	private Timestamp adCreateDate;
	private Boolean adState;
	private Integer emailMax;
	private Integer sendMail;
	private Date emailDate;
	private Boolean adStyle;
//	private MultipartFile[] roomImgFile;
//	
//
//	@XmlTransient
//	@Transient
//	public MultipartFile[] getActImgFile() {
//		return roomImgFile;
//	}
//
//	public void setActImgFile(MultipartFile[] roomImgFile) {
//		this.roomImgFile = roomImgFile;
//	}
//	
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="fk_member_memId")
	private MemberBean roomRentMemId;
	
	@OneToMany(mappedBy = "roomAd",cascade = CascadeType.ALL)
	private Set<RoomBean> roomItems = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "accusationForRoomAdAdId",cascade = CascadeType.ALL)
	private Set<AccusationForRoomAdBean> accusationForRoomAdAdId = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "savedAdForRoomAdAdId",cascade = CascadeType.ALL)
	private Set<SavedAdForRoomAdBean> savedAdForRoomAdAdId = new LinkedHashSet<>();
	@OneToMany(mappedBy = "interestedAdForRoomAdAdId",cascade = CascadeType.ALL)
	private Set<InterestedAdForRoomAdBean> interestedAdForRoomAdAdId = new LinkedHashSet<>();
	
	public RoomRentBean() {
	}
	public RoomRentBean(String adRentType, Integer adRoomNum, String adToiletNum, String adBalconyNum,
			String adLivingRoomNum, String adIdentity, String adAgentFee, String adCurrentPeopleNum, String adHouseType,
			String adAreacode, String adDetailaddress, String adParkingCount, String adExtraCost, Boolean adHasElevator,
			String adMinimumStayLength, Date adAvailableDate, String adTotalArea, Integer adRentPrice, String adDeposit,
			String adNearByTransport, String adTitle, Clob adDetailContent, String adPhone, Boolean adPhoneAttachToAd,
			String adImages, Boolean adCook, Boolean adCurHasPet, Boolean adCurSmoke, String adCurSexOrientation,
			Boolean adCurAllowedSearchbySexOrient, String adCurGender, String adCurAge, Boolean adFutureSmoke,
			String adFutureJobType, Boolean adFuturePet, String adFutureGender, String adFutureAge,
			Boolean adFutureCoupleAccept, Timestamp adUpdateDate, Timestamp adCreateDate, Boolean adState,
			Integer emailMax, Integer sendMail, Date emailDate, Boolean adStyle, MemberBean roomRentMemId) {
		super();
		this.adRentType = adRentType;
		this.adRoomNum = adRoomNum;
		this.adToiletNum = adToiletNum;
		this.adBalconyNum = adBalconyNum;
		this.adLivingRoomNum = adLivingRoomNum;
		this.adIdentity = adIdentity;
		this.adAgentFee = adAgentFee;
		this.adCurrentPeopleNum = adCurrentPeopleNum;
		this.adHouseType = adHouseType;
		this.adAreacode = adAreacode;
		this.adDetailaddress = adDetailaddress;
		this.adParkingCount = adParkingCount;
		this.adExtraCost = adExtraCost;
		this.adHasElevator = adHasElevator;
		this.adMinimumStayLength = adMinimumStayLength;
		this.adAvailableDate = adAvailableDate;
		this.adTotalArea = adTotalArea;
		this.adRentPrice = adRentPrice;
		this.adDeposit = adDeposit;
		this.adNearByTransport = adNearByTransport;
		this.adTitle = adTitle;
		this.adDetailContent = adDetailContent;
		this.adPhone = adPhone;
		this.adPhoneAttachToAd = adPhoneAttachToAd;
		this.adImages = adImages;
		this.adCook = adCook;
		this.adCurHasPet = adCurHasPet;
		this.adCurSmoke = adCurSmoke;
		this.adCurSexOrientation = adCurSexOrientation;
		this.adCurAllowedSearchbySexOrient = adCurAllowedSearchbySexOrient;
		this.adCurGender = adCurGender;
		this.adCurAge = adCurAge;
		this.adFutureSmoke = adFutureSmoke;
		this.adFutureJobType = adFutureJobType;
		this.adFuturePet = adFuturePet;
		this.adFutureGender = adFutureGender;
		this.adFutureAge = adFutureAge;
		this.adFutureCoupleAccept = adFutureCoupleAccept;
		this.adUpdateDate = adUpdateDate;
		this.adCreateDate = adCreateDate;
		this.adState = adState;
		this.emailMax = emailMax;
		this.sendMail = sendMail;
		this.emailDate = emailDate;
		this.adStyle = adStyle;
		this.roomRentMemId = roomRentMemId;
	}
	public RoomRentBean(Integer adId, String adRentType, Integer adRoomNum, String adToiletNum, String adBalconyNum,
			String adLivingRoomNum, String adIdentity, String adAgentFee, String adCurrentPeopleNum, String adHouseType,
			String adAreacode, String adDetailaddress, String adParkingCount, String adExtraCost, Boolean adHasElevator,
			String adMinimumStayLength, Date adAvailableDate, String adTotalArea, Integer adRentPrice, String adDeposit,
			String adNearByTransport, String adTitle, Clob adDetailContent, String adPhone, Boolean adPhoneAttachToAd,
			String adImages, Boolean adCook, Boolean adCurHasPet, Boolean adCurSmoke, String adCurSexOrientation,
			Boolean adCurAllowedSearchbySexOrient, String adCurGender, String adCurAge, Boolean adFutureSmoke,
			String adFutureJobType, Boolean adFuturePet, String adFutureGender, String adFutureAge,
			Boolean adFutureCoupleAccept, Timestamp adUpdateDate, Timestamp adCreateDate, Boolean adState,
			Integer emailMax, Integer sendMail, Date emailDate, Boolean adStyle, MemberBean roomRentMemId) {
		super();
		this.adId = adId;
		this.adRentType = adRentType;
		this.adRoomNum = adRoomNum;
		this.adToiletNum = adToiletNum;
		this.adBalconyNum = adBalconyNum;
		this.adLivingRoomNum = adLivingRoomNum;
		this.adIdentity = adIdentity;
		this.adAgentFee = adAgentFee;
		this.adCurrentPeopleNum = adCurrentPeopleNum;
		this.adHouseType = adHouseType;
		this.adAreacode = adAreacode;
		this.adDetailaddress = adDetailaddress;
		this.adParkingCount = adParkingCount;
		this.adExtraCost = adExtraCost;
		this.adHasElevator = adHasElevator;
		this.adMinimumStayLength = adMinimumStayLength;
		this.adAvailableDate = adAvailableDate;
		this.adTotalArea = adTotalArea;
		this.adRentPrice = adRentPrice;
		this.adDeposit = adDeposit;
		this.adNearByTransport = adNearByTransport;
		this.adTitle = adTitle;
		this.adDetailContent = adDetailContent;
		this.adPhone = adPhone;
		this.adPhoneAttachToAd = adPhoneAttachToAd;
		this.adImages = adImages;
		this.adCook = adCook;
		this.adCurHasPet = adCurHasPet;
		this.adCurSmoke = adCurSmoke;
		this.adCurSexOrientation = adCurSexOrientation;
		this.adCurAllowedSearchbySexOrient = adCurAllowedSearchbySexOrient;
		this.adCurGender = adCurGender;
		this.adCurAge = adCurAge;
		this.adFutureSmoke = adFutureSmoke;
		this.adFutureJobType = adFutureJobType;
		this.adFuturePet = adFuturePet;
		this.adFutureGender = adFutureGender;
		this.adFutureAge = adFutureAge;
		this.adFutureCoupleAccept = adFutureCoupleAccept;
		this.adUpdateDate = adUpdateDate;
		this.adCreateDate = adCreateDate;
		this.adState = adState;
		this.emailMax = emailMax;
		this.sendMail = sendMail;
		this.emailDate = emailDate;
		this.adStyle = adStyle;
		this.roomRentMemId = roomRentMemId;
	}
	
	
	
	public RoomRentBean(Integer adId, String adRentType, Integer adRoomNum, String adToiletNum, String adBalconyNum,
			String adLivingRoomNum, String adHouseType, String adDetailaddress, String adTotalArea, Integer adRentPrice,
			String adNearByTransport, String adTitle, String adImages, Boolean adStyle, MemberBean roomRentMemId,
			Set<RoomBean> roomItems) {
		super();
		this.adId = adId;
		this.adRentType = adRentType;
		this.adRoomNum = adRoomNum;
		this.adToiletNum = adToiletNum;
		this.adBalconyNum = adBalconyNum;
		this.adLivingRoomNum = adLivingRoomNum;
		this.adHouseType = adHouseType;
		this.adDetailaddress = adDetailaddress;
		this.adTotalArea = adTotalArea;
		this.adRentPrice = adRentPrice;
		this.adNearByTransport = adNearByTransport;
		this.adTitle = adTitle;
		this.adImages = adImages;
		this.adStyle = adStyle;
		this.roomRentMemId = roomRentMemId;
		this.roomItems = roomItems;
	}
	public Integer getAdId() {
		return adId;
	}
	public void setAdId(Integer adId) {
		this.adId = adId;
	}
	public String getAdRentType() {
		return adRentType;
	}
	public void setAdRentType(String adRentType) {
		this.adRentType = adRentType;
	}
	public Integer getAdRoomNum() {
		return adRoomNum;
	}
	public void setAdRoomNum(Integer adRoomNum) {
		this.adRoomNum = adRoomNum;
	}
	public String getAdToiletNum() {
		return adToiletNum;
	}
	public void setAdToiletNum(String adToiletNum) {
		this.adToiletNum = adToiletNum;
	}
	public String getAdBalconyNum() {
		return adBalconyNum;
	}
	public void setAdBalconyNum(String adBalconyNum) {
		this.adBalconyNum = adBalconyNum;
	}
	public String getAdLivingRoomNum() {
		return adLivingRoomNum;
	}
	public void setAdLivingRoomNum(String adLivingRoomNum) {
		this.adLivingRoomNum = adLivingRoomNum;
	}
	public String getAdIdentity() {
		return adIdentity;
	}
	public void setAdIdentity(String adIdentity) {
		this.adIdentity = adIdentity;
	}
	public String getAdAgentFee() {
		return adAgentFee;
	}
	public void setAdAgentFee(String adAgentFee) {
		this.adAgentFee = adAgentFee;
	}
	public String getAdCurrentPeopleNum() {
		return adCurrentPeopleNum;
	}
	public void setAdCurrentPeopleNum(String adCurrentPeopleNum) {
		this.adCurrentPeopleNum = adCurrentPeopleNum;
	}
	public String getAdHouseType() {
		return adHouseType;
	}
	public void setAdHouseType(String adHouseType) {
		this.adHouseType = adHouseType;
	}
	public String getAdAreacode() {
		return adAreacode;
	}
	public void setAdAreacode(String adAreacode) {
		this.adAreacode = adAreacode;
	}
	public String getAdDetailaddress() {
		return adDetailaddress;
	}
	public void setAdDetailaddress(String adDetailaddress) {
		this.adDetailaddress = adDetailaddress;
	}
	public String getAdParkingCount() {
		return adParkingCount;
	}
	public void setAdParkingCount(String adParkingCount) {
		this.adParkingCount = adParkingCount;
	}
	public String getAdExtraCost() {
		return adExtraCost;
	}
	public void setAdExtraCost(String adExtraCost) {
		this.adExtraCost = adExtraCost;
	}
	public Boolean getAdHasElevator() {
		return adHasElevator;
	}
	public void setAdHasElevator(Boolean adHasElevator) {
		this.adHasElevator = adHasElevator;
	}
	public String getAdMinimumStayLength() {
		return adMinimumStayLength;
	}
	public void setAdMinimumStayLength(String adMinimumStayLength) {
		this.adMinimumStayLength = adMinimumStayLength;
	}
	public Date getAdAvailableDate() {
		return adAvailableDate;
	}
	public void setAdAvailableDate(Date adAvailableDate) {
		this.adAvailableDate = adAvailableDate;
	}
	public String getAdTotalArea() {
		return adTotalArea;
	}
	public void setAdTotalArea(String adTotalArea) {
		this.adTotalArea = adTotalArea;
	}
	public Integer getAdRentPrice() {
		return adRentPrice;
	}
	public void setAdRentPrice(Integer adRentPrice) {
		this.adRentPrice = adRentPrice;
	}
	public String getAdDeposit() {
		return adDeposit;
	}
	public void setAdDeposit(String adDeposit) {
		this.adDeposit = adDeposit;
	}
	public String getAdNearByTransport() {
		return adNearByTransport;
	}
	public void setAdNearByTransport(String adNearByTransport) {
		this.adNearByTransport = adNearByTransport;
	}
	public String getAdTitle() {
		return adTitle;
	}
	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}
	public Clob getAdDetailContent() {
		return adDetailContent;
	}
	public void setAdDetailContent(Clob adDetailContent) {
		this.adDetailContent = adDetailContent;
	}
	public String getAdPhone() {
		return adPhone;
	}
	public void setAdPhone(String adPhone) {
		this.adPhone = adPhone;
	}
	public Boolean getAdPhoneAttachToAd() {
		return adPhoneAttachToAd;
	}
	public void setAdPhoneAttachToAd(Boolean adPhoneAttachToAd) {
		this.adPhoneAttachToAd = adPhoneAttachToAd;
	}
	public String getAdImages() {
		return adImages;
	}
	public void setAdImages(String adImages) {
		this.adImages = adImages;
	}
	public Boolean getAdCook() {
		return adCook;
	}
	public void setAdCook(Boolean adCook) {
		this.adCook = adCook;
	}
	public Boolean getAdCurHasPet() {
		return adCurHasPet;
	}
	public void setAdCurHasPet(Boolean adCurHasPet) {
		this.adCurHasPet = adCurHasPet;
	}
	public Boolean getAdCurSmoke() {
		return adCurSmoke;
	}
	public void setAdCurSmoke(Boolean adCurSmoke) {
		this.adCurSmoke = adCurSmoke;
	}
	public String getAdCurSexOrientation() {
		return adCurSexOrientation;
	}
	public void setAdCurSexOrientation(String adCurSexOrientation) {
		this.adCurSexOrientation = adCurSexOrientation;
	}
	public Boolean getAdCurAllowedSearchbySexOrient() {
		return adCurAllowedSearchbySexOrient;
	}
	public void setAdCurAllowedSearchbySexOrient(Boolean adCurAllowedSearchbySexOrient) {
		this.adCurAllowedSearchbySexOrient = adCurAllowedSearchbySexOrient;
	}
	public String getAdCurGender() {
		return adCurGender;
	}
	public void setAdCurGender(String adCurGender) {
		this.adCurGender = adCurGender;
	}
	public String getAdCurAge() {
		return adCurAge;
	}
	public void setAdCurAge(String adCurAge) {
		this.adCurAge = adCurAge;
	}
	public Boolean getAdFutureSmoke() {
		return adFutureSmoke;
	}
	public void setAdFutureSmoke(Boolean adFutureSmoke) {
		this.adFutureSmoke = adFutureSmoke;
	}
	public String getAdFutureJobType() {
		return adFutureJobType;
	}
	public void setAdFutureJobType(String adFutureJobType) {
		this.adFutureJobType = adFutureJobType;
	}
	public Boolean getAdFuturePet() {
		return adFuturePet;
	}
	public void setAdFuturePet(Boolean adFuturePet) {
		this.adFuturePet = adFuturePet;
	}
	public String getAdFutureGender() {
		return adFutureGender;
	}
	public void setAdFutureGender(String adFutureGender) {
		this.adFutureGender = adFutureGender;
	}
	public String getAdFutureAge() {
		return adFutureAge;
	}
	public void setAdFutureAge(String adFutureAge) {
		this.adFutureAge = adFutureAge;
	}
	public Boolean getAdFutureCoupleAccept() {
		return adFutureCoupleAccept;
	}
	public void setAdFutureCoupleAccept(Boolean adFutureCoupleAccept) {
		this.adFutureCoupleAccept = adFutureCoupleAccept;
	}
	public Timestamp getAdUpdateDate() {
		return adUpdateDate;
	}
	public void setAdUpdateDate(Timestamp adUpdateDate) {
		this.adUpdateDate = adUpdateDate;
	}
	public Timestamp getAdCreateDate() {
		return adCreateDate;
	}
	public void setAdCreateDate(Timestamp adCreateDate) {
		this.adCreateDate = adCreateDate;
	}
	public Boolean getAdState() {
		return adState;
	}
	public void setAdState(Boolean adState) {
		this.adState = adState;
	}
	public Integer getEmailMax() {
		return emailMax;
	}
	public void setEmailMax(Integer emailMax) {
		this.emailMax = emailMax;
	}
	public Integer getSendMail() {
		return sendMail;
	}
	public void setSendMail(Integer sendMail) {
		this.sendMail = sendMail;
	}
	public Date getEmailDate() {
		return emailDate;
	}
	public void setEmailDate(Date emailDate) {
		this.emailDate = emailDate;
	}
	public Boolean getAdStyle() {
		return adStyle;
	}
	public void setAdStyle(Boolean adStyle) {
		this.adStyle = adStyle;
	}
	public MemberBean getRoomRentMemId() {
		return roomRentMemId;
	}
	public void setRoomRentMemId(MemberBean roomRentMemId) {
		this.roomRentMemId = roomRentMemId;
	}
	public Set<RoomBean> getRoomItems() {
		return roomItems;
	}
	public void setRoomItems(Set<RoomBean> roomItems) {
		this.roomItems = roomItems;
	}
	public Set<AccusationForRoomAdBean> getAccusationForRoomAdAdId() {
		return accusationForRoomAdAdId;
	}
	public void setAccusationForRoomAdAdId(Set<AccusationForRoomAdBean> accusationForRoomAdAdId) {
		this.accusationForRoomAdAdId = accusationForRoomAdAdId;
	}
	public Set<SavedAdForRoomAdBean> getSavedAdForRoomAdAdId() {
		return savedAdForRoomAdAdId;
	}
	public void setSavedAdForRoomAdAdId(Set<SavedAdForRoomAdBean> savedAdForRoomAdAdId) {
		this.savedAdForRoomAdAdId = savedAdForRoomAdAdId;
	}
	public Set<InterestedAdForRoomAdBean> getInterestedAdForRoomAdAdId() {
		return interestedAdForRoomAdAdId;
	}
	public void setInterestedAdForRoomAdAdId(Set<InterestedAdForRoomAdBean> interestedAdForRoomAdAdId) {
		this.interestedAdForRoomAdAdId = interestedAdForRoomAdAdId;
	}
	
}

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
@Entity
@Table(name="WantedRoomAd")
public class WantedRoomBean implements Serializable{	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer findRoomId;			// 找房廣告id
	private String peopleNumGender;	// 廣告者性別及人數	
	private Integer suiteQuantity;			
	private Integer roomQuantity;			
	private Boolean agreeShare;			// 合租意願
	private String areaCode;			// 地區(二級搜索) e.x:台南市歸仁區
	private Integer budget;				// 租金預算
	private Date checkInDate; 			// 可入住日期
	private String liveTime;			// 居住時間
	// 找房 想要的設施
	private Boolean hasWashMachine;  	// 洗衣機
	private Boolean hasRefrigerator; 	// 冰箱
	private Boolean hasTV;			 	// 電視
	private Boolean hasAirConditioning; // 冷氣
	private Boolean hasWaterHeater;  	// 熱水器
	private Boolean hasInternet;		// 網路
	private Boolean hasFourthTV;		// 第四台
	private Boolean hasGas;				// 天然瓦斯
	private Boolean hasWardrobe;		// 衣櫃
	private Boolean hasSofa;			// 沙發
	private Boolean hasTable;			// 桌子
	private Boolean hasChair;			// 椅子
	private Boolean hasParking;			// 停車位
	private Boolean hasBalcony;			// 陽台
	private Boolean hasSingleBed;		// 單人床
	private Boolean hasDoubleBed;		// 雙人床
	private Boolean allowCook;			// 開火
	private String age;					// 年齡
	private String job;					// 職業 
	private Boolean allowSmoke;			// 抽菸
	private Boolean allowPet;			// 養寵
	private String sexualOrientation;   // 性向
	private Boolean agreeAdCondition;   // 同意(性向)成為廣告搜尋條件
	// 找房 想要的室友
	private String wantedRoommatesGender;	// 室友性別
	private String wantedRoommatesAge;		// 室友年齡
	private String wantedRoommatesJob;		// 室友職業
	private Boolean wantedRoommatesSmoke; 	// 室友有無抽菸
	private Boolean wantedRoommatesPet; 	// 室友有無養寵
	private String wantedRoommatesSex; 		// 室友性向
	// 找房 廣告詳情
	private String adTitle;					// 廣告標題
	private Clob adDescription;			// 廣告現況描述
	private String phone;					// 手機(電話)
	private String images;					// 上傳的照片(存進資料庫是圖片網址)
	private Integer emailMax;				// 上限幾封信
	private Boolean adState;				// 廣告是否失效
	private Integer sendMail;				// 本日已寄出幾封email
	private Date emailDate;					// 要寄信日期
	private Boolean adStyle;				// 廣告類型
	private Timestamp adCreateDate;
	private Timestamp adUpdateDate;
//	private MultipartFile[] memberImgFile;
//	
//
//	@XmlTransient
//	@Transient
//	public MultipartFile[] getActImgFile() {
//		return memberImgFile;
//	}
//
//	public void setActImgFile(MultipartFile[] memberImgFile) {
//		this.memberImgFile = memberImgFile;
//	}
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="fk_member_memId")
	private MemberBean wantedRoomAdMemId;
	
	@OneToMany(mappedBy = "accusationForWantedRoomAdFindRoomId",cascade = CascadeType.ALL)
	private Set<AccusationForWantedRoomAdBean> accusationForWantedRoomAdFindRoomId = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "savedAdForWantedRoomAdFindRoomId", cascade = CascadeType.ALL)
	private Set<SavedAdForWantedRoomAdBean> savedAdForWantedRoomAdFindRoomId = new LinkedHashSet<>();
	@OneToMany(mappedBy = "interestedAdForWantedRoomAdFindRoomId", cascade = CascadeType.ALL)
	private Set<InterestedAdForWantedRoomAdBean> interestedAdForWantedRoomAdFindRoomId = new LinkedHashSet<>();
	
	public WantedRoomBean() {
	}
	public WantedRoomBean(String peopleNumGender, Integer suiteQuantity, Integer roomQuantity, Boolean agreeShare,
			String areaCode, Integer budget, Date checkInDate, String liveTime, Boolean hasWashMachine,
			Boolean hasRefrigerator, Boolean hasTV, Boolean hasAirConditioning, Boolean hasWaterHeater,
			Boolean hasInternet, Boolean hasFourthTV, Boolean hasGas, Boolean hasWardrobe, Boolean hasSofa,
			Boolean hasTable, Boolean hasChair, Boolean hasParking, Boolean hasBalcony, Boolean hasSingleBed,
			Boolean hasDoubleBed, Boolean allowCook, String age, String job, Boolean allowSmoke, Boolean allowPet,
			String sexualOrientation, Boolean agreeAdCondition, String wantedRoommatesGender, String wantedRoommatesAge,
			String wantedRoommatesJob, Boolean wantedRoommatesSmoke, Boolean wantedRoommatesPet,
			String wantedRoommatesSex, String adTitle, Clob adDescription, String phone, String images,
			Integer emailMax, Boolean adState, Integer sendMail, Date emailDate, Boolean adStyle,
			Timestamp adCreateDate, Timestamp adUpdateDate, MemberBean wantedRoomAdMemId) {
		super();
		this.peopleNumGender = peopleNumGender;
		this.suiteQuantity = suiteQuantity;
		this.roomQuantity = roomQuantity;
		this.agreeShare = agreeShare;
		this.areaCode = areaCode;
		this.budget = budget;
		this.checkInDate = checkInDate;
		this.liveTime = liveTime;
		this.hasWashMachine = hasWashMachine;
		this.hasRefrigerator = hasRefrigerator;
		this.hasTV = hasTV;
		this.hasAirConditioning = hasAirConditioning;
		this.hasWaterHeater = hasWaterHeater;
		this.hasInternet = hasInternet;
		this.hasFourthTV = hasFourthTV;
		this.hasGas = hasGas;
		this.hasWardrobe = hasWardrobe;
		this.hasSofa = hasSofa;
		this.hasTable = hasTable;
		this.hasChair = hasChair;
		this.hasParking = hasParking;
		this.hasBalcony = hasBalcony;
		this.hasSingleBed = hasSingleBed;
		this.hasDoubleBed = hasDoubleBed;
		this.allowCook = allowCook;
		this.age = age;
		this.job = job;
		this.allowSmoke = allowSmoke;
		this.allowPet = allowPet;
		this.sexualOrientation = sexualOrientation;
		this.agreeAdCondition = agreeAdCondition;
		this.wantedRoommatesGender = wantedRoommatesGender;
		this.wantedRoommatesAge = wantedRoommatesAge;
		this.wantedRoommatesJob = wantedRoommatesJob;
		this.wantedRoommatesSmoke = wantedRoommatesSmoke;
		this.wantedRoommatesPet = wantedRoommatesPet;
		this.wantedRoommatesSex = wantedRoommatesSex;
		this.adTitle = adTitle;
		this.adDescription = adDescription;
		this.phone = phone;
		this.images = images;
		this.emailMax = emailMax;
		this.adState = adState;
		this.sendMail = sendMail;
		this.emailDate = emailDate;
		this.adStyle = adStyle;
		this.adCreateDate = adCreateDate;
		this.adUpdateDate = adUpdateDate;
		this.wantedRoomAdMemId = wantedRoomAdMemId;
	}

	public WantedRoomBean(Integer findRoomId, String peopleNumGender, Integer suiteQuantity, Integer roomQuantity,
			Boolean agreeShare, String areaCode, Integer budget, Date checkInDate, String liveTime,
			Boolean hasWashMachine, Boolean hasRefrigerator, Boolean hasTV, Boolean hasAirConditioning,
			Boolean hasWaterHeater, Boolean hasInternet, Boolean hasFourthTV, Boolean hasGas, Boolean hasWardrobe,
			Boolean hasSofa, Boolean hasTable, Boolean hasChair, Boolean hasParking, Boolean hasBalcony,
			Boolean hasSingleBed, Boolean hasDoubleBed, Boolean allowCook, String age, String job, Boolean allowSmoke,
			Boolean allowPet, String sexualOrientation, Boolean agreeAdCondition, String wantedRoommatesGender,
			String wantedRoommatesAge, String wantedRoommatesJob, Boolean wantedRoommatesSmoke,
			Boolean wantedRoommatesPet, String wantedRoommatesSex, String adTitle, Clob adDescription, String phone,
			String images, Integer emailMax, Boolean adState, Integer sendMail, Date emailDate, Boolean adStyle,
			Timestamp adCreateDate, Timestamp adUpdateDate, MemberBean wantedRoomAdMemId) {
		super();
		this.findRoomId = findRoomId;
		this.peopleNumGender = peopleNumGender;
		this.suiteQuantity = suiteQuantity;
		this.roomQuantity = roomQuantity;
		this.agreeShare = agreeShare;
		this.areaCode = areaCode;
		this.budget = budget;
		this.checkInDate = checkInDate;
		this.liveTime = liveTime;
		this.hasWashMachine = hasWashMachine;
		this.hasRefrigerator = hasRefrigerator;
		this.hasTV = hasTV;
		this.hasAirConditioning = hasAirConditioning;
		this.hasWaterHeater = hasWaterHeater;
		this.hasInternet = hasInternet;
		this.hasFourthTV = hasFourthTV;
		this.hasGas = hasGas;
		this.hasWardrobe = hasWardrobe;
		this.hasSofa = hasSofa;
		this.hasTable = hasTable;
		this.hasChair = hasChair;
		this.hasParking = hasParking;
		this.hasBalcony = hasBalcony;
		this.hasSingleBed = hasSingleBed;
		this.hasDoubleBed = hasDoubleBed;
		this.allowCook = allowCook;
		this.age = age;
		this.job = job;
		this.allowSmoke = allowSmoke;
		this.allowPet = allowPet;
		this.sexualOrientation = sexualOrientation;
		this.agreeAdCondition = agreeAdCondition;
		this.wantedRoommatesGender = wantedRoommatesGender;
		this.wantedRoommatesAge = wantedRoommatesAge;
		this.wantedRoommatesJob = wantedRoommatesJob;
		this.wantedRoommatesSmoke = wantedRoommatesSmoke;
		this.wantedRoommatesPet = wantedRoommatesPet;
		this.wantedRoommatesSex = wantedRoommatesSex;
		this.adTitle = adTitle;
		this.adDescription = adDescription;
		this.phone = phone;
		this.images = images;
		this.emailMax = emailMax;
		this.adState = adState;
		this.sendMail = sendMail;
		this.emailDate = emailDate;
		this.adStyle = adStyle;
		this.adCreateDate = adCreateDate;
		this.adUpdateDate = adUpdateDate;
		this.wantedRoomAdMemId = wantedRoomAdMemId;
	}

	
	public Integer getFindRoomId() {
		return findRoomId;
	}

	public void setFindRoomId(Integer findRoomId) {
		this.findRoomId = findRoomId;
	}

	public String getPeopleNumGender() {
		return peopleNumGender;
	}

	public void setPeopleNumGender(String peopleNumGender) {
		this.peopleNumGender = peopleNumGender;
	}

	public Integer getSuiteQuantity() {
		return suiteQuantity;
	}

	public void setSuiteQuantity(Integer suiteQuantity) {
		this.suiteQuantity = suiteQuantity;
	}

	public Integer getRoomQuantity() {
		return roomQuantity;
	}

	public void setRoomQuantity(Integer roomQuantity) {
		this.roomQuantity = roomQuantity;
	}

	public Boolean getAgreeShare() {
		return agreeShare;
	}

	public void setAgreeShare(Boolean agreeShare) {
		this.agreeShare = agreeShare;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getBudget() {
		return budget;
	}

	public void setBudget(Integer budget) {
		this.budget = budget;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(String liveTime) {
		this.liveTime = liveTime;
	}

	public Boolean getHasWashMachine() {
		return hasWashMachine;
	}

	public void setHasWashMachine(Boolean hasWashMachine) {
		this.hasWashMachine = hasWashMachine;
	}

	public Boolean getHasRefrigerator() {
		return hasRefrigerator;
	}

	public void setHasRefrigerator(Boolean hasRefrigerator) {
		this.hasRefrigerator = hasRefrigerator;
	}

	public Boolean getHasTV() {
		return hasTV;
	}

	public void setHasTV(Boolean hasTV) {
		this.hasTV = hasTV;
	}

	public Boolean getHasAirConditioning() {
		return hasAirConditioning;
	}

	public void setHasAirConditioning(Boolean hasAirConditioning) {
		this.hasAirConditioning = hasAirConditioning;
	}

	public Boolean getHasWaterHeater() {
		return hasWaterHeater;
	}

	public void setHasWaterHeater(Boolean hasWaterHeater) {
		this.hasWaterHeater = hasWaterHeater;
	}

	public Boolean getHasInternet() {
		return hasInternet;
	}

	public void setHasInternet(Boolean hasInternet) {
		this.hasInternet = hasInternet;
	}

	public Boolean getHasFourthTV() {
		return hasFourthTV;
	}

	public void setHasFourthTV(Boolean hasFourthTV) {
		this.hasFourthTV = hasFourthTV;
	}

	public Boolean getHasGas() {
		return hasGas;
	}

	public void setHasGas(Boolean hasGas) {
		this.hasGas = hasGas;
	}

	public Boolean getHasWardrobe() {
		return hasWardrobe;
	}

	public void setHasWardrobe(Boolean hasWardrobe) {
		this.hasWardrobe = hasWardrobe;
	}

	public Boolean getHasSofa() {
		return hasSofa;
	}

	public void setHasSofa(Boolean hasSofa) {
		this.hasSofa = hasSofa;
	}

	public Boolean getHasTable() {
		return hasTable;
	}

	public void setHasTable(Boolean hasTable) {
		this.hasTable = hasTable;
	}

	public Boolean getHasChair() {
		return hasChair;
	}

	public void setHasChair(Boolean hasChair) {
		this.hasChair = hasChair;
	}

	public Boolean getHasParking() {
		return hasParking;
	}

	public void setHasParking(Boolean hasParking) {
		this.hasParking = hasParking;
	}

	public Boolean getHasBalcony() {
		return hasBalcony;
	}

	public void setHasBalcony(Boolean hasBalcony) {
		this.hasBalcony = hasBalcony;
	}

	public Boolean getHasSingleBed() {
		return hasSingleBed;
	}

	public void setHasSingleBed(Boolean hasSingleBed) {
		this.hasSingleBed = hasSingleBed;
	}

	public Boolean getHasDoubleBed() {
		return hasDoubleBed;
	}

	public void setHasDoubleBed(Boolean hasDoubleBed) {
		this.hasDoubleBed = hasDoubleBed;
	}

	public Boolean getAllowCook() {
		return allowCook;
	}

	public void setAllowCook(Boolean allowCook) {
		this.allowCook = allowCook;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Boolean getAllowSmoke() {
		return allowSmoke;
	}

	public void setAllowSmoke(Boolean allowSmoke) {
		this.allowSmoke = allowSmoke;
	}

	public Boolean getAllowPet() {
		return allowPet;
	}

	public void setAllowPet(Boolean allowPet) {
		this.allowPet = allowPet;
	}

	public String getSexualOrientation() {
		return sexualOrientation;
	}

	public void setSexualOrientation(String sexualOrientation) {
		this.sexualOrientation = sexualOrientation;
	}

	public Boolean getAgreeAdCondition() {
		return agreeAdCondition;
	}

	public void setAgreeAdCondition(Boolean agreeAdCondition) {
		this.agreeAdCondition = agreeAdCondition;
	}

	public String getWantedRoommatesGender() {
		return wantedRoommatesGender;
	}

	public void setWantedRoommatesGender(String wantedRoommatesGender) {
		this.wantedRoommatesGender = wantedRoommatesGender;
	}

	public String getWantedRoommatesAge() {
		return wantedRoommatesAge;
	}

	public void setWantedRoommatesAge(String wantedRoommatesAge) {
		this.wantedRoommatesAge = wantedRoommatesAge;
	}

	public String getWantedRoommatesJob() {
		return wantedRoommatesJob;
	}

	public void setWantedRoommatesJob(String wantedRoommatesJob) {
		this.wantedRoommatesJob = wantedRoommatesJob;
	}

	public Boolean getWantedRoommatesSmoke() {
		return wantedRoommatesSmoke;
	}

	public void setWantedRoommatesSmoke(Boolean wantedRoommatesSmoke) {
		this.wantedRoommatesSmoke = wantedRoommatesSmoke;
	}

	public Boolean getWantedRoommatesPet() {
		return wantedRoommatesPet;
	}

	public void setWantedRoommatesPet(Boolean wantedRoommatesPet) {
		this.wantedRoommatesPet = wantedRoommatesPet;
	}

	public String getWantedRoommatesSex() {
		return wantedRoommatesSex;
	}

	public void setWantedRoommatesSex(String wantedRoommatesSex) {
		this.wantedRoommatesSex = wantedRoommatesSex;
	}

	public String getAdTitle() {
		return adTitle;
	}

	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}

	public Clob getAdDescription() {
		return adDescription;
	}

	public void setAdDescription(Clob adDescription) {
		this.adDescription = adDescription;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Integer getEmailMax() {
		return emailMax;
	}

	public void setEmailMax(Integer emailMax) {
		this.emailMax = emailMax;
	}

	public Boolean getAdState() {
		return adState;
	}

	public void setAdState(Boolean adState) {
		this.adState = adState;
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

	public Timestamp getAdCreateDate() {
		return adCreateDate;
	}

	public void setAdCreateDate(Timestamp adCreateDate) {
		this.adCreateDate = adCreateDate;
	}

	public Timestamp getAdUpdateDate() {
		return adUpdateDate;
	}

	public void setAdUpdateDate(Timestamp adUpdateDate) {
		this.adUpdateDate = adUpdateDate;
	}

	public MemberBean getWantedRoomAdMemId() {
		return wantedRoomAdMemId;
	}

	public void setWantedRoomAdMemId(MemberBean wantedRoomAdMemId) {
		this.wantedRoomAdMemId = wantedRoomAdMemId;
	}

	public Set<AccusationForWantedRoomAdBean> getAccusationForWantedRoomAdFindRoomId() {
		return accusationForWantedRoomAdFindRoomId;
	}

	public void setAccusationForWantedRoomAdFindRoomId(
			Set<AccusationForWantedRoomAdBean> accusationForWantedRoomAdFindRoomId) {
		this.accusationForWantedRoomAdFindRoomId = accusationForWantedRoomAdFindRoomId;
	}

	public Set<SavedAdForWantedRoomAdBean> getSavedAdForWantedRoomAdFindRoomId() {
		return savedAdForWantedRoomAdFindRoomId;
	}

	public void setSavedAdForWantedRoomAdFindRoomId(Set<SavedAdForWantedRoomAdBean> savedAdForWantedRoomAdFindRoomId) {
		this.savedAdForWantedRoomAdFindRoomId = savedAdForWantedRoomAdFindRoomId;
	}

	public Set<InterestedAdForWantedRoomAdBean> getInterestedAdForWantedRoomAdFindRoomId() {
		return interestedAdForWantedRoomAdFindRoomId;
	}

	public void setInterestedAdForWantedRoomAdFindRoomId(
			Set<InterestedAdForWantedRoomAdBean> interestedAdForWantedRoomAdFindRoomId) {
		this.interestedAdForWantedRoomAdFindRoomId = interestedAdForWantedRoomAdFindRoomId;
	}

	
	
}	
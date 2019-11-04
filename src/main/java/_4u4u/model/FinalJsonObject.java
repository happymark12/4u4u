package _4u4u.model;

public class FinalJsonObject {
	
	//for Both Ads
	private Integer adId;
	private String adTitle;
	private String interestedAdTitle;
	private Integer interestedPrimaryKey;
	private String[] adImages;
	private Boolean adStyle; 
	private Integer totalPages;
	
	private String adRentPrice;
	private String memberState; //2:vip 1:normal
	private String contactState; //2:可聯繫 1:早鳥
	//for RoomRentAd
	private String adRentType;
	private String roomType;
//	private String adNearByTransport;
//	private String adHouseType;
	private String adAddress;
//	Integer adRoomNum; 
//	String adToiletNu;
//	String adBalconyNum;
//	String adLivingRoomNum;
//	String adTotalArea;
//	private MemberBean roomRentMemId;
	
	//for WantedRoomAd
	private String budget;	
	private String peopleNumGender;	// 廣告者性別及人數	
	private String wantedRoomType;
	private String age;					// 年齡
	private String job;	
	private String name;
	public FinalJsonObject() {
	}
	
	
	public Integer getInterestedPrimaryKey() {
		return interestedPrimaryKey;
	}


	public void setInterestedPrimaryKey(Integer interestedPrimaryKey) {
		this.interestedPrimaryKey = interestedPrimaryKey;
	}


	public String getInterestedAdTitle() {
		return interestedAdTitle;
	}


	public void setInterestedAdTitle(String interestedAdTitle) {
		this.interestedAdTitle = interestedAdTitle;
	}


	public String getContactState() {
		return contactState;
	}

	public Integer getTotalPages() {
		return totalPages;
	}


	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public void setContactState(String contactState) {
		this.contactState = contactState;
	}


	public String getMemberState() {
		return memberState;
	}


	public void setMemberState(String memberState) {
		this.memberState = memberState;
	}


	public Integer getAdId() {
		return adId;
	}
	public void setAdId(Integer adId) {
		this.adId = adId;
	}
	public String getAdTitle() {
		return adTitle;
	}
	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}
	public String[] getAdImages() {
		return adImages;
	}
	public void setAdImages(String[] adImages) {
		this.adImages = adImages;
	}
	public Boolean getAdStyle() {
		return adStyle;
	}
	public void setAdStyle(Boolean adStyle) {
		this.adStyle = adStyle;
	}
	public String getAdRentPrice() {
		return adRentPrice;
	}
	public void setAdRentPrice(String adRentPrice) {
		this.adRentPrice = adRentPrice;
	}
	public String getAdRentType() {
		return adRentType;
	}
	public void setAdRentType(String adRentType) {
		this.adRentType = adRentType;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getAdaddress() {
		return adAddress;
	}
	public void setAdaddress(String adaddress) {
		this.adAddress = adaddress;
	}
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
	}
	public String getPeopleNumGender() {
		return peopleNumGender;
	}
	public void setPeopleNumGender(String peopleNumGender) {
		this.peopleNumGender = peopleNumGender;
	}
	public String getWantedRoomType() {
		return wantedRoomType;
	}
	public void setWantedRoomType(String wantedRoomType) {
		this.wantedRoomType = wantedRoomType;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	
	
}

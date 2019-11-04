package _4u4u.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Search")
public class SearchBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer searchId;
	private String searchTitle;
	private String searchType;
	private String searchAreaCode;
	private String rentType;
	private String houseType;
	private Boolean hasElevator;
	private String futureGender;
	private Boolean hasToilet;
	private Boolean cook;
	private Boolean hasParking;
	private String adIdentity;
	private String minimumStayLength;
	private Date availableDate;
	private Boolean smoke;
	private Boolean pet;
	private Integer roomNum;
	private String rentPriceRange;
	private Boolean balcony;
	private Boolean duplex;
	private Boolean hasWash;
	private Boolean hasIcebox;
	private Boolean has4;
	private Boolean hasWaterHeater;
	private Boolean hasGas;
	private Boolean hasTV;
	private Boolean hasWardrobe;
	private Boolean hasBroadband;
	private Boolean hasDesk;
	private Boolean hasChair;
	private Boolean hasSingleBed;
	private Boolean hasDoubleBed;
	private Boolean hasColdAir;
	private Boolean coupleAccept;
	private String jobType;
	private String gender;
	private String ageRange;
	private Boolean selectLGBT;
	private Date createDate;
	private Integer emailMax;
	private Integer emailCount;
	private Date emailDate;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="fk_member_memId")
	private MemberBean searchMember;
	
	public SearchBean() {
		super();
	}
	public SearchBean(Integer searchId, String searchTitle, String searchType, String searchAreaCode, String rentType,
			String houseType, Boolean hasElevator, String futureGender, Boolean hasToilet, Boolean cook,
			Boolean hasParking, String adIdentity, String minimumStayLength, Date availableDate, Boolean smoke,
			Boolean pet, Integer roomNum, String rentPriceRange, Boolean balcony, Boolean duplex, Boolean hasWash,
			Boolean hasIcebox, Boolean has4, Boolean hasWaterHeater, Boolean hasGas, Boolean hasTV, Boolean hasWardrobe,
			Boolean hasBroadband, Boolean hasDesk, Boolean hasChair, Boolean hasSingleBed, Boolean hasDoubleBed,
			Boolean hasColdAir, Boolean coupleAccept, String jobType, String gender, String ageRange,
			Boolean selectLGBT, Date createDate, Integer emailMax, Integer emailCount, Date emailDate) {
		super();
		this.searchId = searchId;
		this.searchTitle = searchTitle;
		this.searchType = searchType;
		this.searchAreaCode = searchAreaCode;
		this.rentType = rentType;
		this.houseType = houseType;
		this.hasElevator = hasElevator;
		this.futureGender = futureGender;
		this.hasToilet = hasToilet;
		this.cook = cook;
		this.hasParking = hasParking;
		this.adIdentity = adIdentity;
		this.minimumStayLength = minimumStayLength;
		this.availableDate = availableDate;
		this.smoke = smoke;
		this.pet = pet;
		this.roomNum = roomNum;
		this.rentPriceRange = rentPriceRange;
		this.balcony = balcony;
		this.duplex = duplex;
		this.hasWash = hasWash;
		this.hasIcebox = hasIcebox;
		this.has4 = has4;
		this.hasWaterHeater = hasWaterHeater;
		this.hasGas = hasGas;
		this.hasTV = hasTV;
		this.hasWardrobe = hasWardrobe;
		this.hasBroadband = hasBroadband;
		this.hasDesk = hasDesk;
		this.hasChair = hasChair;
		this.hasSingleBed = hasSingleBed;
		this.hasDoubleBed = hasDoubleBed;
		this.hasColdAir = hasColdAir;
		this.coupleAccept = coupleAccept;
		this.jobType = jobType;
		this.gender = gender;
		this.ageRange = ageRange;
		this.selectLGBT = selectLGBT;
		this.createDate = createDate;
		this.emailMax = emailMax;
		this.emailCount = emailCount;
		this.emailDate = emailDate;
	}
	public SearchBean(Integer searchId, String searchTitle, String searchType, String searchAreaCode, String rentType,
			String houseType, Boolean hasElevator, String futureGender, Boolean hasToilet, Boolean cook,
			Boolean hasParking, String adIdentity, String minimumStayLength, Date availableDate, Boolean smoke,
			Boolean pet, Integer roomNum, String rentPriceRange, Boolean balcony, Boolean duplex, Boolean hasWash,
			Boolean hasIcebox, Boolean has4, Boolean hasWaterHeater, Boolean hasGas, Boolean hasTV, Boolean hasWardrobe,
			Boolean hasBroadband, Boolean hasDesk, Boolean hasChair, Boolean hasSingleBed, Boolean hasDoubleBed,
			Boolean hasColdAir, Boolean coupleAccept, String jobType, String gender, String ageRange,
			Boolean selectLGBT, Date createDate, Integer emailMax, Integer emailCount, Date emailDate,
			MemberBean searchMember) {
		super();
		this.searchId = searchId;
		this.searchTitle = searchTitle;
		this.searchType = searchType;
		this.searchAreaCode = searchAreaCode;
		this.rentType = rentType;
		this.houseType = houseType;
		this.hasElevator = hasElevator;
		this.futureGender = futureGender;
		this.hasToilet = hasToilet;
		this.cook = cook;
		this.hasParking = hasParking;
		this.adIdentity = adIdentity;
		this.minimumStayLength = minimumStayLength;
		this.availableDate = availableDate;
		this.smoke = smoke;
		this.pet = pet;
		this.roomNum = roomNum;
		this.rentPriceRange = rentPriceRange;
		this.balcony = balcony;
		this.duplex = duplex;
		this.hasWash = hasWash;
		this.hasIcebox = hasIcebox;
		this.has4 = has4;
		this.hasWaterHeater = hasWaterHeater;
		this.hasGas = hasGas;
		this.hasTV = hasTV;
		this.hasWardrobe = hasWardrobe;
		this.hasBroadband = hasBroadband;
		this.hasDesk = hasDesk;
		this.hasChair = hasChair;
		this.hasSingleBed = hasSingleBed;
		this.hasDoubleBed = hasDoubleBed;
		this.hasColdAir = hasColdAir;
		this.coupleAccept = coupleAccept;
		this.jobType = jobType;
		this.gender = gender;
		this.ageRange = ageRange;
		this.selectLGBT = selectLGBT;
		this.createDate = createDate;
		this.emailMax = emailMax;
		this.emailCount = emailCount;
		this.emailDate = emailDate;
		this.searchMember = searchMember;
	}
	
	public Integer getSearchId() {
		return searchId;
	}
	public void setSearchId(Integer searchId) {
		this.searchId = searchId;
	}
	public String getSearchTitle() {
		return searchTitle;
	}
	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchAreaCode() {
		return searchAreaCode;
	}
	public void setSearchAreaCode(String searchAreaCode) {
		this.searchAreaCode = searchAreaCode;
	}
	public String getRentType() {
		return rentType;
	}
	public void setRentType(String rentType) {
		this.rentType = rentType;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public Boolean getHasElevator() {
		return hasElevator;
	}
	public void setHasElevator(Boolean hasElevator) {
		this.hasElevator = hasElevator;
	}
	public String getFutureGender() {
		return futureGender;
	}
	public void setFutureGender(String futureGender) {
		this.futureGender = futureGender;
	}
	public Boolean getHasToilet() {
		return hasToilet;
	}
	public void setHasToilet(Boolean hasToilet) {
		this.hasToilet = hasToilet;
	}
	public Boolean getCook() {
		return cook;
	}
	public void setCook(Boolean cook) {
		this.cook = cook;
	}
	public Boolean getHasParking() {
		return hasParking;
	}
	public void setHasParking(Boolean hasParking) {
		this.hasParking = hasParking;
	}
	public String getAdIdentity() {
		return adIdentity;
	}
	public void setAdIdentity(String adIdentity) {
		this.adIdentity = adIdentity;
	}
	public String getMinimumStayLength() {
		return minimumStayLength;
	}
	public void setMinimumStayLength(String minimumStayLength) {
		this.minimumStayLength = minimumStayLength;
	}
	public Date getAvailableDate() {
		return availableDate;
	}
	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}
	public Boolean getSmoke() {
		return smoke;
	}
	public void setSmoke(Boolean smoke) {
		this.smoke = smoke;
	}
	public Boolean getPet() {
		return pet;
	}
	public void setPet(Boolean pet) {
		this.pet = pet;
	}
	public Integer getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}
	public String getRentPriceRange() {
		return rentPriceRange;
	}
	public void setRentPriceRange(String rentPriceRange) {
		this.rentPriceRange = rentPriceRange;
	}
	public Boolean getBalcony() {
		return balcony;
	}
	public void setBalcony(Boolean balcony) {
		this.balcony = balcony;
	}
	public Boolean getDuplex() {
		return duplex;
	}
	public void setDuplex(Boolean duplex) {
		this.duplex = duplex;
	}
	public Boolean getHasWash() {
		return hasWash;
	}
	public void setHasWash(Boolean hasWash) {
		this.hasWash = hasWash;
	}
	public Boolean getHasIcebox() {
		return hasIcebox;
	}
	public void setHasIcebox(Boolean hasIcebox) {
		this.hasIcebox = hasIcebox;
	}
	public Boolean getHas4() {
		return has4;
	}
	public void setHas4(Boolean has4) {
		this.has4 = has4;
	}
	public Boolean getHasWaterHeater() {
		return hasWaterHeater;
	}
	public void setHasWaterHeater(Boolean hasWaterHeater) {
		this.hasWaterHeater = hasWaterHeater;
	}
	public Boolean getHasGas() {
		return hasGas;
	}
	public void setHasGas(Boolean hasGas) {
		this.hasGas = hasGas;
	}
	public Boolean getHasTV() {
		return hasTV;
	}
	public void setHasTV(Boolean hasTV) {
		this.hasTV = hasTV;
	}
	public Boolean getHasWardrobe() {
		return hasWardrobe;
	}
	public void setHasWardrobe(Boolean hasWardrobe) {
		this.hasWardrobe = hasWardrobe;
	}
	public Boolean getHasBroadband() {
		return hasBroadband;
	}
	public void setHasBroadband(Boolean hasBroadband) {
		this.hasBroadband = hasBroadband;
	}
	public Boolean getHasDesk() {
		return hasDesk;
	}
	public void setHasDesk(Boolean hasDesk) {
		this.hasDesk = hasDesk;
	}
	public Boolean getHasChair() {
		return hasChair;
	}
	public void setHasChair(Boolean hasChair) {
		this.hasChair = hasChair;
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
	public Boolean getHasColdAir() {
		return hasColdAir;
	}
	public void setHasColdAir(Boolean hasColdAir) {
		this.hasColdAir = hasColdAir;
	}
	public Boolean getCoupleAccept() {
		return coupleAccept;
	}
	public void setCoupleAccept(Boolean coupleAccept) {
		this.coupleAccept = coupleAccept;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAgeRange() {
		return ageRange;
	}
	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}
	public Boolean getSelectLGBT() {
		return selectLGBT;
	}
	public void setSelectLGBT(Boolean selectLGBT) {
		this.selectLGBT = selectLGBT;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getEmailMax() {
		return emailMax;
	}
	public void setEmailMax(Integer emailMax) {
		this.emailMax = emailMax;
	}
	public Integer getEmailCount() {
		return emailCount;
	}
	public void setEmailCount(Integer emailCount) {
		this.emailCount = emailCount;
	}
	public Date getEmailDate() {
		return emailDate;
	}
	public void setEmailDate(Date emailDate) {
		this.emailDate = emailDate;
	}
	public MemberBean getSearchMember() {
		return searchMember;
	}
	public void setSearchMember(MemberBean searchMember) {
		this.searchMember = searchMember;
	}
	
}

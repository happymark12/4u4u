package _4u4u.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name="Member",uniqueConstraints = {@UniqueConstraint(columnNames="email")})
public class MemberBean implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memId;
	private String email;		
	private String password;	
	private String gender;
	private Blob pic;
	private String name;
	private String picName;
	private String state;		
	private Timestamp vipStart;
	private Timestamp vipEnd;
	private Timestamp creatTime;
//	private MultipartFile memPicFile;
//	
//	@XmlTransient
//	@Transient
//	public MultipartFile getMemPicFile() {
//		return memPicFile;
//	}
//
//	public void setMemPicFile(MultipartFile memPicFile) {
//		this.memPicFile = memPicFile;
//	}


	@OneToMany(mappedBy = "roomRentMemId" , cascade = CascadeType.ALL)
	private Set<RoomRentBean> roomRentMemId = new LinkedHashSet<>(); 
	@OneToMany(mappedBy = "wantedRoomAdMemId" , cascade = CascadeType.ALL)
	private Set<WantedRoomBean> wantedRoomAdMemId = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "sendMemId" , cascade = CascadeType.ALL)
	private Set<MessagesBean> sendMemId = new LinkedHashSet<>();
	@OneToMany(mappedBy = "targetMemId" , cascade = CascadeType.ALL)
	private Set<MessagesBean> targetMemId = new LinkedHashSet<>();
	
	@OneToOne(mappedBy = "authenticationMemId", cascade = CascadeType.ALL)
	private AuthenticationBean authenticationMemId;
	
	@OneToMany(mappedBy = "accusationForRoomAdMemId", cascade = CascadeType.ALL)
	private Set<AccusationForRoomAdBean> accusationForRoomAdMemId = new LinkedHashSet<>();
	@OneToMany(mappedBy = "accusationForWantedRoomAdMemId", cascade = CascadeType.ALL)
	private Set<AccusationForWantedRoomAdBean> accusationForWantedRoomAdMemId = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "searchMember", cascade = CascadeType.ALL)
	private Set<SearchBean> searchMember = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "savedAdForRoomAdMemId", cascade = CascadeType.ALL)
	private Set<SavedAdForRoomAdBean> savedAdForRoomAdMemId = new LinkedHashSet<>();
	@OneToMany(mappedBy = "interestedAdForRoomAdMemId", cascade = CascadeType.ALL)
	private Set<InterestedAdForRoomAdBean> interestedAdForRoomAdMemId = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "savedAdForWantedRoomAdMemId", cascade = CascadeType.ALL)
	private Set<SavedAdForWantedRoomAdBean> savedAdForWantedRoomAdMemId = new LinkedHashSet<>();
	@OneToMany(mappedBy = "interestedAdForWantedRoomAdMemId", cascade = CascadeType.ALL)
	private Set<InterestedAdForWantedRoomAdBean> interestedAdForWantedRoomAdMemId = new LinkedHashSet<>();
	
	public MemberBean() {
	}

	public MemberBean(String email, String password, String gender, Blob pic, String name, String picName, String state,
			Timestamp vipStart, Timestamp vipEnd, Timestamp creatTime) {
		super();
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.pic = pic;
		this.name = name;
		this.picName = picName;
		this.state = state;
		this.vipStart = vipStart;
		this.vipEnd = vipEnd;
		this.creatTime = creatTime;
	}

	public MemberBean(Integer memId, String email, String password, String gender, Blob pic, String name,
			String picName, String state, Timestamp vipStart, Timestamp vipEnd, Timestamp creatTime) {
		super();
		this.memId = memId;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.pic = pic;
		this.name = name;
		this.picName = picName;
		this.state = state;
		this.vipStart = vipStart;
		this.vipEnd = vipEnd;
		this.creatTime = creatTime;
	}

	public Integer getMemId() {
		return memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Blob getPic() {
		return pic;
	}

	public void setPic(Blob pic) {
		this.pic = pic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getVipStart() {
		return vipStart;
	}

	public void setVipStart(Timestamp vipStart) {
		this.vipStart = vipStart;
	}

	public Timestamp getVipEnd() {
		return vipEnd;
	}

	public void setVipEnd(Timestamp vipEnd) {
		this.vipEnd = vipEnd;
	}

	public Timestamp getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}

	public Set<RoomRentBean> getRoomRentMemId() {
		return roomRentMemId;
	}

	public void setRoomRentMemId(Set<RoomRentBean> roomRentMemId) {
		this.roomRentMemId = roomRentMemId;
	}

	public Set<WantedRoomBean> getWantedRoomAdMemId() {
		return wantedRoomAdMemId;
	}

	public void setWantedRoomAdMemId(Set<WantedRoomBean> wantedRoomAdMemId) {
		this.wantedRoomAdMemId = wantedRoomAdMemId;
	}

	public Set<MessagesBean> getSendMemId() {
		return sendMemId;
	}

	public void setSendMemId(Set<MessagesBean> sendMemId) {
		this.sendMemId = sendMemId;
	}

	public Set<MessagesBean> getTargetMemId() {
		return targetMemId;
	}

	public void setTargetMemId(Set<MessagesBean> targetMemId) {
		this.targetMemId = targetMemId;
	}

	public AuthenticationBean getAuthenticationMemId() {
		return authenticationMemId;
	}

	public void setAuthenticationMemId(AuthenticationBean authenticationMemId) {
		this.authenticationMemId = authenticationMemId;
	}

	public Set<AccusationForRoomAdBean> getAccusationForRoomAdMemId() {
		return accusationForRoomAdMemId;
	}

	public void setAccusationForRoomAdMemId(Set<AccusationForRoomAdBean> accusationForRoomAdMemId) {
		this.accusationForRoomAdMemId = accusationForRoomAdMemId;
	}

	public Set<AccusationForWantedRoomAdBean> getAccusationForWantedRoomAdMemId() {
		return accusationForWantedRoomAdMemId;
	}

	public void setAccusationForWantedRoomAdMemId(Set<AccusationForWantedRoomAdBean> accusationForWantedRoomAdMemId) {
		this.accusationForWantedRoomAdMemId = accusationForWantedRoomAdMemId;
	}

	public Set<SearchBean> getSearchMember() {
		return searchMember;
	}

	public void setSearchMember(Set<SearchBean> searchMember) {
		this.searchMember = searchMember;
	}

	public Set<SavedAdForRoomAdBean> getSavedAdForRoomAdMemId() {
		return savedAdForRoomAdMemId;
	}

	public void setSavedAdForRoomAdMemId(Set<SavedAdForRoomAdBean> savedAdForRoomAdMemId) {
		this.savedAdForRoomAdMemId = savedAdForRoomAdMemId;
	}

	public Set<InterestedAdForRoomAdBean> getInterestedAdForRoomAdMemId() {
		return interestedAdForRoomAdMemId;
	}

	public void setInterestedAdForRoomAdMemId(Set<InterestedAdForRoomAdBean> interestedAdForRoomAdMemId) {
		this.interestedAdForRoomAdMemId = interestedAdForRoomAdMemId;
	}

	public Set<SavedAdForWantedRoomAdBean> getSavedAdForWantedRoomAdMemId() {
		return savedAdForWantedRoomAdMemId;
	}

	public void setSavedAdForWantedRoomAdMemId(Set<SavedAdForWantedRoomAdBean> savedAdForWantedRoomAdMemId) {
		this.savedAdForWantedRoomAdMemId = savedAdForWantedRoomAdMemId;
	}

	public Set<InterestedAdForWantedRoomAdBean> getInterestedAdForWantedRoomAdMemId() {
		return interestedAdForWantedRoomAdMemId;
	}

	public void setInterestedAdForWantedRoomAdMemId(Set<InterestedAdForWantedRoomAdBean> interestedAdForWantedRoomAdMemId) {
		this.interestedAdForWantedRoomAdMemId = interestedAdForWantedRoomAdMemId;
	}

}
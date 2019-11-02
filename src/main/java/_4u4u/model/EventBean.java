package _4u4u.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Event")
public class EventBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer eventId;
	private String eventTitle;
	private Date date;
	private String imageName;
//	private Blob image;
	private String address;
	private String detail;
	private Integer totalPeopleCountLimit;
	private Integer needRoomPeopleCount;
	private Integer haveRoomPelpleCount;
	private Boolean state;
	
	@OneToMany(mappedBy = "applicationFormEventId" , cascade = CascadeType.ALL)
	private Set<ApplicationFormBean> applicationFormEventId = new LinkedHashSet<>();
	
	public EventBean() {
		super();
	}
	//不包含主鍵的建構子
	public EventBean(String eventTitle, Date date, String imageName, Blob image, String address, String detail,
			Integer totalPeopleCountLimit, Integer needRoomPeopleCount, Integer haveRoomPelpleCount, Boolean state,
			Set<ApplicationFormBean> applicationFormEventId) {
		super();
		this.eventTitle = eventTitle;
		this.date = date;
		this.imageName = imageName;
		this.address = address;
		this.detail = detail;
		this.totalPeopleCountLimit = totalPeopleCountLimit;
		this.needRoomPeopleCount = needRoomPeopleCount;
		this.haveRoomPelpleCount = haveRoomPelpleCount;
		this.state = state;
		this.applicationFormEventId = applicationFormEventId;
	}
	//全部都包含的建構子
	public EventBean(Integer eventId, String eventTitle, Date date, String imageName, Blob image, String address,
			String detail, Integer totalPeopleCountLimit, Integer needRoomPeopleCount, Integer haveRoomPelpleCount,
			Boolean state, Set<ApplicationFormBean> applicationFormEventId) {
		super();
		this.eventId = eventId;
		this.eventTitle = eventTitle;
		this.date = date;
		this.imageName = imageName;
		this.address = address;
		this.detail = detail;
		this.totalPeopleCountLimit = totalPeopleCountLimit;
		this.needRoomPeopleCount = needRoomPeopleCount;
		this.haveRoomPelpleCount = haveRoomPelpleCount;
		this.state = state;
		this.applicationFormEventId = applicationFormEventId;
	}
	
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Integer getTotalPeopleCountLimit() {
		return totalPeopleCountLimit;
	}
	public void setTotalPeopleCountLimit(Integer totalPeopleCountLimit) {
		this.totalPeopleCountLimit = totalPeopleCountLimit;
	}
	public Integer getNeedRoomPeopleCount() {
		return needRoomPeopleCount;
	}
	public void setNeedRoomPeopleCount(Integer needRoomPeopleCount) {
		this.needRoomPeopleCount = needRoomPeopleCount;
	}
	public Integer getHaveRoomPelpleCount() {
		return haveRoomPelpleCount;
	}
	public void setHaveRoomPelpleCount(Integer haveRoomPelpleCount) {
		this.haveRoomPelpleCount = haveRoomPelpleCount;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Set<ApplicationFormBean> getApplicationFormEventId() {
		return applicationFormEventId;
	}
	public void setApplicationFormEventId(Set<ApplicationFormBean> applicationFormEventId) {
		this.applicationFormEventId = applicationFormEventId;
	}
	
}

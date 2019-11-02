package _4u4u.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "SavedAdForRoomAd",uniqueConstraints = {@UniqueConstraint(columnNames = {"fk_member_memId","fk_roomRent_adId"})})
public class SavedAdForRoomAdBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Timestamp createTime;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_member_memId")
	private MemberBean savedAdForRoomAdMemId;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_roomRent_adId")
	private RoomRentBean savedAdForRoomAdAdId;
	
	public SavedAdForRoomAdBean() {
		super();
	}
	public SavedAdForRoomAdBean(Timestamp createTime, MemberBean savedAdForRoomAdMemId,
			RoomRentBean savedAdForRoomAdAdId) {
		super();
		this.createTime = createTime;
		this.savedAdForRoomAdMemId = savedAdForRoomAdMemId;
		this.savedAdForRoomAdAdId = savedAdForRoomAdAdId;
	}
	public SavedAdForRoomAdBean(Integer id, Timestamp createTime, MemberBean savedAdForRoomAdMemId,
			RoomRentBean savedAdForRoomAdAdId) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.savedAdForRoomAdMemId = savedAdForRoomAdMemId;
		this.savedAdForRoomAdAdId = savedAdForRoomAdAdId;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public MemberBean getSavedAdForRoomAdMemId() {
		return savedAdForRoomAdMemId;
	}
	public void setSavedAdForRoomAdMemId(MemberBean savedAdForRoomAdMemId) {
		this.savedAdForRoomAdMemId = savedAdForRoomAdMemId;
	}
	public RoomRentBean getSavedAdForRoomAdAdId() {
		return savedAdForRoomAdAdId;
	}
	public void setSavedAdForRoomAdAdId(RoomRentBean savedAdForRoomAdAdId) {
		this.savedAdForRoomAdAdId = savedAdForRoomAdAdId;
	}
	
}

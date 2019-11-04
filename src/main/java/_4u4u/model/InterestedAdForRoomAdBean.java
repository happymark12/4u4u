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
@Table(name = "InterestedAdForRoomAd",uniqueConstraints = {@UniqueConstraint(columnNames = {"fk_member_memId","fk_roomRent_adId"})})
public class InterestedAdForRoomAdBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Timestamp createTime;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_member_memId")
	private MemberBean interestedAdForRoomAdMemId;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_roomRent_adId")
	private RoomRentBean interestedAdForRoomAdAdId;
	
	public InterestedAdForRoomAdBean() {
		super();
	}
	public InterestedAdForRoomAdBean(Timestamp createTime, MemberBean interestedAdForRoomAdMemId,
			RoomRentBean interestedAdForRoomAdAdId) {
		super();
		this.createTime = createTime;
		this.interestedAdForRoomAdMemId = interestedAdForRoomAdMemId;
		this.interestedAdForRoomAdAdId = interestedAdForRoomAdAdId;
	}
	public InterestedAdForRoomAdBean(Integer id, Timestamp createTime, MemberBean interestedAdForRoomAdMemId,
			RoomRentBean interestedAdForRoomAdAdId) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.interestedAdForRoomAdMemId = interestedAdForRoomAdMemId;
		this.interestedAdForRoomAdAdId = interestedAdForRoomAdAdId;
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
	public MemberBean getInterestedAdForRoomAdMemId() {
		return interestedAdForRoomAdMemId;
	}
	public void setInterestedAdForRoomAdMemId(MemberBean interestedAdForRoomAdMemId) {
		this.interestedAdForRoomAdMemId = interestedAdForRoomAdMemId;
	}
	public RoomRentBean getInterestedAdForRoomAdAdId() {
		return interestedAdForRoomAdAdId;
	}
	public void setInterestedAdForRoomAdAdId(RoomRentBean interestedAdForRoomAdAdId) {
		this.interestedAdForRoomAdAdId = interestedAdForRoomAdAdId;
	}
	
}

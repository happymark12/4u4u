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
@Table(name = "InterestedAdForWantedRoomAd",uniqueConstraints = {@UniqueConstraint(columnNames = {"fk_member_memId","fk_wantedRoomAd_findRoomId"})})
public class InterestedAdForWantedRoomAdBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Timestamp createTime;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_member_memId")
	private MemberBean interestedAdForWantedRoomAdMemId;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_wantedRoomAd_findRoomId")
	private WantedRoomBean interestedAdForWantedRoomAdFindRoomId;
	
	public InterestedAdForWantedRoomAdBean() {
		super();
	}
	public InterestedAdForWantedRoomAdBean(Timestamp createTime, MemberBean interestedAdForWantedRoomAdMemId,
			WantedRoomBean interestedAdForWantedRoomAdFindRoomId) {
		super();
		this.createTime = createTime;
		this.interestedAdForWantedRoomAdMemId = interestedAdForWantedRoomAdMemId;
		this.interestedAdForWantedRoomAdFindRoomId = interestedAdForWantedRoomAdFindRoomId;
	}
	public InterestedAdForWantedRoomAdBean(Integer id, Timestamp createTime,
			MemberBean interestedAdForWantedRoomAdMemId, WantedRoomBean interestedAdForWantedRoomAdFindRoomId) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.interestedAdForWantedRoomAdMemId = interestedAdForWantedRoomAdMemId;
		this.interestedAdForWantedRoomAdFindRoomId = interestedAdForWantedRoomAdFindRoomId;
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
	public MemberBean getInterestedAdForWantedRoomAdMemId() {
		return interestedAdForWantedRoomAdMemId;
	}
	public void setInterestedAdForWantedRoomAdMemId(MemberBean interestedAdForWantedRoomAdMemId) {
		this.interestedAdForWantedRoomAdMemId = interestedAdForWantedRoomAdMemId;
	}
	public WantedRoomBean getInterestedAdForWantedRoomAdFindRoomId() {
		return interestedAdForWantedRoomAdFindRoomId;
	}
	public void setInterestedAdForWantedRoomAdFindRoomId(WantedRoomBean interestedAdForWantedRoomAdFindRoomId) {
		this.interestedAdForWantedRoomAdFindRoomId = interestedAdForWantedRoomAdFindRoomId;
	}
	
}

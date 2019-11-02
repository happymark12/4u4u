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
@Table(name = "SavedAdForWantedRoomAd",uniqueConstraints = {@UniqueConstraint(columnNames = {"fk_member_memId","fk_wantedRoomAd_findRoomId"})})
public class SavedAdForWantedRoomAdBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Timestamp createTime;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_member_memId")
	private MemberBean savedAdForWantedRoomAdMemId;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_wantedRoomAd_findRoomId")
	private WantedRoomBean savedAdForWantedRoomAdFindRoomId;
	
	public SavedAdForWantedRoomAdBean() {
		super();
	}
	public SavedAdForWantedRoomAdBean(Timestamp createTime, MemberBean savedAdForWantedRoomAdMemId,
			WantedRoomBean savedAdForWantedRoomAdFindRoomId) {
		super();
		this.createTime = createTime;
		this.savedAdForWantedRoomAdMemId = savedAdForWantedRoomAdMemId;
		this.savedAdForWantedRoomAdFindRoomId = savedAdForWantedRoomAdFindRoomId;
	}
	public SavedAdForWantedRoomAdBean(Integer id, Timestamp createTime, MemberBean savedAdForWantedRoomAdMemId,
			WantedRoomBean savedAdForWantedRoomAdFindRoomId) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.savedAdForWantedRoomAdMemId = savedAdForWantedRoomAdMemId;
		this.savedAdForWantedRoomAdFindRoomId = savedAdForWantedRoomAdFindRoomId;
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
	public MemberBean getSavedAdForWantedRoomAdMemId() {
		return savedAdForWantedRoomAdMemId;
	}
	public void setSavedAdForWantedRoomAdMemId(MemberBean savedAdForWantedRoomAdMemId) {
		this.savedAdForWantedRoomAdMemId = savedAdForWantedRoomAdMemId;
	}
	public WantedRoomBean getSavedAdForWantedRoomAdFindRoomId() {
		return savedAdForWantedRoomAdFindRoomId;
	}
	public void setSavedAdForWantedRoomAdFindRoomId(WantedRoomBean savedAdForWantedRoomAdFindRoomId) {
		this.savedAdForWantedRoomAdFindRoomId = savedAdForWantedRoomAdFindRoomId;
	}
	
}

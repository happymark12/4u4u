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

@Entity
@Table(name = "AccusationForWantedRoomAd")
public class AccusationForWantedRoomAdBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer accusationId;
	private String accusationContents;
	private Timestamp accusationTime;
	private Boolean hashandling;
	private String handlingContents;
	private Timestamp handlingTime;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="fk_member_memId")
	private MemberBean accusationForWantedRoomAdMemId;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="fk_roomAd_findRoomId")
	private RoomRentBean accusationForWantedRoomAdFindRoomId;
	
	public AccusationForWantedRoomAdBean() {
		super();
	}
	public AccusationForWantedRoomAdBean(String accusationContents, Timestamp accusationTime, Boolean hashandling,
			String handlingContents, Timestamp handlingTime, MemberBean accusationForWantedRoomAdMemId,
			RoomRentBean accusationForWantedRoomAdFindRoomId) {
		super();
		this.accusationContents = accusationContents;
		this.accusationTime = accusationTime;
		this.hashandling = hashandling;
		this.handlingContents = handlingContents;
		this.handlingTime = handlingTime;
		this.accusationForWantedRoomAdMemId = accusationForWantedRoomAdMemId;
		this.accusationForWantedRoomAdFindRoomId = accusationForWantedRoomAdFindRoomId;
	}
	public AccusationForWantedRoomAdBean(Integer accusationId, String accusationContents, Timestamp accusationTime,
			Boolean hashandling, String handlingContents, Timestamp handlingTime,
			MemberBean accusationForWantedRoomAdMemId, RoomRentBean accusationForWantedRoomAdFindRoomId) {
		super();
		this.accusationId = accusationId;
		this.accusationContents = accusationContents;
		this.accusationTime = accusationTime;
		this.hashandling = hashandling;
		this.handlingContents = handlingContents;
		this.handlingTime = handlingTime;
		this.accusationForWantedRoomAdMemId = accusationForWantedRoomAdMemId;
		this.accusationForWantedRoomAdFindRoomId = accusationForWantedRoomAdFindRoomId;
	}
	
	public Integer getAccusationId() {
		return accusationId;
	}
	public void setAccusationId(Integer accusationId) {
		this.accusationId = accusationId;
	}
	public String getAccusationContents() {
		return accusationContents;
	}
	public void setAccusationContents(String accusationContents) {
		this.accusationContents = accusationContents;
	}
	public Timestamp getAccusationTime() {
		return accusationTime;
	}
	public void setAccusationTime(Timestamp accusationTime) {
		this.accusationTime = accusationTime;
	}
	public Boolean getHashandling() {
		return hashandling;
	}
	public void setHashandling(Boolean hashandling) {
		this.hashandling = hashandling;
	}
	public String getHandlingContents() {
		return handlingContents;
	}
	public void setHandlingContents(String handlingContents) {
		this.handlingContents = handlingContents;
	}
	public Timestamp getHandlingTime() {
		return handlingTime;
	}
	public void setHandlingTime(Timestamp handlingTime) {
		this.handlingTime = handlingTime;
	}
	public MemberBean getAccusationForWantedRoomAdMemId() {
		return accusationForWantedRoomAdMemId;
	}
	public void setAccusationForWantedRoomAdMemId(MemberBean accusationForWantedRoomAdMemId) {
		this.accusationForWantedRoomAdMemId = accusationForWantedRoomAdMemId;
	}
	public RoomRentBean getAccusationForWantedRoomAdFindRoomId() {
		return accusationForWantedRoomAdFindRoomId;
	}
	public void setAccusationForWantedRoomAdFindRoomId(RoomRentBean accusationForWantedRoomAdFindRoomId) {
		this.accusationForWantedRoomAdFindRoomId = accusationForWantedRoomAdFindRoomId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

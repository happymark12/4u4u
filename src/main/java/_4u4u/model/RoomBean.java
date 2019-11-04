package _4u4u.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="Room")
public class RoomBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer roomId;
	String 	roomType;
	String 	rentFloor;
	String	rentTotalFloor; 
	Boolean hasBalcony;
	Boolean hasDuplex; //樓中樓
	String area;
	Boolean hasWash;
	Boolean hasIceBox;
	Boolean has4; //第四台
	Boolean hasGas; 
	Boolean hasTV; 
	Boolean hasWardrobe; 
	Boolean hasSofa; 
	Boolean hasHeater; 
	Boolean hasBroadBand; 
	Boolean hasDesk; 
	Boolean hasChair; 
	Boolean hasSingleBed; 
	Boolean hasDoubleBed; 
	Boolean hasColdAir; 
	Integer rentPrice; 
	String deposit; 
	Boolean allowCook; 
	Boolean	roomState;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="fk_roomAd_adId")
	RoomRentBean roomAd;
	
	public RoomBean() {
		
	}

	public RoomBean(String roomType, String rentFloor, String rentTotalFloor, Boolean hasBalcony, Boolean hasDuplex,
			String area, Boolean hasWash, Boolean hasIceBox, Boolean has4, Boolean hasGas, Boolean hasTV,
			Boolean hasWardrobe, Boolean hasSofa, Boolean hasHeater, Boolean hasBroadBand, Boolean hasDesk,
			Boolean hasChair, Boolean hasSingleBed, Boolean hasDoubleBed, Boolean hasColdAir, Integer rentPrice,
			String deposit, Boolean allowCook, Boolean roomState, RoomRentBean roomAd) {
		super();
		this.roomType = roomType;
		this.rentFloor = rentFloor;
		this.rentTotalFloor = rentTotalFloor;
		this.hasBalcony = hasBalcony;
		this.hasDuplex = hasDuplex;
		this.area = area;
		this.hasWash = hasWash;
		this.hasIceBox = hasIceBox;
		this.has4 = has4;
		this.hasGas = hasGas;
		this.hasTV = hasTV;
		this.hasWardrobe = hasWardrobe;
		this.hasSofa = hasSofa;
		this.hasHeater = hasHeater;
		this.hasBroadBand = hasBroadBand;
		this.hasDesk = hasDesk;
		this.hasChair = hasChair;
		this.hasSingleBed = hasSingleBed;
		this.hasDoubleBed = hasDoubleBed;
		this.hasColdAir = hasColdAir;
		this.rentPrice = rentPrice;
		this.deposit = deposit;
		this.allowCook = allowCook;
		this.roomState = roomState;
		this.roomAd = roomAd;
	}

	public RoomBean(Integer roomId, String roomType, String rentFloor, String rentTotalFloor, Boolean hasBalcony,
			Boolean hasDuplex, String area, Boolean hasWash, Boolean hasIceBox, Boolean has4, Boolean hasGas,
			Boolean hasTV, Boolean hasWardrobe, Boolean hasSofa, Boolean hasHeater, Boolean hasBroadBand,
			Boolean hasDesk, Boolean hasChair, Boolean hasSingleBed, Boolean hasDoubleBed, Boolean hasColdAir,
			Integer rentPrice, String deposit, Boolean allowCook, Boolean roomState, RoomRentBean roomRentAd) {
		super();
		this.roomId = roomId;
		this.roomType = roomType;
		this.rentFloor = rentFloor;
		this.rentTotalFloor = rentTotalFloor;
		this.hasBalcony = hasBalcony;
		this.hasDuplex = hasDuplex;
		this.area = area;
		this.hasWash = hasWash;
		this.hasIceBox = hasIceBox;
		this.has4 = has4;
		this.hasGas = hasGas;
		this.hasTV = hasTV;
		this.hasWardrobe = hasWardrobe;
		this.hasSofa = hasSofa;
		this.hasHeater = hasHeater;
		this.hasBroadBand = hasBroadBand;
		this.hasDesk = hasDesk;
		this.hasChair = hasChair;
		this.hasSingleBed = hasSingleBed;
		this.hasDoubleBed = hasDoubleBed;
		this.hasColdAir = hasColdAir;
		this.rentPrice = rentPrice;
		this.deposit = deposit;
		this.allowCook = allowCook;
		this.roomState = roomState;
		this.roomAd = roomRentAd;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getRentFloor() {
		return rentFloor;
	}

	public void setRentFloor(String rentFloor) {
		this.rentFloor = rentFloor;
	}

	public String getRentTotalFloor() {
		return rentTotalFloor;
	}

	public void setRentTotalFloor(String rentTotalFloor) {
		this.rentTotalFloor = rentTotalFloor;
	}

	public Boolean getHasBalcony() {
		return hasBalcony;
	}

	public void setHasBalcony(Boolean hasBalcony) {
		this.hasBalcony = hasBalcony;
	}

	public Boolean getHasDuplex() {
		return hasDuplex;
	}

	public void setHasDuplex(Boolean hasDuplex) {
		this.hasDuplex = hasDuplex;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Boolean getHasWash() {
		return hasWash;
	}

	public void setHasWash(Boolean hasWash) {
		this.hasWash = hasWash;
	}

	public Boolean getHasIceBox() {
		return hasIceBox;
	}

	public void setHasIceBox(Boolean hasIceBox) {
		this.hasIceBox = hasIceBox;
	}

	public Boolean getHas4() {
		return has4;
	}

	public void setHas4(Boolean has4) {
		this.has4 = has4;
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

	public Boolean getHasSofa() {
		return hasSofa;
	}

	public void setHasSofa(Boolean hasSofa) {
		this.hasSofa = hasSofa;
	}

	public Boolean getHasHeater() {
		return hasHeater;
	}

	public void setHasHeater(Boolean hasHeater) {
		this.hasHeater = hasHeater;
	}

	public Boolean getHasBroadBand() {
		return hasBroadBand;
	}

	public void setHasBroadBand(Boolean hasBroadBand) {
		this.hasBroadBand = hasBroadBand;
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

	public Integer getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(Integer rentPrice) {
		this.rentPrice = rentPrice;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public Boolean getAllowCook() {
		return allowCook;
	}

	public void setAllowCook(Boolean allowCook) {
		this.allowCook = allowCook;
	}

	public Boolean getRoomState() {
		return roomState;
	}

	public void setRoomState(Boolean roomState) {
		this.roomState = roomState;
	}

	public RoomRentBean getRoomRentAd() {
		return roomAd;
	}

	public void setRoomRentAd(RoomRentBean roomRentAd) {
		this.roomAd = roomRentAd;
	}

	public RoomRentBean getRoomAd() {
		return roomAd;
	}
	
	public void setRoomAd(RoomRentBean roomAd) {
		this.roomAd = roomAd;
	}
	
}

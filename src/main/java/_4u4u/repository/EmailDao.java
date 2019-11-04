package _4u4u.repository;

import _4u4u.model.RoomRentBean;
import _4u4u.model.WantedRoomBean;

public interface EmailDao {

	public boolean needSendEmail(RoomRentBean roomRentBean); // RoomRentAd是否要寄信

	public boolean needSendEmail(WantedRoomBean wantedRoomBean); // WantedRoomAd是否要寄信

	public void sendMailAddOne(RoomRentBean roomRentBean); // RoomRentAd的寄送EMail數 + 1

	public void sendMailAddOne(WantedRoomBean wantedRoomBean); // WantedRoomAd的寄送EMail數 + 1

}
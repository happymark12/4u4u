package _4u4u.service;

import _4u4u.model.RoomRentBean;
import _4u4u.model.WantedRoomBean;

public interface EmailService {

	public void sendMatchEmail(RoomRentBean roomRentBean);

	public void sendMatchEmail(WantedRoomBean wantedRoomBean);

}
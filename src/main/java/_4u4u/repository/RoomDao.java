package _4u4u.repository;

import java.util.List;

import _4u4u.model.RoomBean;
import _4u4u.model.RoomRentBean;

public interface RoomDao {
	
	
	List<RoomBean> getRoomsByFk(RoomRentBean roomAd) ;
	
	int saveRoom(RoomBean bean);
	
	int updateRoom(RoomBean bean);

//	void setConnection(Connection con);

	
}
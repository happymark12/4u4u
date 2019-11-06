package _4u4u.service;

import java.util.List;

import _4u4u.model.RoomBean;
import _4u4u.model.RoomRentBean;


public interface RoomService {
	List<RoomBean> getRoomsByFk(RoomRentBean roomAd);
	
	int saveRoom(RoomBean roomBean); // =新增房間
	
	int updateRoom(RoomBean bean);	// 更新房間
	
}

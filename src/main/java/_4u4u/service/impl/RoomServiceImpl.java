package _4u4u.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import _4u4u.model.RoomBean;
import _4u4u.repository.RoomDao;
import _4u4u.service.RoomService;
@Transactional
@Service
public class RoomServiceImpl implements Serializable, RoomService {
	
	private static final long serialVersionUID = 1L;
	private RoomDao dao;
	@Autowired
	public void setDao(RoomDao dao) {
		this.dao = dao;
	}

	// Constructor
	public RoomServiceImpl() {
//		dao = new RoomDaoImpl();
	}

//	@Override
//	public List<RoomBean> getRoomsByAdId(int adId) {
//		return dao.getRoomsByAdId(adId);
//	}

	@Override
	public int saveRoom(RoomBean roomBean) {
		
		dao.saveRoom(roomBean);
		
		return 0;
	}

	
}
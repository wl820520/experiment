package com.justh5.experiment.service.impl;

import com.justh5.experiment.dao.HotellDao;
import com.justh5.experiment.mapper.HotelMapper;
import com.justh5.experiment.model.HotelModel;
import com.justh5.experiment.model.RoomModel;
import com.justh5.experiment.model.UserModel;
import com.justh5.experiment.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotellDao hotellDao;

    @Override
    public List<HotelModel> getHotelList() {
        return hotellDao.getHotelList();
    }

    @Override
    public List<RoomModel> getRoomListByHotelId(Integer hotelid) {
        return hotellDao.getRoomListByHotelId(hotelid);
    }

    @Override
    public Integer addHotel(HotelModel hotelModel) {
        return hotellDao.addhotel(hotelModel);
    }

    @Override
    public Integer addRoom(RoomModel roomModel) {
        return hotellDao.addroom(roomModel);
    }

    @Override
    public List<RoomModel> getHotRoomList() {
        return hotellDao.getHotRoom();
    }

    @Override
    public List<Integer> getCount() {
        return hotellDao.getCount();
    }

    @Override
    public Integer delHotel(Integer id) {
        return hotellDao.delhotel(id);
    }

    @Override
    public Integer delRoom(Integer id) {
        return hotellDao.delroom(id);
    }

    @Override
    public HotelModel getHotelById(Integer id) {
        return hotellDao.getHotelById(id);
    }

    @Override
    public Integer updateHotel(HotelModel hotelModel) {
        return hotellDao.updatehotel(hotelModel);
    }
}

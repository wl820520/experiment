package com.justh5.experiment.service;

import com.justh5.experiment.model.HotelModel;
import com.justh5.experiment.model.RoomModel;

import java.util.List;

public interface HotelService {
    List<HotelModel> getHotelList();
    List<RoomModel> getRoomListByHotelId(Integer hotelid);
    Integer addHotel(HotelModel hotelModel);
    Integer addRoom(RoomModel roomModel);
    List<RoomModel> getHotRoomList();
    List<Integer> getCount();
    Integer delHotel(Integer id);
    Integer delRoom(Integer id);
    HotelModel getHotelById(Integer id);
    Integer updateHotel(HotelModel hotelModel);
}

package com.justh5.experiment.dao;
import com.justh5.experiment.mapper.HotelMapper;
import com.justh5.experiment.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HotellDao {
    @Autowired
    private HotelMapper hotelMapper;
    public UserModel getUserModelByID(Integer uid){
        return hotelMapper.getUserModelByID(uid);
    }
    public UserModel getUserModelByName(String loginname){
        return hotelMapper.getUserModelByName(loginname);
    }
    public List<HotelModel> getHotelList(){
        return hotelMapper.getHotelList();
    }
    public List<RoomModel> getRoomListByHotelId(Integer hotelid){

        return hotelMapper.getRoomListByHotelId(hotelid);
    }
    public HotelModel getHotelById(Integer id){
        return hotelMapper.getHotelById(id);
    }
    public List<UserModel> getUserList(){
        return hotelMapper.getUserList();
    }
    public List<FeedBackModel> getFeedList(){
        return hotelMapper.getFeedList();
    }
    public List<OrderModel> getOrderList(){
        return hotelMapper.getOrderList();
    }
    public OrderModel getOrderById(Integer id){
        return hotelMapper.getOrderById(id);
    }
    public List<OrderModel> getOrderListByUid(Integer uid){
        return hotelMapper.getOrderListByUid(uid);
    }
    public OrderModel getOrderByUserId(Integer uid){
        return hotelMapper.getOrderByUserId(uid);
    }
    public OrderModel getNewOrder(){
        return hotelMapper.getNewOrder();
    }
    public Integer adduser(UserModel userModel){
        return hotelMapper.add(userModel);
    }
    public Integer addhotel(HotelModel hotelModel){
            return hotelMapper.addhotel(hotelModel);
    }
    public Integer updatehotel(HotelModel hotelModel){
            return  hotelMapper.updateHotel(hotelModel);
    }
    public Integer addroom(RoomModel roomModel){
        return hotelMapper.addroom(roomModel);
    }
    public Integer addfeedback(FeedBackModel feedBackModel){
        return hotelMapper.addfeedback(feedBackModel);
    }
    public Integer addorder(OrderModel orderModel){
        return hotelMapper.addorder(orderModel);
    }
    public List<RoomModel> getHotRoom(){
        return hotelMapper.getHotRoom();
    }
    public List<Integer> getCount(){return hotelMapper.getCount();}
    public Integer deluser(Integer id){
        return hotelMapper.delUserById(id);
    }
    public Integer delhotel(Integer id){
        return hotelMapper.delHotelById(id);
    }
    public Integer delroom(Integer id){
        return hotelMapper.delRoomById(id);
    }
    public Integer delorder(Integer id){
        return hotelMapper.delOrderById(id);
    }
    public Integer updateOrderStatus(Integer id){
        return hotelMapper.updateOrderStatus(id);
    }
    public Integer updateRoomStatus(Integer id){
        return hotelMapper.updateRoomStatus(id);
    }
}

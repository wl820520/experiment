package com.justh5.experiment.mapper;

import com.justh5.experiment.model.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HotelMapper {
    @Select("select *from `user` where id =#{id}")
    UserModel getUserModelByID(@Param("id") Integer uid);
    @Select("select *from `user` where loginname =#{loginname}")
    UserModel getUserModelByName(@Param("loginname") String loginname);
    @Select("select *from `user` where isdelete=0")
    List<UserModel> getUserList();
    @Select("select*from feedback a left join user b where a.uid=b.id")
    List<FeedBackModel> getFeedList();
    @Select("select *from experiment where isdelete=0")
    List<HotelModel> getHotelList();
    @Select("select *from experiment where isdelete=0 and id=#{id}")
    HotelModel getHotelById(@Param("id") Integer id);
    @Select("select *from `order` where isdelete<>1")
    List<OrderModel> getOrderList();
    @Select("select *from `order` where isdelete<>1 and id=#{id}")
    OrderModel getOrderById(Integer id);
    @Select("select *from `order` where isdelete<>1 and userid=#{userid}")
    List<OrderModel> getOrderListByUid(@Param("userid") Integer userid);
    @Select("select *from room where isdelete=0 and hotelid=#{hotelid}")
    List<RoomModel> getRoomListByHotelId(@Param("hotelid") Integer hotelid);
    @Select("select *from `order` where isdelete<>1 and uid=#{uid}")
    OrderModel getOrderByUserId(@Param("uid") Integer uid);
    @Select("select *from `order` where isdelete<>1 and userid<>0 and starttime is not NULL and starttime <>'' order by id desc limit 1")
    OrderModel getNewOrder();
    @Insert("insert into `user`(loginname,loginpwd,username,userphone,createtime,isdelete) values(#{loginname},#{loginpwd},#{username},#{userphone},NOW(),0)")
    Integer add(UserModel user);

    @Insert("insert into `experiment`(hotelname,address,pic,createtime,isdelete,ishot,note) values(#{hotelname},#{address},#{pic},NOW(),0,#{ishot},#{note})")
    Integer addhotel(HotelModel hotelModel);

    @Insert("insert into `room`(roomname,hotelid,pic,roomcode,price,timeprice,roomstatus,createtime,isdelete,ishot,note) values(#{roomname},#{hotelid},#{pic},#{roomcode},#{price},#{timeprice},#{roomstatus},NOW(),0,#{ishot},#{note})")
    Integer addroom(RoomModel roomModel);

    @Insert("insert into `feedback`(content,createtime,isdelete,uid,username,email) values(#{content},NOW(),0,#{uid},#{username},#{email})")
    Integer addfeedback(FeedBackModel feedBackModel);

    @Insert("insert into `order`(orderserial,hotelid,roomid,userid,createtime,isdelete,starttime,endtime,price,timeprice,hotelname,roomname) values(#{orderserial},#{hotelid},#{roomid},#{userid},NOW(),0,#{starttime},#{endtime},#{price},#{timeprice},#{hotelname},#{roomname})")
    Integer addorder(OrderModel orderModel);
    @Select("select *from `room` where isdelete=0 and ishot=1")
    List<RoomModel> getHotRoom();
    @Select("(select count(0) from `room` where roomstatus=1) UNION ALL (SELECT count(0) FROM `room`) UNION  ALL(select count(0) from `experiment`) UNION  ALL(select count(0) from `user`)")
    List<Integer> getCount();
    @Select("update `order` set isdelete=1 where id=#{id}")
    Integer delOrderById(@Param("id") Integer id);
    @Select("update `room` set isdelete=1 where id=#{id}")
    Integer delRoomById(@Param("id") Integer id);
    @Select("update `experiment` set isdelete=1 where id=#{id}")
    Integer delHotelById(@Param("id") Integer id);
    @Select("update `user` set isdelete=1 where id=#{id}")
    Integer delUserById(@Param("id") Integer id);
    @Select("update `order` set isdelete=2 where id=#{id}")
    Integer updateOrderStatus(@Param("id") Integer id);
    @Select("update `order` set roomstatus=0 where id=#{id}")
    Integer updateRoomStatus(@Param("id") Integer id);
    @Select("update `experiment` set hotelname=#{hotelname} , address=#{address} , pic=#{pic} , ishot=#{ishot} , note=#{note} where id=#{id}")
    Integer updateHotel(HotelModel hotelModel);
}

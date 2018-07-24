package com.justh5.experiment.model;


import java.util.Timer;

public class OrderModel {
    private Integer id;
    private Integer hotelid;
    private String orderserial;
    private Integer roomid;
    private Integer userid;
    private String starttime;
    private String endtime;

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    private Integer isdelete;

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    private String roomname;
    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    private String hotelname;

    public Integer getId() {
        return id;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHotelid() {
        return hotelid;
    }

    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }

    public String getOrderserial() {
        return orderserial;
    }

    public void setOrderserial(String orderserial) {
        this.orderserial = orderserial;
    }

    public Integer getRoomid() {
        return roomid;
    }

    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTimeprice() {
        return timeprice;
    }

    public void setTimeprice(String timeprice) {
        this.timeprice = timeprice;
    }

    private Double price;
    private String timeprice;
}

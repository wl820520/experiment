package com.justh5.experiment.model;

import java.math.BigDecimal;

public class RoomModel {
    private Integer id;
    private String roomname;
    private Integer hotelid;
    private String roomcode;
    private Integer price;
    private String pic;
    private Integer ishot;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    private String note;

    public Integer getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(Integer roomtype) {
        this.roomtype = roomtype;
    }

    private Integer roomtype;
    public Integer getRoomstatus() {
        return roomstatus;
    }

    public void setRoomstatus(Integer roomstatus) {
        this.roomstatus = roomstatus;
    }

    private Integer roomstatus;
    public String getTimeprice() {
        return timeprice;
    }

    public void setTimeprice(String timeprice) {
        this.timeprice = timeprice;
    }

    private String timeprice;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getIshot() {
        return ishot;
    }

    public void setIshot(Integer ishot) {
        this.ishot = ishot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public Integer getHotelid() {
        return hotelid;
    }

    public void setHotelid(Integer hotelid) {
        this.hotelid = hotelid;
    }

    public String getRoomcode() {
        return roomcode;
    }

    public void setRoomcode(String roomcode) {
        this.roomcode = roomcode;
    }



    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}

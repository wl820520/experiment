package com.justh5.experiment.controller;

import com.justh5.experiment.domain.SysResult;
import com.justh5.experiment.domain.CommonHelper;
import com.justh5.experiment.model.OrderModel;
import com.justh5.experiment.model.RoomModel;
import com.justh5.experiment.model.UserModel;
import com.justh5.experiment.service.HotelService;
import com.justh5.experiment.service.OrderService;
import com.justh5.experiment.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    private static Logger logger = LogManager.getLogger(HotelController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @RequestMapping(value="getOrderList", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SysResult getOrderList() throws SQLException {
        List<OrderModel> userList= orderService.getOrderList();
        return new SysResult(1, "success",userList);
    }
    @RequestMapping(value="getNewOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SysResult getNewOrder() throws SQLException {
        OrderModel order= orderService.getNewOrder();
        if(order!=null){
            UserModel userModel= userService.getUserModelById(order.getUserid());
            if(userModel!=null){
                NewOrder newOrder=new NewOrder();
                newOrder.username=userModel.getUsername();
                newOrder.ordername=order.getRoomname()==""?order.getHotelname():order.getRoomname();
                newOrder.endtime=order.getEndtime();
                newOrder.starttime=order.getStarttime();
                return new SysResult(1, "success",newOrder);
            }
        }
        return new SysResult(99, "success");
    }
    private class NewOrder{
        public String username;
        public String ordername;
        public  String starttime;
        public String endtime;
    }
    @RequestMapping(value="getOrderListByUid", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SysResult getOrderListByUid(Integer uid) throws SQLException {
        List<OrderModel> userList= orderService.getOrderListByUid(uid);
        return new SysResult(1, "success",userList);
    }
    @RequestMapping(value="updateOrderStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SysResult setOrder(Integer id) throws SQLException {
        OrderModel orderModel=orderService.getOrderById(id);
        if(orderModel!=null) {
            orderService.updateOrderStatus(id);
            if(orderModel.getRoomid()>0) {
                orderService.updateRoomStatus(orderModel.getRoomid());
            }
            return new SysResult(1, "success");
        }
        return new SysResult(99, "fail");
    }
    @RequestMapping(value="addorder", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SysResult getuser(String hotelname,Integer hotelid,String roomname,String starttime,String endtime,Integer userid,Integer roomid) throws SQLException {
        OrderModel orderModel=new OrderModel();
        orderModel.setHotelid(hotelid==null?0:hotelid);
        orderModel.setHotelname(hotelname);
        orderModel.setStarttime(starttime);
        orderModel.setEndtime(endtime);
        orderModel.setOrderserial(CommonHelper.getRandomString(20));
        orderModel.setUserid(userid);
        orderModel.setRoomid(roomid==null?0:roomid);
        orderModel.setRoomname(roomname);
        Integer oid = orderService.addOrder(orderModel);
        if(oid>0) {
            return new SysResult(1, "success");
        }else{
            return new SysResult(99, "fail");
        }
    }
}

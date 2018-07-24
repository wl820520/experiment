package com.justh5.experiment.service;

import com.justh5.experiment.model.OrderModel;

import java.util.List;

public interface OrderService {
    Integer addOrder(OrderModel orderModel);
    List<OrderModel> getOrderList();
    Integer delOrder(Integer id);
    List<OrderModel> getOrderListByUid(int uid);
    OrderModel getOrderById(int id);
    OrderModel getNewOrder();
    Integer updateOrderStatus(Integer id);
    Integer updateRoomStatus(Integer id);
}

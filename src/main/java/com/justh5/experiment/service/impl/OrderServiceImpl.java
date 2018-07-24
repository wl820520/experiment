package com.justh5.experiment.service.impl;

import com.justh5.experiment.dao.HotellDao;
import com.justh5.experiment.model.OrderModel;
import com.justh5.experiment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private HotellDao hotellDao;

    @Override
    public Integer addOrder(OrderModel orderModel) {
        return hotellDao.addorder(orderModel);
    }

    @Override
    public List<OrderModel> getOrderList() {
        return hotellDao.getOrderList();
    }

    @Override
    public Integer delOrder(Integer id) {
        return hotellDao.delorder(id);
    }

    @Override
    public List<OrderModel> getOrderListByUid(int uid) {
        return hotellDao.getOrderListByUid(uid);
    }

    @Override
    public OrderModel getOrderById(int id) {
        return hotellDao.getOrderById(id);
    }

    @Override
    public OrderModel getNewOrder() {
        return hotellDao.getNewOrder();
    }

    @Override
    public Integer updateOrderStatus(Integer id) {
        return hotellDao.updateOrderStatus(id);
    }

    @Override
    public Integer updateRoomStatus(Integer id) {
        return hotellDao.updateRoomStatus(id);
    }
}

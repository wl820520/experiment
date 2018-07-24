package com.justh5.experiment.service.impl;

import com.justh5.experiment.dao.HotellDao;
import com.justh5.experiment.model.UserModel;
import com.justh5.experiment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private HotellDao hotellDao;

    @Override
    public UserModel getUserModelById(Integer id) {
        return hotellDao.getUserModelByID(id);
    }

    @Override
    public Integer addUser(UserModel userModel) {
        return hotellDao.adduser(userModel);
    }

    @Override
    public UserModel getUserByName(String name) {
        return hotellDao.getUserModelByName(name);
    }

    @Override
    public List<UserModel> getUserList() {
        return hotellDao.getUserList();
    }

    @Override
    public Integer delUser(Integer id) {
        return hotellDao.deluser(id);
    }
}

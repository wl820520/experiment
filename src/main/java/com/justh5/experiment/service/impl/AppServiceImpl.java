package com.justh5.experiment.service.impl;

import com.justh5.experiment.mapper.ExperimentMapper;
import com.justh5.experiment.model.UserModel;
import com.justh5.experiment.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppServiceImpl implements AppService {
    @Autowired
    private ExperimentMapper experimentMapper;
    @Override
    public UserModel getUserByUserName(String username) {
        return experimentMapper.getUserModelByName(username);
    }

    @Override
    public UserModel getUserByUserCode(String usercode) {
        return experimentMapper.getUserModelByCode(usercode);
    }

    @Override
    public UserModel getUserByUserSign(String usersign) {
        return experimentMapper.getUserModelBySign(usersign);
    }

    @Override
    public void insertUser(UserModel userModel) {
        experimentMapper.insertUser(userModel);
    }
}

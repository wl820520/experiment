package com.justh5.experiment.service;

import com.justh5.experiment.model.ExAnswerEntity;
import com.justh5.experiment.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public interface AppService {
    UserModel getUserByUserName(String username);
    UserModel getUserByUserCode(String usercode);
    UserModel getUserByUserSign(String usersign);
    UserModel getUserById(Integer id);
    void  insertUser(UserModel userModel);
    void insertExAnswerEntity(ExAnswerEntity exAnswerEntity);
}

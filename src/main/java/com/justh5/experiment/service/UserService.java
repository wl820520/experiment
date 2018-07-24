package com.justh5.experiment.service;

import com.justh5.experiment.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel getUserModelById(Integer id);
    Integer addUser(UserModel userModel);
    UserModel getUserByName(String name);
    List<UserModel> getUserList();
    Integer delUser(Integer id);
}

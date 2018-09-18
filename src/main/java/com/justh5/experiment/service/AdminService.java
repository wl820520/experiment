package com.justh5.experiment.service;

import com.justh5.experiment.model.ExClassEntity;
import com.justh5.experiment.model.UserModel;

import java.util.List;

public interface AdminService {
    UserModel getAdminUser();
    List<UserModel> getUserList();
    void updateUser(UserModel userModel);
    String getDeviceValue(String ipaddress,String devType);
    List<ExClassEntity> getClassList();
    ExClassEntity getClassById(Integer id);
    void insertClassModel(ExClassEntity exClassEntity);
    void updateClassModel(ExClassEntity exClassEntity);
    void delClass(Integer id);
}

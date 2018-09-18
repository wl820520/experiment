package com.justh5.experiment.domain;

import com.justh5.experiment.model.ExMainEntity;
import com.justh5.experiment.model.UserModel;

public class LoginResultResp {
    UserModel userModel;
    ExMainEntity exMainEntity;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public ExMainEntity getExMainEntity() {
        return exMainEntity;
    }

    public void setExMainEntity(ExMainEntity exMainEntity) {
        this.exMainEntity = exMainEntity;
    }
}

package com.justh5.experiment.domain;

import com.justh5.experiment.model.ExMainAndStationEntity;
import com.justh5.experiment.model.UserModel;
import lombok.Data;

@Data
public class LoginResultResp {
    UserModel userModel;
    ExMainAndStationEntity exMainEntity;
    private String token;

}

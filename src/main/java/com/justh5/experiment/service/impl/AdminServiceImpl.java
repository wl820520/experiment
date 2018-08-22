package com.justh5.experiment.service.impl;

import com.justh5.experiment.mapper.ExperimentMapper;
import com.justh5.experiment.model.UserModel;
import com.justh5.experiment.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private ExperimentMapper experimentMapper;

    @Override
    public UserModel getAdminUser() {
        return experimentMapper.getUserModelByID(1);
    }
}

package com.justh5.experiment.service.impl;

import com.justh5.experiment.dao.HotellDao;
import com.justh5.experiment.model.FeedBackModel;
import com.justh5.experiment.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private HotellDao hotellDao;

    @Override
    public Integer addFeedBack(FeedBackModel feedBackModel) {
        return hotellDao.addfeedback(feedBackModel);
    }
}

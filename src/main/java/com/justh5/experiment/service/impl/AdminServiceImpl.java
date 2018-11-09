package com.justh5.experiment.service.impl;

import com.justh5.experiment.mapper.ExperimentMapper;
import com.justh5.experiment.model.ExClassEntity;
import com.justh5.experiment.model.UserModel;
import com.justh5.experiment.service.AdminService;
import com.youxinpai.common.util.web.ResponseResult;
import com.youxinpai.common.util.web.http.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private ExperimentMapper experimentMapper;

    @Override
    public UserModel getAdminUser() {
        return experimentMapper.getUserModelByID(1);
    }

    @Override
    public List<UserModel> getUserList() {
        return experimentMapper.getUserList();
    }

    @Override
    public void updateUser(UserModel userModel) {
        experimentMapper.updateUser(userModel);
    }

    @Override
    public String getDeviceValue(String ipaddress, String devType) {
        try {
            ResponseResult responseResult = HttpClientUtils.doGet("http://123.56.255.65:503/getval?gettype="+devType+"&getip="+ipaddress);
            if(responseResult.getCode()==0) {
                if (!StringUtils.isEmpty(responseResult.getData())) {
                    return responseResult.getData().toString();
                }
            }else{
                return responseResult.getMsg();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ExClassEntity> getClassList() {
        return experimentMapper.getClassList();
    }

    @Override
    public ExClassEntity getClassById(Integer id) {
        return experimentMapper.getClassById(id);
    }

    @Override
    public void insertClassModel(ExClassEntity exClassEntity) {
        experimentMapper.insertClass(exClassEntity);
    }

    @Override
    public void updateClassModel(ExClassEntity exClassEntity) {
        experimentMapper.updateClass(exClassEntity);
    }

    @Override
    public void delClass(Integer id) {
        experimentMapper.delClass(id);
    }

    @Override
    public void delUser(Integer id) {
        experimentMapper.delUser(id);
    }
}

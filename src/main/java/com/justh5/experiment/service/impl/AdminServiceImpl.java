package com.justh5.experiment.service.impl;

import com.justh5.experiment.mapper.ExperimentMapper;
import com.justh5.experiment.model.ExClassEntity;
import com.justh5.experiment.model.UserModel;
import com.justh5.experiment.service.AdminService;
import com.youxinpai.common.util.web.ResponseResult;
import com.youxinpai.common.util.web.http.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private ExperimentMapper experimentMapper;

    @Value("${nodeServer}")
    String nodeServer;

    @Override
    public String getDeviceValue(String ipaddress, String devType) {
        try {
            ResponseResult responseResult = HttpClientUtils.doGet(nodeServer+"getval?gettype="+devType+"&getip="+ipaddress);
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

}

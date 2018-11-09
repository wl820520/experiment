package com.justh5.experiment.service.impl;

import com.justh5.experiment.mapper.ExperimentMapper;
import com.justh5.experiment.model.ExAnswerEntity;
import com.justh5.experiment.model.UserModel;
import com.justh5.experiment.service.AppService;
import com.justh5.experiment.util.CommonHelper;
import com.youxinpai.common.util.utils.ConvertUtil;
import com.youxinpai.common.util.web.ResponseResult;
import com.youxinpai.common.util.web.http.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.util.HashMap;
import java.util.Map;

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
    public UserModel getUserById(Integer id) {
        return experimentMapper.getUserModelByID(id);
    }

    @Override
    public void insertUser(UserModel userModel) {
        experimentMapper.insertUser(userModel);
    }

    @Override
    public void insertExAnswerEntity(ExAnswerEntity exAnswerEntity) {
        experimentMapper.insertExAnswerEntity(exAnswerEntity);
    }

    @Override
    public Integer getCountAnswer() {
        return experimentMapper.getCountAnswer();
    }

    @Override
    public void sendSMS() {
        ResponseResult responseResult = new ResponseResult();
        try {
            String smsYxpMd5Key = "VWl3a0xBejA0UHp6aDFkaUtoa24=";
            String smsUxinMd5Key = "Jl5ISjh3aGdocyVAISlkMDMzNA==";
            String smsUxinServerUrl = "http://api.xin.com/api/send_sms";
            Map<String, String> uxinMap = new HashMap<>();
            uxinMap.put("accesskey", "SMS_YXP");
            String test="";
            for(int i=1;i<10;i++){test+= CommonHelper.getRandomChar();}
            uxinMap.put("content", test);
            // uxinMap.put("mobile", "13548784873");
            //uxinMap.put("mobile", "13810827971");
            uxinMap.put("mobile", "15802534166");
            uxinMap.put("src", "yxp");//业务表示
            uxinMap.put("ip", "127.0.0.1");
            String key = smsYxpMd5Key;
            BASE64Decoder decoder = new BASE64Decoder();
            key = new String(decoder.decodeBuffer(key), "UTF-8");
            String reqStr = ConvertUtil.getUrlParamsByMap(uxinMap);
            String sign = com.youxinpai.common.util.encryption.MD5Util.MD5Encrypt(reqStr + key);
            uxinMap.put("sn", sign);
            responseResult = HttpClientUtils.doPostReq(smsUxinServerUrl, uxinMap);
            String aa="ssss";
        } catch (Exception ex) {

        }

    }
}

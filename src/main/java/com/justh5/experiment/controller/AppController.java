package com.justh5.experiment.controller;

import com.justh5.experiment.domain.LoginResultResp;
import com.justh5.experiment.domain.RegistReq;
import com.justh5.experiment.domain.ResultReq;
import com.justh5.experiment.domain.SysResult;
import com.justh5.experiment.model.*;
import com.justh5.experiment.service.AppService;
import com.justh5.experiment.service.ExMainService;
import com.justh5.experiment.util.HTTPUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("app")
public class AppController {
    private static Logger logger = LogManager.getLogger(AppController.class);
    @Autowired
    private AppService appService;
    @Autowired
    private ExMainService exMainService;
    @RequestMapping(value = "/login")
    public SysResult login(String username, String password,String serialid) {
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            return new SysResult(99, "用户名或密码不能为空",null);
        }
        if(StringUtils.isEmpty(serialid)){
            return new SysResult(99, "平板号不能为空",null);
        }
        ExStationEntity exStationEntity=exMainService.getExStationBySerialId(serialid);
        if(exStationEntity==null){
            logger.error("平板未绑定试题"+serialid);
            return new SysResult(99, "平板未绑定试题",null);
        }
        UserModel userModel=appService.getUserByUserName(username);
        if(userModel!=null&&userModel.getId()>0&&userModel.getUsername().trim().equals(username.trim())&&userModel.getLoginpwd().trim().equals(password.trim())){
            ExMainEntity exMainEntity= exMainService.getExMainById(exStationEntity.getMainid());
            if(exMainEntity!=null) {
                return new SysResult(1, "success", exMainEntity);
            }else{
                return new SysResult(99, "未找到试题",null);
            }
        }
        return new SysResult(99, "用户名或密码错误",null);
    }
    @RequestMapping(value = "/logincode")
    public SysResult logincode(String usercode,String serialid) {
        if(StringUtils.isEmpty(usercode)){
            return new SysResult(99, "学号不能为空",null);
        }
        if(StringUtils.isEmpty(serialid)){
            return new SysResult(99, "平板号不能为空",null);
        }
        ExStationEntity exStationEntity=exMainService.getExStationBySerialId(serialid);
        if(exStationEntity==null){
            logger.error("平板未绑定试题"+serialid);
            return new SysResult(99, "平板未绑定试题",null);
        }
        UserModel userModel=appService.getUserByUserCode(usercode);
        LoginResultResp loginResultResp=new LoginResultResp();
        if(userModel!=null&&userModel.getId()>0){
            if(userModel.getUsertype().equals(3)){
                return new SysResult(99, "请扫码注册后再进行实验",null);
            }
            loginResultResp.setUserModel(userModel);
            ExMainEntity exMainEntity= exMainService.getExMainById(exStationEntity.getMainid());
            loginResultResp.setExMainEntity(exMainEntity);
            if(exMainEntity!=null) {
                return new SysResult(1, "success", loginResultResp);
            }else{
                return new SysResult(99, "未找到试题",null);
            }
        }
        return new SysResult(99, "学号错误",null);
    }
    @RequestMapping(value = "/loginsign")
    public SysResult loginsign(String loginsign,String serialid) {
        if(StringUtils.isEmpty(loginsign)){
            return new SysResult(99, "授权码不能为空",null);
        }
        if(StringUtils.isEmpty(serialid)){
            return new SysResult(99, "平板号不能为空",null);
        }
        ExStationEntity exStationEntity=exMainService.getExStationBySerialId(serialid);
        if(exStationEntity==null){
            logger.error("平板未绑定试题"+serialid);
            return new SysResult(99, "平板未绑定试题",null);
        }
        UserModel userModel=appService.getUserByUserSign(loginsign);
        if(userModel!=null&&userModel.getId()>0){
            ExMainEntity exMainEntity= exMainService.getExMainById(exStationEntity.getMainid());
            if(exMainEntity!=null) {
                return new SysResult(1, "success", exMainEntity);
            }else{
                return new SysResult(99, "未找到试题",null);
            }
        }
        return new SysResult(99, "授权码错误",null);
    }
    @RequestMapping(value = "/register")
    @ResponseBody
    public SysResult register(@RequestBody RegistReq registReq) {
        if(registReq!=null&&!StringUtils.isEmpty(registReq.getUserCode())){
            UserModel userModel=new UserModel();
            userModel.setIsdelete(0);
            userModel.setUsertype(0);
            userModel.setLoginname(registReq.getLoginName());
            userModel.setLoginpwd(registReq.getLoginPwd());
            userModel.setUsername(registReq.getUserName());
            userModel.setFacepic(registReq.getUserFace());
            appService.insertUser(userModel);
            return new SysResult(1, "success",null);
        }else{
            return new SysResult(99, "参数错误",null);
        }
    }
    @RequestMapping(value = "/isClassOrver")
    @ResponseBody
    public SysResult isClassOrver() {
        ExOnlineEntity exOnlineEntity=exMainService.getExOnline();
        Integer status=exOnlineEntity==null?0:exOnlineEntity.getEx_status();
        return new SysResult(1, "success",status);
    }
    @RequestMapping(value = "/uploadResult")
    @ResponseBody
    public SysResult uploadExperiment(String req,String serialid) {
        //req="{\"score\":80,\"userid\":1,\"jsonResultModel\": {\"preview\":[{\"id\":1,\"answer\":[{\"name\":\"A\"}]},{\"id\":2,\"answer\":[{\"name\":\"C\"},{\"name\":\"D\"}]}],\"formal\":{\"previewModels\":[{\"id\":1,\"answer\":[{\"name\":\"A\"}]}],\"txtModels\":[{\"id\":2,\"value\":540}],\"tableModels\":[{\"id\":3,\"tableSubModels\":[[{\"id\":1,\"value\":200},{\"id\":2,\"value\":300},{\"id\":3,\"value\":200}]]}]}}}";
        //JsonResultModel jsonResultModel= exMainService.getResultData();
        try {
            if (StringUtils.isEmpty(req)) {
                return new SysResult(99, "参数不能为空", null);
            }
            if (StringUtils.isEmpty(serialid)) {
                return new SysResult(99, "平板号不能为空", null);
            }
            ExStationEntity exStationEntity = exMainService.getExStationBySerialId(serialid);
            logger.info("提交结果json:" + req);
            ResultReq jsonResultModel = JSON.parseObject(req, ResultReq.class);
            ExOnlineEntity exOnlineEntity = exMainService.getExOnline();
            if (exOnlineEntity != null && exOnlineEntity.getEx_status().equals(1)) {
                if (jsonResultModel != null && jsonResultModel.getJsonResultModel() != null && jsonResultModel.getUserid() != null && jsonResultModel.getUserid() > 0) {
                    ExAnswerEntity exAnswerEntity = new ExAnswerEntity();
                    exAnswerEntity.setAnswer(JSON.toJSONString(jsonResultModel.getJsonResultModel()));
                    exAnswerEntity.setUser_id(jsonResultModel.getUserid());
                    exAnswerEntity.setScore(jsonResultModel.getScore());
                    exAnswerEntity.setMain_id(exStationEntity.getMainid());
                    exAnswerEntity.setStation_id(exStationEntity.getId());
                    exAnswerEntity.setCreate_time(new Date().getTime());
                    appService.insertExAnswerEntity(exAnswerEntity);
                } else {
                    return new SysResult(99, "用户或答卷不能为空", null);
                }
            } else {
                return new SysResult(99, "已经结课，不能提交答卷", null);
            }
            return new SysResult(1, "success", null);
        }catch (Exception ex){
            ex.printStackTrace();
            logger.error("提交答卷异常：",ex);
            return new SysResult(99, "提交答卷异常", null);
        }
    }
    @RequestMapping(value = "/getEquipmentData")
    @ResponseBody
    public SysResult getEquipmentData(String userid,String equipmentcode,String serialid) {
        try {
            if (StringUtils.isEmpty(userid)||StringUtils.isEmpty(equipmentcode)) {
                return new SysResult(99, "用户和设备号不能为空", null);
            }
            if (StringUtils.isEmpty(serialid)) {
                return new SysResult(99, "平板号不能为空", null);
            }
            logger.info("获取设备值:userid " + userid+" code "+equipmentcode+" serialid "+serialid);
            ExStationEntity exStationEntity = exMainService.getExStationBySerialId(serialid);
            if(exStationEntity==null){
                logger.error("平板未绑定试题"+serialid);
                return new SysResult(99, "平板未绑定试题",null);
            }
            Map<String, Object> params=new HashMap<>();
            params.put("getdevname",exStationEntity.getEx_code());
            params.put("gettype",equipmentcode);
            String result=  HTTPUtil.httpClientGet("http://123.56.255.65:503/getval",params,"utf-8");
            logger.info("获取设备信息：dev "+exStationEntity.getEx_code()+"  type"+equipmentcode+"  "+result);
            return new SysResult(1, "success", result);
        }catch (Exception ex){
            ex.printStackTrace();
            logger.error("提交答卷异常：",ex);
            return new SysResult(99, "提交答卷异常", null);
        }
    }

}

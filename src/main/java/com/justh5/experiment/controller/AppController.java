package com.justh5.experiment.controller;

import com.alibaba.fastjson.JSONObject;
import com.justh5.experiment.domain.*;
import com.justh5.experiment.mapper.ExperimentMapper;
import com.justh5.experiment.model.*;
import com.justh5.experiment.service.AppService;
import com.justh5.experiment.service.ExMainService;
import com.justh5.experiment.socketservice.SocketServer;
import com.justh5.experiment.util.CacheManager;
import com.justh5.experiment.util.HTTPUtil;
import com.justh5.experiment.util.JwtTokenUtil;
import com.youxinpai.common.util.web.ResponseResult;
import org.apache.camel.util.Time;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.*;

@RestController
@RequestMapping("app")
public class AppController {
    private static Logger logger = LogManager.getLogger(AppController.class);
    @Autowired
    private AppService appService;
    @Autowired
    private SocketServer socketServer;
    @Autowired
    private ExMainService exMainService;
    @Autowired
    private ExperimentMapper experimentMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${imgServer}")
    String imgServer;
    @Value("${deviceServer}")
    String deviceServer;

    @Value("${nodeServer}")
    String nodeServer;

    @Value("${versionpath}")
    String versionpath;
    @RequestMapping(value = "/test")
    public SysResult test(String aa) {
        //exMainService.GetTransferData("time","C1:PAVA PER,1.00E-03s");
        //exMainService.GetTransferData("freq","C1:PAVA FREQ,1.00E+03Hz");
        //exMainService.GetTransferData("freq","C1:PAVA FREQ,****Hz");
        if(StringUtils.isEmpty(aa)){
           aa="C1:PAVA MAX,1.40E+00V";
        }
        String bb= exMainService.TestTransferData("voltage",aa);
        return new SysResult(0, "", bb);
    }
    @RequestMapping(value = "/logincode")
    public SysResult logincode(String usercode,String serialid) {
        if(StringUtils.isEmpty(usercode)){
            return new SysResult(99, "学号不能为空",null);
        }
        if(StringUtils.isEmpty(serialid)){
            return new SysResult(99, "平板号不能为空",null);
        }
        UserModel userModel=experimentMapper.getUserModelByCode(usercode);
        if(userModel!=null&&userModel.getId()>0){
            return logindata(userModel,serialid);
        }
        return new SysResult(99, "学号错误",null);
    }
    @RequestMapping(value = "/loginface")
    public SysResult loginface(HttpServletRequest request,String serialid) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> fileList = multipartRequest.getFiles("pic");
        if (fileList == null || fileList.size() == 0) {
            return new SysResult(99, "文件错误");
        }
        MultipartFile file = fileList.get(0);
        try {
            Map<String, MultipartFile> map = new HashMap<>();
            map.put("file", file);
            ResponseResult responseResult = HTTPUtil.doFormFilePostReq(imgServer+"compere", map, null);
            logger.info("登录识别人脸返回：" + responseResult);
            if (responseResult.getCode()==200) {
                JSONObject values = JSON.parseObject(responseResult.getData().toString());
                if (values.containsKey("code") && values.getInteger("code") == 1) {
                    Integer uid=values.getInteger("data");
                    UserModel userModel = experimentMapper.getUserModelByID(uid);
                    if (userModel != null && userModel.getId() > 0) {
                        return logindata(userModel,serialid);
                    }else{
                        return new SysResult(99, "未找到学生", null);
                    }
                } else {
                    logger.info("人脸识别错误"+responseResult.getMsg());
                    return new SysResult(99, "人脸识别失败", null);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
            logger.error("人脸识别异常：",ex);
        }
        return new SysResult(99, "人脸登录错误",null);
    }
    private SysResult logindata(UserModel userModel,String serialid){
        LoginResultResp loginResultResp = new LoginResultResp();
//        if (userModel.getUsertype().equals(3)) {
//            return new SysResult(99, "请扫码注册后再进行实验", null);
//        }
        ExOnlineEntity exOnlineEntity = experimentMapper.getExOnline(userModel.getTeacherid());
        if (exOnlineEntity == null || exOnlineEntity.getEx_status().equals(0)) {
            return new SysResult(99, "未到上课时间点，不能操作系统",null);
        }
        loginResultResp.setUserModel(userModel);
        ExMainAndStationEntity exMainEntity = experimentMapper.getExMainStationBySerialIdAndTeacherId(serialid,userModel.getTeacherid());
        loginResultResp.setExMainEntity(exMainEntity);
        if (exMainEntity != null) {
            Integer usedcount = experimentMapper.getUserdAnswer(userModel.getId(), exMainEntity.getId());
            if (usedcount != null && usedcount > 0) {
                return new SysResult(99, "您已提交过该实验", null);
            }
            Cache cache=new Cache();
            cache.setKey(userModel.getUsername());
            cache.setValue(userModel);
            cache.setTimeOut(Time.minutes(5L).toMillis());
            String token = jwtTokenUtil.generateToken(userModel);
            CacheManager.putCache(token,cache);
            loginResultResp.setToken(token);
            experimentMapper.loginUser(new Date().getTime(),userModel.getId());
            return new SysResult(1, "success", loginResultResp);
        } else {
            return new SysResult(99, "未找到试题，请联系老师绑定试题", null);
        }
    }
    @RequestMapping(value = "/loginsign")
    public SysResult loginsign(String loginsign,String serialid) {
        if(StringUtils.isEmpty(loginsign)){
            return new SysResult(99, "授权码不能为空",null);
        }
        if(StringUtils.isEmpty(serialid)){
            return new SysResult(99, "平板号不能为空",null);
        }
        UserModel userModel=experimentMapper.getUserModelBySign(loginsign);
        if(userModel!=null&&userModel.getId()>0){
            ExMainAndStationEntity exStationEntity=experimentMapper.getExMainStationBySerialIdAndTeacherId(serialid,userModel.getTeacherid());
            if(exStationEntity==null){
                logger.error("平板未绑定试题"+serialid);
                return new SysResult(99, "平板未绑定试题",null);
            }
            ExMainEntity exMainEntity= experimentMapper.getExMainById(exStationEntity.getMain_id());
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
            experimentMapper.insertUser(userModel);
            return new SysResult(1, "success",null);
        }else{
            return new SysResult(99, "参数错误",null);
        }
    }
    @RequestMapping(value = "/uploadResult")
    @ResponseBody
    public SysResult uploadExperiment(HttpServletRequest request,Integer score, String userid,String serialid,String newjson) {
        //req="{\"score\":80,\"userid\":1,\"jsonResultModel\": {\"preview\":[{\"id\":1,\"answer\":[{\"name\":\"A\"}]},{\"id\":2,\"answer\":[{\"name\":\"C\"},{\"name\":\"D\"}]}],\"formal\":{\"previewModels\":[{\"id\":1,\"answer\":[{\"name\":\"A\"}]}],\"txtModels\":[{\"id\":2,\"value\":540}],\"tableModels\":[{\"id\":3,\"tableSubModels\":[[{\"id\":1,\"value\":200},{\"id\":2,\"value\":300},{\"id\":3,\"value\":200}]]}]}}}";
        //JsonResultModel jsonResultModel= exMainService.getResultData();
        try {
            if (StringUtils.isEmpty(serialid)) {
                return new SysResult(99, "平板号不能为空", null);
            }
            Integer uid=0;
            if(!StringUtils.isEmpty(userid)){
                uid=Integer.parseInt(userid);
            }
            UserModel userModel=getUserModel(request.getHeader("token").toString(),uid);
            if(userModel==null){
                return new SysResult(99, "token失效或用户id不能为空", null);
            }
            ExMainAndStationEntity exStationEntity = experimentMapper.getExMainStationBySerialIdAndTeacherId(serialid,userModel.getTeacherid());
            if(exStationEntity==null){
                return new SysResult(99, "平板号未绑定实验台"+serialid, null);
            }
            ExOnlineEntity exOnlineEntity = experimentMapper.getExOnline(userModel.getTeacherid());
            Integer count=0;
            if (exOnlineEntity != null && exOnlineEntity.getEx_status().equals(1)) {
                ExAnswerEntity exAnswerEntity = new ExAnswerEntity();
                exAnswerEntity.setAnswer(newjson);
                exAnswerEntity.setTeacher_id(userModel.getTeacherid());
                exAnswerEntity.setUser_id(userModel.getId());
                exAnswerEntity.setScore(score);
                exAnswerEntity.setMain_id(exStationEntity.getMain_id());
                exAnswerEntity.setStation_id(exStationEntity.getId());
                exAnswerEntity.setCreate_time(userModel.getLogintime());
                exAnswerEntity.setEnd_time(new Date().getTime());
                exAnswerEntity.setTeacher_id(userModel.getTeacherid());
                experimentMapper.insertExAnswerEntity(exAnswerEntity);
                count= experimentMapper.getCountAnswer();
            } else {
                return new SysResult(99, "已经结课，不能提交答卷",null);
            }
            return new SysResult(1, "success",  count<=10?1:0);
        }catch (Exception ex){
            ex.printStackTrace();
            logger.error("提交答卷异常：",ex);
            return new SysResult(99, "提交答卷异常", null);
        }
    }

    @RequestMapping(value = "/isClassOrver")
    @ResponseBody
    public SysResult isClassOrver(HttpServletRequest request,Integer uid) {
        UserModel userModel=getUserModel(request.getHeader("token").toString(),uid);
        if(userModel!=null) {
            ExOnlineEntity exOnlineEntity = experimentMapper.getExOnline(userModel.getTeacherid());
            Integer status = exOnlineEntity == null ? 0 : exOnlineEntity.getEx_status();
            return new SysResult(1, "success", status);
        }else{
            return new SysResult(99, "token失效，请重新登录", null);
        }
    }
    @RequestMapping(value = "/getEquipmentData")
    @ResponseBody
    public SysResult getEquipmentData(HttpServletRequest request,String userid,String equipmentcode,String serialid) {
        try {
            if (StringUtils.isEmpty(equipmentcode)) {
                return new SysResult(99, "设备号不能为空", null);
            }
            if (StringUtils.isEmpty(serialid)) {
                return new SysResult(99, "平板号不能为空", null);
            }
            Integer uid=0;
            if(!StringUtils.isEmpty(userid)){
                uid=Integer.parseInt(userid);
            }
            UserModel userModel= getUserModel(request.getHeader("token"),uid);
            if(userModel==null){
                return new SysResult(99, "未登录或token失效", null);
            }
            logger.info("获取设备值:userid " + userid+" code "+equipmentcode+" serialid "+serialid);
            ExMainAndStationEntity exStationEntity = experimentMapper.getExMainStationBySerialIdAndTeacherId(serialid,userModel.getTeacherid());
            if(exStationEntity==null){
                logger.error("平板未绑定试题"+serialid);
                return new SysResult(99, "平板未绑定试题",null);
            }
            Map<String, Object> params=new HashMap<>();
            params.put("getdevname",exStationEntity.getEx_code());
            params.put("gettype",equipmentcode);
            String result=  HTTPUtil.httpClientGet(nodeServer+"getval",params,"utf-8");
            logger.info("获取设备信息：dev "+exStationEntity.getEx_code()+"  type"+equipmentcode+"  "+result);
            return new SysResult(1, "success", result);
        }catch (Exception ex){
            ex.printStackTrace();
            logger.error("获取设备信息异常：",ex);
            return new SysResult(99, "获取设备信息异常", null);
        }

    }
    @RequestMapping(value = "/uploadResultFile")
    public SysResult uploadResultFile(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> fileList = multipartRequest.getFiles("file");
        if (fileList == null || fileList.size() == 0) {
            return new SysResult(99, "请提交文件", null);
        }
        MultipartFile file = fileList.get(0);
        UploadFileReq uploadFileReq=getfilereq(request);
        if(uploadFileReq!=null&&!StringUtils.isEmpty(uploadFileReq.getUserid())){
            if (StringUtils.isEmpty(uploadFileReq.getSerialid())) {
                return new SysResult(99, "平板号不能为空", null);
            }
            UserModel userModel=exMainService.getUserInfoByToken(request.getHeader("token").toString());
            if(userModel==null) {
                userModel = experimentMapper.getUserModelByCode(uploadFileReq.getUserid());
                if(userModel==null){
                    return new SysResult(99, "未找到绑定用户,或token失效", null);
                }
            }
            ExMainAndStationEntity exStationEntity = experimentMapper.getExMainStationBySerialIdAndTeacherId(uploadFileReq.getSerialid(),userModel.getTeacherid());
            if(exStationEntity==null){
                return new SysResult(99, "未找到绑定平板号", null);
            }
            ExOnlineEntity exOnlineEntity = experimentMapper.getExOnline(userModel.getTeacherid());
            Integer count=0;
            try {
                if (exOnlineEntity != null && exOnlineEntity.getEx_status().equals(1)) {
                    InputStream in= file.getInputStream();
                    byte b[]=new byte[(int)file.getSize()];     //创建合适文件大小的数组
                    in.read(b);    //读取文件中的内容到b[]数组
                    in.close();
                    String answer=new String(b);
                    ExAnswerEntity exAnswerEntity = new ExAnswerEntity();
                    exAnswerEntity.setAnswer(answer);
                    //System.out.println("答案："+answer);
                    exAnswerEntity.setUser_id(userModel.getId());
                    exAnswerEntity.setScore(uploadFileReq.getScore());
                    exAnswerEntity.setMain_id(exStationEntity.getMain_id());
                    exAnswerEntity.setStation_id(exStationEntity.getId());
                    exAnswerEntity.setCreate_time(userModel.getLogintime());
                    exAnswerEntity.setEnd_time(new Date().getTime());
                    count = experimentMapper.getCountAnswer();
                    count=count==null?0:count;
                    exAnswerEntity.setIsaddscore(0);
                    if(exOnlineEntity.getBonus_num()!=null&&count<exOnlineEntity.getBonus_num()){
                        exAnswerEntity.setIsaddscore(1);
                        exAnswerEntity.setScore(exAnswerEntity.getScore()+2);
                    }
                    experimentMapper.insertExAnswerEntity(exAnswerEntity);
                    //System.out.println("提交答案成功");
                    ResultResp resultResp=new ResultResp();
                    resultResp.setRank(count+1);
                    resultResp.setIsbonus(exAnswerEntity.getIsaddscore());
                    return new SysResult(1, "success", resultResp );
                } else {
                    return new SysResult(99, "已经结课，不能提交答卷", null);
                }
            }catch (Exception ex){
                return new SysResult(99, "数据转化异常", null);
            }
        }else{
            return new SysResult(99, "请上传用户信息");
        }
    }
    private UploadFileReq getfilereq(HttpServletRequest request){
        UploadFileReq uploadFileReq=new UploadFileReq();
        try {
            Map<String, String[]> map = request.getParameterMap();
            if (map != null && map.size() > 0) {
                String jsonStr = "";
                for (String[] val : map.values()) {
                    for (String v : val) {
                        jsonStr =v;
                    }
                }
                if (!StringUtils.isEmpty(jsonStr)) {
                    uploadFileReq = JSON.parseObject(URLDecoder.decode(jsonStr), UploadFileReq.class);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return uploadFileReq;
    }

    private UserModel getUserModel(String token,Integer userid){
        UserModel userModel=exMainService.getUserInfoByToken(token);
        if(userModel==null) {
            userModel=experimentMapper.getUserModelByID(userid);
        }
        return userModel;
    }
    @RequestMapping(value = "/getversion")
    @ResponseBody
    public SysResult getversion(HttpServletRequest request,Integer uid) {
        UserModel userModel=getUserModel(request.getHeader("token"),uid);
        if(userModel==null){
            return new SysResult(99, "token失效");
        }
        ExOnlineEntity exOnlineEntity=experimentMapper.getExOnline(userModel.getTeacherid());
        if(exOnlineEntity!=null) {
            return new SysResult(1, "success", exOnlineEntity.getVersion());
        }else{
            return new SysResult(99, "版本信息读取失败");
        }
    }
    @RequestMapping(value = "/updateversion")
    @ResponseBody
    public SysResult updateversion(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> fileList = multipartRequest.getFiles("file");
        if (fileList == null || fileList.size() == 0) {
            return new SysResult(99, "请提交版本文件", null);
        }
        MultipartFile file = fileList.get(0);
        Integer version=0;
        try {
            Map<String, String[]> map = request.getParameterMap();
            if (map != null && map.size() > 0) {
                String jsonStr = "";
                for (String[] val : map.values()) {
                    for (String v : val) {
                        jsonStr =v;// CommonHelper.getUTF8StringFromGBKString(v);
                        //System.out.println(jsonStr);
                    }
                }
                if (!StringUtils.isEmpty(jsonStr)) {
                    version=Integer.parseInt(jsonStr);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ExVersionEntity exVersionEntity=experimentMapper.getVersion();
        if(version!=null&&version>0&&exVersionEntity!=null) {
            if(version> exVersionEntity.getVersion()) {
                if(uploadapk(file,0)) {
                    experimentMapper.updateVersion(version);
                    return new SysResult(1, "success", null);
                }else{
                    return new SysResult(99, "上传文件失败");
                }
            }else{
                return new SysResult(99, "版本号必须大于当前版本号："+exVersionEntity.getVersion());
            }
        }else{
            return new SysResult(99, "请输入正确的版本号");
        }
    }
    @RequestMapping(value = "/updatecsversion")
    @ResponseBody
    public SysResult updatecsversion(HttpServletRequest request) {
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> fileList = multipartRequest.getFiles("file");
            MultipartFile file = fileList.get(0);
            uploadapk(file,1);
            return new SysResult(1, "success", null);
        }catch (Exception ex){
            logger.error(ex);
            return new SysResult(99, "error", null);
        }
    }

    @RequestMapping(value = "/getSOC")
    @ResponseBody
    public SysResult getSOC(String userid,String oSCCOde,String serialid,String type,String transferType) {
        Map<String,Object> map=new HashMap<>();
        map.put("userid",userid);
        map.put("oSCCOde",oSCCOde);
        map.put("serialid",serialid);
        map.put("type",type);
        map.put("transferType",transferType);
        String responseResult =   HTTPUtil.httpClientPost(deviceServer+"admin/getDeviceData",map,null);
        if (!StringUtils.isEmpty(responseResult)) {
            JSONObject values = JSON.parseObject(responseResult);
            if (values.containsKey("code") && values.getInteger("code") == 1) {
                return new SysResult(99, "", values.getString("data"));
            }
            return new SysResult(99, values.getString("msg"), null);
        }
        return new SysResult(99, "error", null);
    }


    private boolean uploadapk(MultipartFile file,Integer type)
    {
        try{
            String filepath=type==0?( versionpath+"experiment.apk"):(versionpath+"test.apk");
            File desFile = new File(filepath);
            if(desFile.exists()){
                desFile.delete();
            }
            file.transferTo(desFile);
        }catch (Exception ex){
            logger.error("保存文件异常：",ex);
            return false;
        }
        return true;
    }

    @RequestMapping(value = "updateuserpic")
    @ResponseBody
    public SysResult updateuserpic(String usercode,String facepic) {
        try {
            if (!StringUtils.isEmpty(usercode)) {
                UserModel userModel1=experimentMapper.getUserModelByCode(usercode);
                if(userModel1!=null) {
                    if (!StringUtils.isEmpty(facepic)) {
                        //if (!responseResult.contains("error")) {
                        experimentMapper.updateUserFacePic(userModel1.getId(), facepic);
                        Map<String, Object> map = new HashMap<>();
                        map.put("usercode", userModel1.getUsercode());
                        String responseResult = HTTPUtil.httpClientPost(imgServer+"refresh", map, null);
                        return new SysResult(1, "success", null);
                        //  }else {
                        // return new SysResult(99, "图片必须为人脸图片", null);
                        // }
                    } else {
                        return new SysResult(99, "图片不能为空", null);
                    }
                }
            } else {
                return new SysResult(99, "未找到用户code", null);
            }
        } catch (Exception ex) {
            logger.error("update user pic error", ex);
            return new SysResult(99, "update error", null);
        }
        return new SysResult(1, "success", null);
    }

}

package com.justh5.experiment.controller;

import com.justh5.experiment.domain.*;
import com.justh5.experiment.model.*;
import com.justh5.experiment.service.AppService;
import com.justh5.experiment.service.ExMainService;
import com.justh5.experiment.socketservice.SocketServer;
import com.justh5.experiment.util.HTTPUtil;
import com.youxinpai.common.util.utils.ConvertUtil;
import com.youxinpai.common.util.utils.JsonUtil;
import com.youxinpai.common.util.utils.YXPStringUtils;
import com.youxinpai.common.util.web.ResponseResult;
import com.youxinpai.common.util.web.http.HttpClientUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        ExOnlineEntity exOnlineEntity = exMainService.getExOnline();
        if (exOnlineEntity == null || exOnlineEntity.getEx_status().equals(0)) {
            return new SysResult(99, "未到上课时间点，不能操作系统",null);
        }
        UserModel userModel=appService.getUserByUserCode(usercode);
        LoginResultResp loginResultResp=new LoginResultResp();
        if(userModel!=null&&userModel.getId()>0){
            Integer usedcount=exMainService.getUsedAnswer(userModel.getId(),exStationEntity.getMainid());
            if(usedcount!=null&& usedcount>0){
                return new SysResult(99, "您已提交过该实验",null);
            }
            if(userModel.getUsertype().equals(3)){
                return new SysResult(99, "请扫码注册后再进行实验",null);
            }
            loginResultResp.setUserModel(userModel);
            ExMainEntity exMainEntity= exMainService.getExMainById(exStationEntity.getMainid());
            loginResultResp.setExMainEntity(exMainEntity);
            exMainService.loginUser(userModel.getId());
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
    @RequestMapping(value = "/uploadResult")
    @ResponseBody
    public SysResult uploadExperiment(Integer score, String userid,String serialid,String newjson) {
        //req="{\"score\":80,\"userid\":1,\"jsonResultModel\": {\"preview\":[{\"id\":1,\"answer\":[{\"name\":\"A\"}]},{\"id\":2,\"answer\":[{\"name\":\"C\"},{\"name\":\"D\"}]}],\"formal\":{\"previewModels\":[{\"id\":1,\"answer\":[{\"name\":\"A\"}]}],\"txtModels\":[{\"id\":2,\"value\":540}],\"tableModels\":[{\"id\":3,\"tableSubModels\":[[{\"id\":1,\"value\":200},{\"id\":2,\"value\":300},{\"id\":3,\"value\":200}]]}]}}}";
        //JsonResultModel jsonResultModel= exMainService.getResultData();
        try {

            if (StringUtils.isEmpty(serialid)) {
                return new SysResult(99, "平板号不能为空", null);
            }
            ExStationEntity exStationEntity = exMainService.getExStationBySerialId(serialid);
            if(exStationEntity==null){
                return new SysResult(99, "未找到绑定平板号", null);
            }
            ExOnlineEntity exOnlineEntity = exMainService.getExOnline();
            UserModel userModel=appService.getUserByUserCode(userid);
            if(userModel==null){
                return new SysResult(99, "未找到绑定用户", null);
            }
            Integer count=0;
            if (exOnlineEntity != null && exOnlineEntity.getEx_status().equals(1)) {
                ExAnswerEntity exAnswerEntity = new ExAnswerEntity();
                exAnswerEntity.setAnswer(newjson);
                exAnswerEntity.setUser_id(userModel.getId());
                exAnswerEntity.setScore(score);
                exAnswerEntity.setMain_id(exStationEntity.getMainid());
                exAnswerEntity.setStation_id(exStationEntity.getId());
                exAnswerEntity.setCreate_time(userModel.getLogintime());
                exAnswerEntity.setEnd_time(new Date().getTime());
                appService.insertExAnswerEntity(exAnswerEntity);
                count= appService.getCountAnswer();

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
    public SysResult isClassOrver() {
        ExOnlineEntity exOnlineEntity=exMainService.getExOnline();
        Integer status=exOnlineEntity==null?0:exOnlineEntity.getEx_status();
        return new SysResult(1, "success",status);
    }
    public String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
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
            ExStationEntity exStationEntity = exMainService.getExStationBySerialId(uploadFileReq.getSerialid());
            if(exStationEntity==null){
                return new SysResult(99, "未找到绑定平板号", null);
            }
            ExOnlineEntity exOnlineEntity = exMainService.getExOnline();
            UserModel userModel=appService.getUserByUserCode(uploadFileReq.getUserid());
            if(userModel==null){
                return new SysResult(99, "未找到绑定用户", null);
            }
            //System.out.println("上传内容"+JSON.toJSONString(uploadFileReq));
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
                    exAnswerEntity.setMain_id(exStationEntity.getMainid());
                    exAnswerEntity.setStation_id(exStationEntity.getId());
                    exAnswerEntity.setCreate_time(userModel.getLogintime());
                    exAnswerEntity.setEnd_time(new Date().getTime());
                    count = appService.getCountAnswer();
                    count=count==null?0:count;
                    exAnswerEntity.setIsaddscore(0);
                    if(exOnlineEntity.getBonus_num()!=null&&count<exOnlineEntity.getBonus_num()){
                        exAnswerEntity.setIsaddscore(1);
                        exAnswerEntity.setScore(exAnswerEntity.getScore()+2);
                    }
                    appService.insertExAnswerEntity(exAnswerEntity);
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

    @RequestMapping(value = "/getversion")
    @ResponseBody
    public SysResult getversion() {
        ExOnlineEntity exOnlineEntity=exMainService.getExOnline();
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
        ExOnlineEntity exOnlineEntity=exMainService.getExOnline();
        if(version!=null&&version>0&&exOnlineEntity!=null) {
            if(version> exOnlineEntity.getVersion()) {
                if(uploadapk(file,0)) {
                    exMainService.updateVersion(version);

                    return new SysResult(1, "success", null);
                }else{
                    return new SysResult(99, "上传文件失败");
                }
            }else{
                return new SysResult(99, "版本号必须大于当前版本号："+exOnlineEntity.getVersion());
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
        try {
            if(StringUtils.isEmpty(userid)||StringUtils.isEmpty(oSCCOde)||StringUtils.isEmpty(serialid)||StringUtils.isEmpty(type)||StringUtils.isEmpty(transferType)){
                return new SysResult(99, "参数不能为空 userid,oSCCOde,serialid,type,transferType", null);
            }
            OSCReq oscReq=new OSCReq();
            oscReq.setoSCCOde(oSCCOde);
            oscReq.setSerialid(serialid);
            oscReq.setType(type);
            oscReq.setTransferType(transferType);
            oscReq.setUserid(userid);
            //HttpClientUtils.doPost("");
            if (!StringUtils.isEmpty(oscReq.getUserid()) && !StringUtils.isEmpty(oscReq.getSerialid())) {
                if (socketServer.channelModel != null && socketServer.channelModel.getSocketChannel() != null && socketServer.channelModel.getSocketChannel().isConnected()) {
                    OSCResp oscResp = new OSCResp();
                    oscResp.setType(oscReq.getType());
                    ExStationEntity exStationEntity = exMainService.getExStationBySerialId(oscReq.getSerialid());
                    if (exStationEntity != null && !StringUtils.isEmpty(exStationEntity.getEx_osc())) {
                        oscResp.setDeviceName(exStationEntity.getEx_osc());
                        oscResp.setInstruct(oscReq.getoSCCOde());
                        ByteBuffer sBuffer = ByteBuffer.allocate(1024);
                        String str = JSON.toJSONString(oscResp);
                        logger.info("发送数据：" + str);
                        sBuffer = ByteBuffer.allocate(str.getBytes("UTF-8").length);
                        sBuffer.put(str.getBytes("UTF-8"));
                        sBuffer.flip();
                        try {
                            if (socketServer.channelModel.getSocketChannel().isConnected()) {
                                logger.info("write buffer");
                                //socketServer.channelModel.setResp("");
                                if(socketServer.channelModel.getOscRespModelList()!=null&&socketServer.channelModel.getOscRespModelList().size()>0){
                                    for(OSCRespModel oscRespModel:socketServer.channelModel.getOscRespModelList()){
                                        if(!StringUtils.isEmpty(oscRespModel.getDeviceName())&&!StringUtils.isEmpty(oscResp.getDeviceName())&& oscRespModel.getDeviceName().toLowerCase().equals(oscResp.getDeviceName().toLowerCase())){
                                            oscRespModel.setResp("");
                                        }
                                    }}
                                socketServer.channelModel.getSocketChannel().write(sBuffer);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            System.out.println(ex.getMessage());
                        }
                        int num=20;
                        if(oscResp.getType().equals("write")){
                            num=30;//写指令超时时间
                        }
                        for (int i = 0; i < num; i++) {
                            Thread.sleep(300);
                            if (socketServer.channelModel.getOscRespModelList() != null && socketServer.channelModel.getOscRespModelList().size() > 0) {
                                for (OSCRespModel oscRespModel : socketServer.channelModel.getOscRespModelList()) {
                                    if (!StringUtils.isEmpty(oscRespModel.getDeviceName()) && !StringUtils.isEmpty(oscResp.getDeviceName()) && oscRespModel.getDeviceName().toLowerCase().equals(oscResp.getDeviceName().toLowerCase())) {
                                        if (!StringUtils.isEmpty(oscRespModel.getResp())) {
                                            if (oscRespModel.getResp().contains("error")) {
                                                return new SysResult(1, "", oscRespModel.getResp());
                                            }
                                            String data = exMainService.GetTransferData(oscReq.getTransferType(), oscRespModel.getResp());
                                            logger.info("获取数据：" + oscRespModel.getResp() + "转换后：" + data);
                                            return new SysResult(1, "", data);
                                        }
                                    }
                                }
                            }
                        }
                        return new SysResult(99, "获取数据超时", null);
                    } else {
                        return new SysResult(99, "未设置设备信息", null);
                    }
                } else {
                    return new SysResult(99, "未连接设备", null);
                }
//                return new SysResult(1, "success", null);
            } else {
                return new SysResult(99, "参数不能为空", null);
            }
        } catch (Exception ex) {
            logger.error("获取示波器数据异常", ex);
            System.out.println(ex);
            return new SysResult(99, "error", null);
        }
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
}

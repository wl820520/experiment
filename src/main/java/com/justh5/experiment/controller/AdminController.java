package com.justh5.experiment.controller;

import com.alibaba.fastjson.JSON;
import com.justh5.experiment.config.WeChatMpConfig;
import com.justh5.experiment.domain.*;
import com.justh5.experiment.model.*;
import com.justh5.experiment.service.AdminService;
import com.justh5.experiment.service.AppService;
import com.justh5.experiment.service.ExMainService;
import com.justh5.experiment.socketservice.SocketServer;
import com.justh5.experiment.util.HTTPUtil;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("admin")
public class AdminController {
    private static Logger logger = LogManager.getLogger(AdminController.class);
    @Value("${urlpath}")
    String urlpath;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AppService appService;
    @Autowired
    private ExMainService exMainService;
    @Autowired
    private WeChatMpConfig weChatMpConfig;
    @Autowired
    private SocketServer socketServer;

    @CrossOrigin
    @RequestMapping("index")
    public Object jumpToView(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("html/admin/index");
    }

    @RequestMapping(value = "/login")
    public SysResult login(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return new SysResult(99, "用户名或密码不能为空", null);
        }
        UserModel userModel = adminService.getAdminUser();
        if (userModel != null && userModel.getId() > 0 && userModel.getLoginname().trim().equals(username.trim()) && userModel.getLoginpwd().trim().equals(password.trim())) {
            return new SysResult(1, "success", null);
        }
        return new SysResult(99, "用户名或密码错误", null);
    }

    @CrossOrigin
    @RequestMapping("statis")
    public Object dashboard(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("html/admin/statis");
    }

    @CrossOrigin
    @RequestMapping("set")
    public Object hotel(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("html/admin/set");
    }

    @RequestMapping(value = "getOnlineModel")
    public SysResult getOnlineModel() {
        ExOnlineEntity exOnlineEntity = exMainService.getExOnline();
        if (exOnlineEntity != null) {
            OnlineResp onlineResp = new OnlineResp();
            List<ExMainEntity> exMainEntities = exMainService.getExMainList();
            onlineResp.setExMainEntities(exMainEntities);
            onlineResp.setExOnlineEntity(exOnlineEntity);
            return new SysResult(1, "", onlineResp);
        } else {
            return new SysResult(99, "未找到配置", null);
        }
    }

    @RequestMapping(value = "updateStatus")
    public SysResult updateStatus(Integer bonusNum, Integer status) {
        exMainService.updateExOnline(bonusNum);
        if (status.equals(1)) {
            exMainService.updateExAnswer();
        }
        return new SysResult(1, "", null);
    }

    @CrossOrigin
    @RequestMapping("experiment")
    public Object user(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("html/admin/experiment");
    }

    @CrossOrigin
    @RequestMapping("version")
    public Object version(ModelAndView modelAndView) {
        modelAndView.setViewName("html/admin/version");
        ExOnlineEntity exOnlineEntity = exMainService.getExOnline();
        if (exOnlineEntity != null) {
            modelAndView.addObject("version", exOnlineEntity.getVersion());
        }
        return modelAndView;
    }

    @RequestMapping(value = "getExperimentList")
    public SysResult getExperimentList() {
        List<ExMainEntity> exMainEntities = exMainService.getExMainList();
        return new SysResult(1, "", exMainEntities);
    }

    @RequestMapping(value = "delexperiment")
    public SysResult delexperiment(Integer id) {
        exMainService.delExMain(id);
        return new SysResult(1, "", null);
    }

    @CrossOrigin
    @RequestMapping("addexperiment")
    public Object addexperiment(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        modelAndView.setViewName("html/admin/addexperiment");
        String idstr = request.getParameter("id");
        if (!StringUtils.isEmpty(idstr)) {
            Integer id = Integer.parseInt(idstr);
            if (id > 0) {
                ExMainEntity exMainEntity = exMainService.getExMainById(id);
                if (exMainEntity != null) {
                    modelAndView.addObject("exMainEntity", JSON.toJSONString(exMainEntity));
                }
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "uploadpic")
    @ResponseBody
    public SysResult uploadpic(HttpServletRequest request) throws SQLException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> fileList = multipartRequest.getFiles("pic");
        String returnpath ="img";// new SimpleDateFormat("yyyyMMdd").format(new Date());
        if (fileList == null || fileList.size() == 0) {
            return new SysResult(99, "文件错误");
        }
        MultipartFile file = fileList.get(0);
        String filename =file.getOriginalFilename();// CommonHelper.getRandomString(18) + ".jpg";
        try {

            String picpath = urlpath + returnpath;
            savePic(file.getInputStream(), picpath, filename);
        } catch (Exception ex) {

        }
        return new SysResult(1, "success", "/experiment/" + returnpath + "/" + filename);
    }

    @RequestMapping(value = "uploadImgBase64")
    @ResponseBody
    public SysResult uploadImgBase64(HttpServletRequest request) {
        try {
            String imgname = CommonHelper.getRandomString(10);
            String imgStr;//接收经过base64编 之后的字符串
            imgStr = request.getParameter("imageData");
            if (StringUtils.isEmpty(imgStr)) {
                Map<String, String[]> map = request.getParameterMap();
                if (map != null && map.size() > 0) {
                    for (String[] val : map.values()) {
                        for (String v : val) {
                            imgStr = v;
                            logger.info(v);
                        }
                    }
                }
            }
            if (StringUtils.isEmpty(imgStr)) {
                return new SysResult(99, "参数为空");
            }
            imgStr = imgStr.replace("data:image/png;base64,", "");
            BASE64Decoder decoder = new BASE64Decoder();
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            String returnpath = new SimpleDateFormat("yyyyMMdd/").format(new Date());
            String picpath = urlpath + returnpath;
            File file = new File(picpath);
            //如果文件夹不存在则创建
            if (!file.exists() && !file.isDirectory()) {
                file.mkdir();
            }
            // 生成jpeg图片
            String imgFilePath = picpath + imgname + ".jpg";// 新生成的图片
            OutputStream out = null;
            try {
                out = new FileOutputStream(imgFilePath);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            out.write(b);
            out.flush();
            out.close();
            return new SysResult(1, "success", "/experiment/" + returnpath + imgname + ".jpg");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new SysResult(99, "生成图片错误");
    }

    private void savePic(InputStream inputStream, String path, String fileName) {

        OutputStream os = null;
        try {
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件

            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            File oldFile=new File(tempFile.getPath() + File.separator + fileName);
            if(oldFile.exists()){
                oldFile.delete();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "saveExMain")
    @ResponseBody
    public SysResult saveExMain(@RequestBody ExMainEntity exMainEntity) {
        if (exMainEntity != null) {
            exMainEntity.setCreate_time(new Date().getTime());
            if (exMainEntity.getId() != 0) {
                exMainService.updateExMain(exMainEntity);
            } else {
                exMainService.insertExMain(exMainEntity);
            }
        }
        return new SysResult(1, "success", null);
    }


    @CrossOrigin
    @RequestMapping("user")
    public Object order(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {

        return new ModelAndView("html/admin/userlist");
    }

    @RequestMapping(value = "getuserlist")
    @ResponseBody
    public SysResult getuserlist(HttpServletRequest request) {
        return new SysResult(1, "success", adminService.getUserList());
    }

    @CrossOrigin
    @RequestMapping("adduser")
    public Object adduser(HttpServletRequest request, ModelAndView modelAndView) {
        modelAndView.setViewName("html/admin/adduser");
        String idstr = request.getParameter("id");
        if (!StringUtils.isEmpty(idstr)) {
            Integer id = Integer.parseInt(idstr);
            if (id > 0) {
                UserModel userModel = appService.getUserById(id);
                if (userModel != null) {
                    modelAndView.addObject("userInfo", JSON.toJSONString(userModel));
                }
            }
        }
        return modelAndView;
    }

    @CrossOrigin
    @RequestMapping("deluser")
    public Object deluser(Integer id) {
        if (id != null && id > 0) {
            adminService.delUser(id);
            return new SysResult(1, "success", null);
        } else {
            return new SysResult(99, "参数不正确", null);
        }
    }

    @RequestMapping(value = "addusermodel")
    @ResponseBody
    public SysResult addusermodel(@RequestBody UserModel userModel) {
        try {
            userModel.setUsertype(0);
            userModel.setEmail("");
            userModel.setIsdelete(0);
            if (userModel.getId() > 0) {
                adminService.updateUser(userModel);
            } else {
                appService.insertUser(userModel);
            }
        } catch (Exception ex) {
            logger.error("addusermodel error", ex);
            return new SysResult(99, "add error", null);
        }
        return new SysResult(1, "success", null);
    }

    @CrossOrigin
    @RequestMapping("class")
    public Object classlist(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {

        return new ModelAndView("html/admin/classlist");
    }

    @RequestMapping(value = "getclasslist")
    @ResponseBody
    public SysResult getclasslist(HttpServletRequest request) {

        return new SysResult(1, "success", adminService.getClassList());
    }

    @RequestMapping(value = "addclass")
    @ResponseBody
    public SysResult addclass(@RequestBody ExClassEntity exClassEntity) {
        try {
            exClassEntity.setCreatetime(new Date().getTime());
            if (exClassEntity.getId() > 0) {
                adminService.updateClassModel(exClassEntity);
            } else {
                adminService.insertClassModel(exClassEntity);
            }
        } catch (Exception ex) {
            logger.error("addusermodel error", ex);
            return new SysResult(99, "add error", null);
        }
        return new SysResult(1, "success", null);
    }

    @RequestMapping(value = "delclass")
    public SysResult delclass(Integer id) {
        adminService.delClass(id);
        return new SysResult(1, "", null);
    }

    @CrossOrigin
    @RequestMapping("report")
    public Object report(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("html/admin/report");
    }

    @RequestMapping(value = "/getExAnswerJsonValue")
    @ResponseBody
    public SysResult getExAnswerJsonValue(Integer id) {
        String answer = exMainService.getExAnswerJsonValue(id);
        if (!StringUtils.isEmpty(answer)) {
            return new SysResult(1, "success", answer);
        } else {
            return new SysResult(99, "读取失败");
        }
    }

    @CrossOrigin
    @RequestMapping("oldreport")
    public Object oldreport(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("html/admin/oldreport");
    }

    @CrossOrigin
    @RequestMapping("delreport")
    public Object delreport(Integer id) {
        exMainService.deleteReport(id);
        return new SysResult(1, "success", null);
    }

    @RequestMapping(value = "getreportlist")
    @ResponseBody
    public SysResult getreportlist(Integer status) {
        try {
            List<ExAnswerEntity> exAnswerEntities = exMainService.getExAnswerEntityList(status);
            return new SysResult(1, "success", exAnswerEntities);
        } catch (Exception ex) {
            return new SysResult(99, "getreportlist error", null);
        }
    }

    @CrossOrigin
    @RequestMapping("devicelist")
    public Object devicelist(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("html/admin/devicelist");
    }

    @CrossOrigin
    @RequestMapping("deldevice")
    public Object deldevice(Integer id) {
        exMainService.deleteExStation(id);
        return new SysResult(1, "success", null);
    }

    @RequestMapping(value = "getdevicelist")
    @ResponseBody
    public SysResult getdevicelist() {
        try {
            List<ExStationEntity> exStationEntities = exMainService.getExStationList();
            return new SysResult(1, "success", exStationEntities);
        } catch (Exception ex) {
            return new SysResult(99, "getreportlist error", null);
        }
    }

    @CrossOrigin
    @RequestMapping("adddevice")
    public Object adddevice(HttpServletRequest request, ModelAndView modelAndView) {
        modelAndView.setViewName("html/admin/adddevice");
        String idstr = request.getParameter("id");
        if (!StringUtils.isEmpty(idstr)) {
            Integer id = Integer.parseInt(idstr);
            if (id > 0) {
                ExStationEntity exStationEntity = exMainService.getExStationById(id);
                if (exStationEntity != null) {
                    modelAndView.addObject("exStationEntity", JSON.toJSONString(exStationEntity));
                }
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "adddevicemodel")
    @ResponseBody
    public SysResult adddevicemodel(@RequestBody ExStationEntity exStationEntity) {
        try {
            exStationEntity.setCreate_time(new Date().getTime());
            exStationEntity.setIsdelete(0);
            if (exStationEntity.getId() > 0) {
                exMainService.updateExStation(exStationEntity);
            } else {
                exMainService.insertExStation(exStationEntity);
            }
            return new SysResult(1, "success", null);
        } catch (Exception ex) {
            return new SysResult(99, "getreportlist error", null);
        }
    }

    @CrossOrigin
    @RequestMapping("device")
    public Object device(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("html/admin/device");
    }

    @RequestMapping(value = "getdevice")
    @ResponseBody
    public SysResult getdevice(@RequestBody DeviceReq deviceReq) {
        try {
            String data = "test";
            Map<String, Object> params = new HashMap<>();
            params.put("getdevname", deviceReq.getIpaddress());
            params.put("gettype", deviceReq.getSelectdevice());
            String result = HTTPUtil.httpClientGet("http://123.56.255.65:503/getval", params, "utf-8");
            logger.info("获取设备信息：dev " + deviceReq.getIpaddress() + "  type" + deviceReq.getSelectdevice() + "  " + result);
            return new SysResult(1, "success", result);
        } catch (Exception ex) {
            logger.error("获取设备信息异常：", ex);
            return new SysResult(99, "getreportlist error", null);
        }
    }

    @Autowired
    private WxMpService wxMpService;

    @CrossOrigin
    @RequestMapping("scanRegister")
    public Object scanRegister(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        wxMpService = weChatMpConfig.wxMpService();
        modelAndView.setViewName("html/admin/register");
        try {
            String url = "http://experiment.justh5.com/admin/scanRegister";
            modelAndView.addObject("sign", getSign(url));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return modelAndView;
    }

    @CrossOrigin
    @RequestMapping("inner")
    public Object inner() {
        return new ModelAndView("html/admin/inner");
    }

    @RequestMapping(value = "scanAddUser")
    @ResponseBody
    public SysResult scanAddUser(@RequestBody UserModel userModel) {
        try {
            userModel.setUsertype(0);
            userModel.setEmail("");
            userModel.setIsdelete(0);
            userModel.setLoginpwd("");
            userModel.setLoginname("");
            userModel.setUsersign("");
            UserModel userModel1 = appService.getUserByUserCode(userModel.getUsercode());
            if (userModel1 != null) {
                if (userModel1.getUsertype().equals(3)) {
                    userModel1.setUsername(userModel.getUsername());
                    userModel1.setUserphone(userModel.getUserphone());
                    userModel1.setFacepic(userModel.getFacepic());
                    userModel1.setUsertype(0);
                    userModel1.setClassid(userModel.getClassid());
                    adminService.updateUser(userModel1);
                } else {
                    return new SysResult(99, "您的学号已经注册", null);
                }
            } else {
                userModel.setUsertype(2);
                appService.insertUser(userModel);
                return new SysResult(1, "注册成功，您未报本实验课，请联系老师", null);
            }
        } catch (Exception ex) {
            logger.error("addusermodel error", ex);
            return new SysResult(99, "add error", null);
        }
        return new SysResult(1, "注册成功", null);
    }

    @RequestMapping(value = "downloadImg")
    @ResponseBody
    public SysResult downloadImg(String server_id) {
        try {
            wxMpService = weChatMpConfig.wxMpService();
            String imgname = CommonHelper.getRandomString(10);
            String returnpath = new SimpleDateFormat("yyyyMMdd/").format(new Date());
            String picpath = urlpath + returnpath;
            InputStream inputStream = wxMpService.getMaterialService().materialImageOrVoiceDownload(server_id);
            savePic(inputStream, picpath, imgname);
            return new SysResult(1, "success", "/experiment/" + returnpath + imgname + ".jpg");
        } catch (Exception ex) {
            logger.error("get wx server img error", ex);
            return new SysResult(99, "add error", null);
        }
    }

    @CrossOrigin
    @RequestMapping("scan")
    public Object scan(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        wxMpService = weChatMpConfig.wxMpService();
        modelAndView.setViewName("html/admin/scan");
        try {
            String url = "http://experiment.justh5.com/admin/scan";
            modelAndView.addObject("sign", getSign(url));
            List<ExClassEntity> exClassEntities = adminService.getClassList();
            modelAndView.addObject("classes", JSON.toJSONString(exClassEntities));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return modelAndView;
    }

    private String getSign(String url) {
        try {
            WxJsapiSignature wxJsapiSignature = wxMpService.createJsapiSignature(url);
            String sign = JSON.toJSONString(wxJsapiSignature);
            logger.info(sign);
            return sign;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @CrossOrigin
    @RequestMapping("getosc")
    public Object getosc(@RequestBody OSCResp oscResp) {
        try {
            if (socketServer.channelModel != null && socketServer.channelModel.getSocketChannel() != null && socketServer.channelModel.getSocketChannel().isConnected()) {
                ByteBuffer sBuffer = ByteBuffer.allocate(1024);
                String str = JSON.toJSONString(oscResp);
                logger.info("发送数据：" + str);
                sBuffer = ByteBuffer.allocate(str.getBytes("UTF-8").length);
                sBuffer.put(str.getBytes("UTF-8"));
                sBuffer.flip();
                logger.info("write buffer");
                if(socketServer.channelModel.getOscRespModelList()!=null&&socketServer.channelModel.getOscRespModelList().size()>0){
                    for(OSCRespModel oscRespModel:socketServer.channelModel.getOscRespModelList()){
                        if(!StringUtils.isEmpty(oscRespModel.getDeviceName())&&!StringUtils.isEmpty(oscResp.getDeviceName())&& oscRespModel.getDeviceName().toLowerCase().equals(oscResp.getDeviceName().toLowerCase())){
                            oscRespModel.setResp("");
                        }
                    }
                }
                socketServer.channelModel.getSocketChannel().write(sBuffer);
                int num=6;
                if(oscResp.getType().equals("write")){
                    num=16;//写指令超时时间
                }
                for (int i = 0; i < num; i++) {
                    Thread.sleep(500);
                    if (socketServer.channelModel.getOscRespModelList() != null && socketServer.channelModel.getOscRespModelList().size() > 0) {
                        for (OSCRespModel oscRespModel : socketServer.channelModel.getOscRespModelList()) {
                            if (!StringUtils.isEmpty(oscRespModel.getDeviceName()) && !StringUtils.isEmpty(oscResp.getDeviceName()) && oscRespModel.getDeviceName().toLowerCase().equals(oscResp.getDeviceName().toLowerCase())) {
                                if (!StringUtils.isEmpty(oscRespModel.getResp())) {
                                    if (oscRespModel.getResp().contains("error")) {
                                        return new SysResult(1, "", oscRespModel.getResp());
                                    }
                                    String data = exMainService.GetTransferData(oscResp.getTransferType(), oscRespModel.getResp());
                                    logger.info("获取数据：" + oscRespModel.getResp() + "转换后：" + data);
                                    return new SysResult(1, "", "原始数据：" + oscRespModel.getResp() + " 转换后：" + data);
                                }
                            }
                        }
                    }
                }
                return new SysResult(99, "获取数据超时", null);
            } else {
                return new SysResult(99, "未连接设备", null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return new SysResult(99, "异常", null);
    }
}

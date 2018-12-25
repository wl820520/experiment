package com.justh5.experiment.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.justh5.experiment.config.WeChatMpConfig;
import com.justh5.experiment.domain.*;
import com.justh5.experiment.mapper.ExperimentMapper;
import com.justh5.experiment.model.*;
import com.justh5.experiment.service.AdminService;
import com.justh5.experiment.service.AppService;
import com.justh5.experiment.service.ExMainService;
import com.justh5.experiment.socketservice.SocketServer;
import com.justh5.experiment.util.ExcelUtil;
import com.justh5.experiment.util.FtpUtil;
import com.justh5.experiment.util.HTTPUtil;
import com.youxinpai.common.util.web.ResponseResult;
import com.youxinpai.common.util.web.http.HttpClientUtils;
import jcifs.UniAddress;
import jcifs.smb.*;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.catalina.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;
import com.youxinpai.cloud.util.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
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
    @Value("${faceSacnServer}")
    String faceSacnServer;
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
    @Autowired
    private ExperimentMapper experimentMapper;

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
            //FtpUtil.uploadFile(filename,picpath,file.getInputStream());
        } catch (Exception ex) {

        }
        return new SysResult(1, "success", "/experiment/" + returnpath + "/" + filename);
    }
    @RequestMapping(value = "uploadface")
    @ResponseBody
    public SysResult uploadface(HttpServletRequest request) throws SQLException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> fileList = multipartRequest.getFiles("pic");
        String returnpath =new SimpleDateFormat("yyyyMMdd").format(new Date());
        if (fileList == null || fileList.size() == 0) {
            return new SysResult(99, "文件错误");
        }
        MultipartFile file = fileList.get(0);
        String filename =CommonHelper.getRandomString(18) + ".jpg";
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
    @RequestMapping(value = "/uploadPdf")
    public SysResult uploadPdf(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> fileList = multipartRequest.getFiles("file");
        if (fileList == null || fileList.size() == 0) {
            return new SysResult(99, "请提交pdf", null);
        }
        MultipartFile file = fileList.get(0);
        String usercode="";
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
                    usercode=jsonStr;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if(!StringUtils.isEmpty(usercode)){
            UserModel userModel=appService.getUserByUserCode(usercode);
            if(userModel==null){
                return new SysResult(99, "未找到绑定用户", null);
            }
            Integer count=0;
            try {
                InputStream in= file.getInputStream();
                savePic(in,urlpath+"/pdf/",usercode+".pdf");
                experimentMapper.updatePDFPath(usercode+".pdf",userModel.getId());
                return new SysResult(1, "success", "");
            }catch (Exception ex){
                return new SysResult(99, "数据转化异常", null);
            }
        }else{
            return new SysResult(99, "请上传用户信息");
        }
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

    /**
     * 方法说明：上传文件到远程共享目录
     * @param localDir         本地临时路径(A:/测试/测试.xls)
     * @param removeDir        远程共享路径(smb://10.169.2.xx/测试/,特殊路径只能用/)
     * @param removeIp         远程共享目录IP(10.169.2.xx)
     * @param removeLoginUser  远程共享目录用户名(user)
     * @param removeLoginPass  远程共享目录密码(password)
     * @return
     * @throws Exception   0成功/-1失败
     */
    public static int smbUploading(String localDir, String removeDir,
                                   String removeIp, String removeLoginUser, String removeLoginPass) throws Exception {
        NtlmPasswordAuthentication auth = null;
        OutputStream out = null;
        int retVal = 0;
        try {
            File dir = new File(localDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            InetAddress ip = InetAddress.getByName(removeIp);
            UniAddress address = new UniAddress(ip);
            // 权限验证
            auth = new NtlmPasswordAuthentication(removeIp, removeLoginUser, removeLoginPass);
            SmbSession.logon(address,auth);

            //远程路径判断文件文件路径是否合法
            SmbFile remoteFile = new SmbFile(removeDir + dir.getName(), auth);
            remoteFile.connect();
            if(remoteFile.isDirectory()){
                retVal = -1;
            }

            // 向远程共享目录写入文件
            out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));
            out.write(toByteArray(dir));
        } catch (UnknownHostException e) {
            retVal = -1;
            e.printStackTrace();
        } catch (MalformedURLException e) {
            retVal = -1;
            e.printStackTrace();
        } catch (SmbException e) {
            retVal = -1;
            e.printStackTrace();
        } catch (IOException e) {
            retVal = -1;
            e.printStackTrace();
        } finally{
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return retVal;
    }

    /**
     * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
     *
     * @param file 文件
     * @return   字节数组
     * @throws IOException IO异常信息
     */
    @SuppressWarnings("resource")
    public static byte[] toByteArray(File file) throws IOException {
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,
                    fc.size()).load();
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                fc.close();
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
    @RequestMapping("compereuserface")
    public Object compereuserface(HttpServletRequest request, ModelAndView modelAndView) {
        modelAndView.setViewName("html/admin/compereuser");
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
    @RequestMapping(value = "compereface")
    @ResponseBody
    public SysResult compereface(HttpServletRequest request) throws SQLException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> fileList = multipartRequest.getFiles("pic");
        if (fileList == null || fileList.size() == 0) {
            return new SysResult(99, "文件错误");
        }
        MultipartFile file = fileList.get(0);
        try {
            if (!file.isEmpty()) {
                Map<String, MultipartFile> map = new HashMap<>();
                map.put("file", file);
                ResponseResult responseResult = HTTPUtil.doFormFilePostReq(faceSacnServer, map, null);
                if (responseResult.getCode() == 200) {
                    JSONObject values = JSON.parseObject(responseResult.getData().toString());
                    if (values.containsKey("code") && values.getInteger("code") == 1) {
                        Integer uid=values.getInteger("data");
                        UserModel userModel= experimentMapper.getUserModelByID(uid);
                        if(userModel!=null){
                            return new SysResult(1, "", userModel);
                        }

                    }
                }
            }
        }catch (Exception ex){
            logger.error("识别异常：",ex);
        }
        return new SysResult(99, "error", null);
    }
    @RequestMapping(value = "addusermodel")
    @ResponseBody
    public SysResult addusermodel(@RequestBody UserModel userModel) {
        try {
            userModel.setUsertype(0);
            userModel.setEmail("");
            userModel.setIsdelete(0);
            if (userModel.getId() > 0) {
//                UserModel userModel1=experimentMapper.getUserModelByID(userModel.getId());
//                if(userModel1!=null){
//                    if(StringUtils.isEmpty(userModel1.getFacepic())||!userModel1.getFacepic().equals(userModel.getFacepic())){
//                        //注册头像
//                        //File tempFile = new File("F:\\wlpspace\\experiment\\src\\main\\resources\\static\\pic20181221\\jjyqwfftoszzfywtoi.jpg");
//                        MultipartFile multipartFile=new MockMultipartFile("file.jpg", new FileInputStream(new File("F:\\wlpspace\\experiment\\src\\main\\resources\\static\\pic20181221\\jjyqwfftoszzfywtoi.jpg")));
//                        if(!multipartFile.isEmpty()) {
//                            Map<String, MultipartFile> map = new HashMap<>();
//                            map.put("file", multipartFile);
//                            ResponseResult responseResult= HTTPUtil.doFormFilePostReq(faceSacnServer, map, null);
//                            if(responseResult.getCode()==0){
//                                //experimentMapper.updateUserEncoding(responseResult.getData().toString(),userModel.getId());
//                            }else{
//
//                            }
//                        }
//                    }
//                }
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

    @CrossOrigin
    @RequestMapping("recode")
    public Object recode(HttpServletRequest request,ModelAndView modelAndView) {
        modelAndView.setViewName("html/admin/recode");
        String id = request.getParameter("id");
        List<RecodeModel> recodeModels=new ArrayList<>();
        if (id != null) {
            Gson gson=new Gson();
            ExAnswerEntity answer= exMainService.getExAnswerById(Integer.parseInt(id));
            if(answer!=null){
                ExMainEntity exMainEntity= exMainService.getExMainById(answer.getMain_id());
                if(exMainEntity!=null){
                    RecodeModel recode=new RecodeModel();
                    recode.setId(0);
                    recode.setTitle("总分");
                    recode.setScore(answer.getScore());
                    recodeModels.add(recode);
                    AnswerBase answerBase= JSON.parseObject(answer.getAnswer(),AnswerBase.class);
                     //Map<String,Object> mapAnswer= gson.fromJson(answer.getAnswer(), new TypeToken<HashMap<String,Object>>(){}.getType());
                     Map<String,Object> mapEx= gson.fromJson(exMainEntity.getJson_value(), new TypeToken<HashMap<String,Object>>(){}.getType());
//                     for(String key :mapAnswer.keySet()){
//                         Object obj=mapAnswer.get(key);
//                         getRecodeModelRef(mapEx,obj,key,recodeModels);
//                     }
                    if(answerBase.getFormal()!=null&&answerBase.getFormal().size()>0){
                        for(AnswerModel answerModel:answerBase.getFormal()){
                            if(answerModel.getId()!=null&&answerModel.getId()>0){
                                RecodeModel recodeModel=new RecodeModel();
                                if(answerModel.getAnswer()!=null&&answerModel.getAnswer().size()>0){
                                    List<AnswerModel> recodeModelList=new ArrayList<>();
                                    for(String key:mapEx.keySet()){
                                        recodeModelList= getRecodeId(key,mapEx.get(key),answerModel.getId());
                                        if(recodeModelList!=null&&recodeModelList.size()>0) {
                                            break;
                                        }
                                    }
                                    String answertxt="";
                                    for(AnswerAnswerModel answerAnswerModel:answerModel.getAnswer()){
                                        answertxt+=answerAnswerModel.getChoose()+""+",";
                                    }
                                    String re="";
                                    for(AnswerModel answerModel1:recodeModelList){
                                        if(answerModel1.isIstrue()){
                                            re+=answerModel1.getName()+",";
                                        }
                                    }
                                    recodeModel.setAnswer(re);
                                    recodeModel.setId(answerModel.getId());
                                    recodeModel.setChoose(answertxt);
                                    recodeModel.setContent("");
                                    recodeModel.setScore(answerModel.getScore());
                                    recodeModel.setTitle(answertxt.equalsIgnoreCase(re)?"正确":"错误");
                                    recodeModel.setType("select");
                                }
                                if(answerModel.getArray()!=null&&answerModel.getArray().size()>0){
                                    String answertxt="";
                                    for(List<AnswerArrayModel> answerArrayModels:answerModel.getArray()){
                                        if(answerArrayModels!=null&&answerArrayModels.size()>0) {
                                            for (AnswerArrayModel answerArrayModel : answerArrayModels) {
                                                if(answerArrayModel!=null&&answerArrayModel.getId()>0){
                                                    answertxt+="id:"+answerArrayModel.getId()+" name:"+answerArrayModel.getName()+" value:"+answerArrayModel.getValue();
                                                }
                                            }
                                        }
                                    }
                                    recodeModel.setAnswer("");
                                    recodeModel.setId(answerModel.getId());
                                    recodeModel.setChoose(answertxt);
                                    recodeModel.setContent("");
                                    recodeModel.setScore(answerModel.getScore());
                                    recodeModel.setTitle(answerModel.getTitle());
                                    recodeModel.setType(answerModel.getType());
                                }
                                if(!StringUtils.isEmpty(answerModel.getType())&&answerModel.getType().equals("input")){
                                    recodeModel.setAnswer("");
                                    recodeModel.setId(answerModel.getId());
                                    recodeModel.setChoose(answerModel.getValue());
                                    recodeModel.setContent("");
                                    recodeModel.setScore(answerModel.getScore());
                                    recodeModel.setTitle(answerModel.getScore()>0?"正确":"错误");
                                    recodeModel.setType(answerModel.getType());
                                }
                                recodeModels.add(recodeModel);
                            }
                        }
                    }
                }
            }
        }
        modelAndView.addObject("recode",JSON.toJSONString(recodeModels));
        return modelAndView;
    }
    private List<AnswerModel> getRecodeId(String key,Object val,Integer id){
        List<AnswerModel> answerModels=new ArrayList<>();
        try{
            ArrayList<Object> objects=(ArrayList<Object>)val;
            if(objects!=null&&objects.size()>0){
                Gson gson=new Gson();
                for(Object object:objects){
                    //Map<String,Object> mapEx= gson.fromJson(object.toString(), new TypeToken<HashMap<String,Object>>(){}.getType());
                    Map<String,Object> mapEx= ((LinkedTreeMap)object);
                    if(mapEx!=null){
                        for(String objkey:mapEx.keySet()){
                            boolean isfind=false;
                            if(objkey.equals("id")){
                                Integer newid=Integer.parseInt(mapEx.get(objkey).toString().replace(".0",""));
                                if(newid>0&&newid.equals(id)){
                                    isfind=true;
                                }
                            }
                            if(isfind){
                                ArrayList<Object> objs=(ArrayList<Object>)mapEx.get("answer");
                                for(Object obj:objs){
                                    Map<String,Object> linkkey= ((LinkedTreeMap)obj);
                                    AnswerModel answerModel=new AnswerModel();
                                    for(String link:linkkey.keySet()){
                                        switch (link){
                                            case "name":
                                                answerModel.setName(linkkey.get("name").toString());
                                                break;
                                            case "title":
                                                answerModel.setTitle(linkkey.get("title").toString());
                                                break;
                                            case "istrue":
                                                answerModel.setIstrue(linkkey.get("istrue").toString().replace(".0","").equals("0")?false:true);
                                        }
                                    }
                                    answerModels.add(answerModel);
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception ex){
            System.out.println(ex);
        }
        return answerModels;
    }
    private void getRecodeModelRef(Map<String,Object> mapEx,Object obj,String key,List<RecodeModel> recodeModels){
        RecodeModel recodeModel=new RecodeModel();
        for(String key2:mapEx.keySet()){
            if(key.equals(key2)){
                if (obj instanceof Integer) {
                    int value = ((Integer) obj).intValue();
                } else if (obj instanceof String) {
                    String s = (String) obj;


                } else if (obj instanceof Double) {
                    double d = ((Double) obj).doubleValue();
                } else if (obj instanceof Float) {
                    float f = ((Float) obj).floatValue();
                } else if (obj instanceof Long) {
                    long l = ((Long) obj).longValue();
                } else if (obj instanceof Boolean) {
                    boolean b = ((Boolean) obj).booleanValue();
                } else if (obj instanceof Date) {
                    Date d = (Date) obj;
                }
            }
        }
        return ;
    }




    @CrossOrigin
    @RequestMapping("questionbasetype")
    public Object questionbasetype(HttpServletRequest request,ModelAndView modelAndView) {
        modelAndView.setViewName("html/admin/questionbasetype");
        String idstr= request.getParameter("id");
        if(!StringUtils.isEmpty(idstr)) {
            Integer id=Integer.parseInt(idstr);
            List<ExQuestionBaseTypeEntity> exQuestionBaseTypeEntityList = experimentMapper.getExQuestionBaseTypeList(id);
            if (exQuestionBaseTypeEntityList != null) {
                modelAndView.addObject("qlist", exQuestionBaseTypeEntityList);
            }
        }
        return modelAndView;
    }



    @CrossOrigin
    @RequestMapping("addquestionbasetype")
    public Object addquestionbasetype(@RequestBody BaseTypeReq baseTypeReq) {
        if(baseTypeReq.getBaseid()!=null&&baseTypeReq.getBaseid()>0&&!StringUtils.isEmpty(baseTypeReq.getTypename())&&!StringUtils.isEmpty(baseTypeReq.getTypetitle())){
            ExMainEntity exMainEntity=exMainService.getExMainById(baseTypeReq.getBaseid());
            if(exMainEntity!=null&&exMainEntity.getId()>0){
                ExQuestionBaseTypeEntity exQuestionBaseTypeEntity=new ExQuestionBaseTypeEntity();
                exQuestionBaseTypeEntity.setMain_id(baseTypeReq.getBaseid());
                exQuestionBaseTypeEntity.setName(baseTypeReq.getTypename());
                exQuestionBaseTypeEntity.setTitle(baseTypeReq.getTypetitle());
                exQuestionBaseTypeEntity.setType(baseTypeReq.getType());
                exQuestionBaseTypeEntity.setValue(baseTypeReq.getVal());
                if(baseTypeReq.getId()!=null&&baseTypeReq.getId()>0){
                    exQuestionBaseTypeEntity.setId(baseTypeReq.getId());
                    experimentMapper.updateExQuestionBaseType(exQuestionBaseTypeEntity);
                }else{
                    experimentMapper.insertExQuestionBaseType(exQuestionBaseTypeEntity);
                }
                return new SysResult(1, "", null);
            }
        }else{
            return new SysResult(99, "数据不能为空,id大于0", null);
        }
        return new SysResult(99, "异常", null);
    }
    @CrossOrigin
    @RequestMapping("delquestionbasetype")
    public Object delquestionbasetype(Integer id) {
        if(id!=null&&id>0){
            experimentMapper.delExQuestionBaseType(id);
            return new SysResult(1, "", null);
        }
        return new SysResult(99, "异常", null);
    }

    @CrossOrigin
    @RequestMapping("questionlist")
    public Object questionlist(HttpServletRequest request,ModelAndView modelAndView) {
        modelAndView.setViewName("html/admin/questionlist");
        String idstr= request.getParameter("id");
        if(!StringUtils.isEmpty(idstr)) {
            Integer id=Integer.parseInt(idstr);
            List<ExQuestionEntity> exQuestionEntityList=experimentMapper.getExQuestionList(id);
            List<ExQuestionTypeEntity> exQuestionTypeEntityList=experimentMapper.getExQuestionTypeList();
            if (exQuestionEntityList != null&&exQuestionEntityList!=null) {
                modelAndView.addObject("qlist", exQuestionEntityList);
                modelAndView.addObject("qtlist", exQuestionTypeEntityList);
            }
        }
        return modelAndView;
    }

    @CrossOrigin
    @RequestMapping("addquestion")
    public Object addquestion(@RequestBody ExQuestionEntity exQuestionEntity) {
        if(exQuestionEntity.getBase_id()==null||exQuestionEntity.getBase_id()==0){
            return new SysResult(99, "找不到父试题id", null);
        }
        if(exQuestionEntity.getId()!=null&&exQuestionEntity.getId()>0){
            experimentMapper.updateExQuestion(exQuestionEntity);
        }else{
            experimentMapper.insertExQuestion(exQuestionEntity);
        }
        return new SysResult(1, "", null);
    }
    @CrossOrigin
    @RequestMapping("delquestion")
    public Object delquestion(Integer id) {
        if(id!=null&&id>0){
            experimentMapper.delExQuestion(id);
            return new SysResult(1, "", null);
        }
        return new SysResult(99, "异常", null);
    }

    @CrossOrigin
    @RequestMapping("output")
    public void output(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Map<String,Object>> maps = new ArrayList<>();
            List<UserModel> userModels=experimentMapper.getUserList();
            List<ExAnswerEntity> exAnswerEntities=experimentMapper.getExAnswerEntityList(0);
            List<ExAnswerEntity> exAnswerEntities1=experimentMapper.getExAnswerEntityList(1);
            exAnswerEntities.addAll(exAnswerEntities1);
            for(UserModel userModel:userModels){
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("学号",userModel.getUsercode());
                map.put("姓名",userModel.getUsername());
                Integer score1=0,score2=0,score3=0,score4=0,score5=0;
                for(ExAnswerEntity exAnswerEntity:exAnswerEntities){
                    if(exAnswerEntity.getUser_id().equals(userModel.getId())){
                        switch(exAnswerEntity.getMain_type()){
                            case 0:
                                score1=exAnswerEntity.getScore();
                                break;
                            case 1:
                                score2=exAnswerEntity.getScore();
                                break;
                            case 2:
                                score3=exAnswerEntity.getScore();
                                break;
                            case 3:
                                score4=exAnswerEntity.getScore();
                                break;
                            case 4:
                                score5=exAnswerEntity.getScore();
                                break;
                        }

                    }
                }
                map.put("实验一",score1);
                map.put("实验二",score2);
                map.put("实验三",score3);
                map.put("实验四",score4);
                map.put("考试成绩",score5);
                map.put("总分",score1+score2+score3+score4+score5);
                maps.add(map);
            }
            //创建Excel表格,写回客户端
            Workbook excel = ExcelUtil.createExcel(maps);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            excel.write(bos);
            byte[] bytes = bos.toByteArray();
            com.youxinpai.cloud.util.HTTPUtil.downloadExcelFile(bytes, response, "学生成绩");
        }catch (Exception ex){
            logger.error("异常",ex);
        }
    }
    /**
     * 支持在线打开和下载
     *
     * @param response
     * @throws IOException
     */
    @CrossOrigin
    @RequestMapping("readpdf")
    public void readpdf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String usercode=request.getParameter("usercode");
        if(StringUtils.isEmpty(usercode)){
            return;
        }
        boolean isOnLine=StringUtils.isEmpty(request.getParameter("online"))?false:true;
        UserModel userModel=experimentMapper.getUserModelByCode(usercode);
        if(userModel!=null) {
            String fname=usercode+".pdf";
            String filePath=urlpath+ "/pdf/"+usercode+".pdf";
            System.out.println("filePath:" + filePath);
            File f = new File(filePath);
            if (!f.exists()) {
                response.sendError(404, "File not found!");
                return;
            }
            BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
            byte[] bs = new byte[1024];
            int len = 0;
            response.reset(); // 非常重要
            if (isOnLine) { // 在线打开方式
                URL u = new URL("file:///" + filePath);
                String contentType = u.openConnection().getContentType();
                response.setContentType(contentType);
                response.setHeader("Content-Disposition", "inline;filename="
                        + fname);
                // 文件名应该编码成utf-8，注意：使用时，我们可忽略这句
            } else {
                // 纯下载方式
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment;filename="
                        + fname);
            }
            OutputStream out = response.getOutputStream();
            while ((len = br.read(bs)) > 0) {
                out.write(bs, 0, len);
            }
            out.flush();
            out.close();
            br.close();
        }
    }
}

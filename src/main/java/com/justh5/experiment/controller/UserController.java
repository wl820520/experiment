package com.justh5.experiment.controller;

import com.justh5.experiment.domain.SysResult;
import com.justh5.experiment.model.UserModel;
import com.justh5.experiment.service.HotelService;
import com.justh5.experiment.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    private static Logger logger = LogManager.getLogger(HotelController.class);
    @Autowired
    private UserService userService;
    @RequestMapping(value="getUserList", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SysResult getUserList() throws SQLException {
        List<UserModel> userList= userService.getUserList();
        logger.info("执行成功");
        return new SysResult(1, "success",userList);
    }
    @RequestMapping(value="regist", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SysResult regist(String loginname,String loginpwd,String username,String userphone) throws SQLException {
        UserModel userModel = userService.getUserByName(loginname);
        if(userModel!=null) {
            return new SysResult(99, "已被注册账号");
        }else {
            userModel = new UserModel();
            userModel.setIsdelete(0);
            userModel.setLoginname(loginname);
            String pwd= md5Password(loginpwd);
            userModel.setLoginpwd(pwd);
            userModel.setUsername(username);
            userModel.setUserphone(userphone);
            Integer uid = userService.addUser(userModel);
            if (uid>0) {
                return new SysResult(1, "success");
            } else {
                return new SysResult(99, "fail");
            }
        }
    }
    /**
     * 生成32位md5码
     * @param password
     * @return
     */
    public static String md5Password(String password) {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }
    @RequestMapping(value="login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SysResult login(String loginname,String loginpwd) throws SQLException {
        if(loginname!=null&&loginname!="") {
            UserModel userModel1 = userService.getUserByName(loginname);
            String pwd=md5Password(loginpwd);
            if(userModel1!=null&&pwd.equals( userModel1.getLoginpwd())) {
                return new SysResult(1, "success",userModel1);
            }else{
                return new SysResult(99, "fail");
            }
        }else{
            return new SysResult(99, "fail");
        }
    }
    @CrossOrigin
    @RequestMapping("userInfo")
    public Object jumpToView(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("experiment/services");
    }
    @CrossOrigin
    @RequestMapping("contact")
    public Object contact(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("experiment/contact");
    }
}

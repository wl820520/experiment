package com.justh5.experiment.controller;

import com.justh5.experiment.domain.SysResult;
import com.justh5.experiment.model.UserModel;
import com.justh5.experiment.service.AdminService;
import org.apache.catalina.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@RestController
@RequestMapping("admin")
public class AdminController {
    private static Logger logger = LogManager.getLogger(AdminController.class);
    @Autowired
    private AdminService adminService;
    @CrossOrigin
    @RequestMapping("index")
    public Object jumpToView(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("admin/index");
    }
    @RequestMapping(value = "/login")
    public SysResult login(String username, String password) {
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            return new SysResult(99, "用户名或密码不能为空",null);
        }
        UserModel userModel=adminService.getAdminUser();
        if(userModel!=null&&userModel.getId()>0&&userModel.getLoginname().trim().equals(username.trim())&&userModel.getLoginpwd().trim().equals(password.trim())){
            return new SysResult(1, "success",null);
        }
        return new SysResult(99, "用户名或密码错误",null);
    }
    @CrossOrigin
    @RequestMapping("statis")
    public Object dashboard(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("admin/statis");
    }
    @CrossOrigin
    @RequestMapping("experiment")
    public Object hotel(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("admin/experiment");
    }
    @CrossOrigin
    @RequestMapping("user")
    public Object user(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("admin/messages");
    }
    @CrossOrigin
    @RequestMapping("order")
    public Object order(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("admin/productlist");
    }
    @CrossOrigin
    @RequestMapping("report")
    public Object report(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("admin/reports");
    }
    @CrossOrigin
    @RequestMapping("room")
    public Object room(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        String id=request.getParameter("id");
        return new ModelAndView("admin/elements");
    }
    @CrossOrigin
    @RequestMapping("addroom")
    public Object addroom(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        String id=request.getParameter("id");
        return new ModelAndView("admin/room");
    }
    @CrossOrigin
    @RequestMapping("addhotel")
    public Object addhotel(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        String id=request.getParameter("id");
        return new ModelAndView("admin/experiment");
    }
}

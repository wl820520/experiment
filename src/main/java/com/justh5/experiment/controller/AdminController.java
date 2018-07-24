package com.justh5.experiment.controller;

import com.justh5.experiment.domain.SysResult;
import com.justh5.experiment.model.FeedBackModel;
import com.justh5.experiment.service.AdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@RestController
@RequestMapping("admin")
public class AdminController {
    private static Logger logger = LogManager.getLogger(HotelController.class);
    @Autowired
    private AdminService adminService;
    @CrossOrigin
    @RequestMapping("index")
    public Object jumpToView(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("admin/index");
    }
    @CrossOrigin
    @RequestMapping("dashboard")
    public Object dashboard(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("admin/dashboard");
    }
    @CrossOrigin
    @RequestMapping("experiment")
    public Object hotel(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        return new ModelAndView("admin/manageblog");
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
    @RequestMapping(value="addfeedback", produces = MediaType.APPLICATION_JSON_VALUE,method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public SysResult addfeedback(HttpServletRequest request, String content, String username, Integer uid, String email) throws SQLException {
        FeedBackModel feedBackModel=new FeedBackModel();
        feedBackModel.setContent(content);
        feedBackModel.setUsername(username);
        feedBackModel.setUid(uid);
        feedBackModel.setEmail(email);
        if(uid>0) {
            Integer feedback =adminService.addFeedBack(feedBackModel);
            return new SysResult(1, "success");
        }else{
            return new SysResult(99, "fail");
        }
    }
}

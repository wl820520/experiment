package com.justh5.experiment.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserModel implements Serializable{
    private Integer id;
    private String loginpwd;
    private String loginname;
    private String username;
    private String userphone;
    private String usercode;
    private String facepic;
    private String usersign;
    private Integer classid;
    private String classname;
    private Long logintime;
    private String pdf;
    private Integer roleid;
    private String email;
    private Integer isdelete;
    private Integer usertype;
    private Integer teacherid;
}

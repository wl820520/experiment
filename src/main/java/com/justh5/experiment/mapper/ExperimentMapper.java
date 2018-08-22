package com.justh5.experiment.mapper;

import com.justh5.experiment.model.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ExperimentMapper {
    @Select("select *from `user` where id =#{id}")
    UserModel getUserModelByID(@Param("id") Integer uid);
    @Select("select *from `user` where loginname =#{loginname} and isdelete=0")
    UserModel getUserModelByName(@Param("loginname") String loginname);
    @Select("select *from `user` where usercode =#{usercode} and isdelete=0")
    UserModel getUserModelByCode(@Param("usercode") String usercode);
    @Select("select *from `user` where usersign =#{usersign} and isdelete=0")
    UserModel getUserModelBySign(@Param("usersign") String usersign);
    @Select("select *from `user` where isdelete=0 and usertype=1")
    List<UserModel> getUserList();
    @Select("insert into `user` (loginname,loginpwd,usercode,signcode,username,userphone,usertype)VALUES(#{userModel.loginname},#{userModel.loginpwd},#{userModel.usercode},#{userModel.signcode},#{userModel.username},#{userModel.userphone},#{userModel.usertype})")
    void insertUser(@Param("userModel") UserModel userModel);


    @Select("select *from `ex_main` where isdelete = 0")
    List<ExMainEntity> getExMainList();
    @Select("select *from `ex_main` where id =#{id} and isdelete=0 limit 1")
    ExMainEntity getExMainById(Integer id);
    @Select("select *from `ex_station` where serialid =#{serialid} and isdelete=0 limit 1")
    ExStationEntity getExStationBySerialId(String serialid);

    @Select("select *from `ex_online` where isdelete=0 limit 1")
    ExOnlineEntity getExOnline();
}

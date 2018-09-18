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
    @Select("select a.*,b.classname from `user` a left join `ex_class` b on a.classid=b.id where a.isdelete=0 and a.usertype<>1")
    List<UserModel> getUserList();
    @Select("insert into `user` (loginname,loginpwd,usercode,usersign,username,userphone,usertype,facepic,classid)VALUES(#{userModel.loginname},#{userModel.loginpwd},#{userModel.usercode},#{userModel.usersign},#{userModel.username},#{userModel.userphone},#{userModel.usertype},#{userModel.facepic},#{userModel.classid})")
    void insertUser(@Param("userModel") UserModel userModel);
    @Select("update `user` set loginname = #{userModel.loginname},loginpwd = #{userModel.loginpwd},usercode = #{userModel.usercode},usersign = #{userModel.usersign},username = #{userModel.username},userphone = #{userModel.userphone},usertype = #{userModel.usertype},facepic = #{userModel.facepic},classid = #{userModel.classid} where id=#{userModel.id}")
    void updateUser(@Param("userModel") UserModel userModel);


    @Select("select *from `ex_main` where isdelete = 0")
    List<ExMainEntity> getExMainList();
    @Select("update `ex_main` set isdelete = 1 where id=#{id}")
    void delExMain(Integer id);

    @Select("select *from `ex_main` where id =#{id} and isdelete=0 limit 1")
    ExMainEntity getExMainById(@Param("id")Integer id);
    @Select("update `ex_main` set main_type = #{exMainEntity.main_type},content = #{exMainEntity.content},title = #{exMainEntity.title},json_value = #{exMainEntity.json_value} where id=#{exMainEntity.id}")
    void updateExMain(@Param("exMainEntity") ExMainEntity exMainEntity);
    @Select("insert into `ex_main` (main_type,content,title,json_value,create_time,isdelete)VALUES(#{exMainEntity.main_type},#{exMainEntity.content},#{exMainEntity.title},#{exMainEntity.json_value},#{exMainEntity.create_time},0)")
    void insertExMain(@Param("exMainEntity") ExMainEntity exMainEntity);

    @Select("select *from `ex_station` where id =#{id} and isdelete=0 limit 1")
    ExStationEntity getExStationById(@Param("id")Integer id);

    @Select("select *from `ex_station` where serialid =#{serialid} and isdelete=0 limit 1")
    ExStationEntity getExStationBySerialId(@Param("serialid")String serialid);

    @Select("select a.*,b.content mainName from `ex_station` a left join ex_main b on a.mainid=b.id where a.isdelete=0")
    List<ExStationEntity> getExStationList();

    @Select("update `ex_station` set ex_name = #{exStationEntity.ex_name},ip_address = #{exStationEntity.ip_address},mainid = #{exStationEntity.mainid},serialid = #{exStationEntity.serialid},ex_code = #{exStationEntity.ex_code} where id=#{exStationEntity.id}")
    void updateExStation(@Param("exStationEntity") ExStationEntity exStationEntity);
    @Select("insert into `ex_station` (ex_name,ip_address,mainid,serialid,ex_code,create_time,isdelete)VALUES(#{exStationEntity.ex_name},#{exStationEntity.ip_address},#{exStationEntity.mainid},#{exStationEntity.serialid},#{exStationEntity.ex_code},#{exStationEntity.create_time},0)")
    void insertExStation(@Param("exStationEntity") ExStationEntity exStationEntity);


    @Select("select *from `ex_online` where isdelete=0 limit 1")
    ExOnlineEntity getExOnline();
    @Select("update `ex_online` set ex_status=if(ex_status=1,0,1),bonus_num=#{bonusNum} where isdelete=0")
    ExOnlineEntity updateExOnline(Integer bonusNum);

    @Select("insert into `ex_answer` (main_id,user_id,station_id,score,answer,create_time,isdelete)VALUES(#{exAnswerEntity.main_id},#{exAnswerEntity.user_id},#{exAnswerEntity.station_id},#{exAnswerEntity.score},#{exAnswerEntity.answer},#{exAnswerEntity.create_time},0)")
    void insertExAnswerEntity(@Param("exAnswerEntity") ExAnswerEntity exAnswerEntity);

    @Select("select a.*,b.content mainName,b.title mainTitle,c.ex_name stationName,d.username userName from `ex_answer` a LEFT JOIN ex_main b on a.main_id=b.id LEFT JOIN ex_station c on a.station_id=c.id LEFT JOIN `user` d on a.user_id=d.id where a.isdelete=0 order by  a.id desc")
    List<ExAnswerEntity> getExAnswerEntityList();


    @Select("select *from `ex_class` where isdelete=0")
    List<ExClassEntity> getClassList();
    @Select("select *from `ex_class` where isdelete=0 and id=#{id}")
    ExClassEntity getClassById(@Param("id") Integer id);
    @Select("insert into `ex_class` (classname,createtime,isdelete)VALUES(#{exClassEntity.classname},#{exClassEntity.createtime},0)")
    void insertClass(@Param("exClassEntity") ExClassEntity exClassEntity);
    @Select("update `ex_class` set classname = #{exClassEntity.classname} where id=#{exClassEntity.id}")
    void updateClass(@Param("exClassEntity") ExClassEntity exClassEntity);
    @Select("update `ex_class` set isdelete = 1 where id=#{id}")
    void delClass(@Param("id")Integer id);
}

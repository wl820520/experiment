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
    @Select("update `user` set isdelete=1 where id =#{id}")
    void delUser(@Param("id") Integer id);
    @Select("select *from `user` where loginname =#{loginname} and isdelete=0")
    UserModel getUserModelByName(@Param("loginname") String loginname);
    @Select("select a.*,b.classname from `user` a left join ex_class b on a.classid=b.id where a.usercode =#{usercode} and a.isdelete=0")
    UserModel getUserModelByCode(@Param("usercode") String usercode);
    @Select("select *from `user` where usersign =#{usersign} and isdelete=0")
    UserModel getUserModelBySign(@Param("usersign") String usersign);
    @Select("select a.*,b.classname from `user` a left join `ex_class` b on a.classid=b.id where a.isdelete=0 and a.roleid=#{roleid}")
    List<UserModel> getUserListByRoleid(@Param("roleid") Integer roleid);
    @Select("select * from `user` where isdelete=0 and teacherid=#{teacherid}")
    List<UserModel> getUserListByTeacherid(@Param("teacherid") Integer teacherid);
    @Select("select * from `user` where isdelete=0 and roleid=2")
    List<UserModel> getTeacherList();

    @Select("insert into `user` (roleid,teacherid,loginname,loginpwd,usercode,usersign,username,userphone,usertype,facepic,classid)VALUES(#{userModel.roleid},#{userModel.teacherid},#{userModel.loginname},#{userModel.loginpwd},#{userModel.usercode},#{userModel.usersign},#{userModel.username},#{userModel.userphone},#{userModel.usertype},#{userModel.facepic},#{userModel.classid})")
    void insertUser(@Param("userModel") UserModel userModel);
    @Select("update `user` set loginname = #{userModel.loginname},loginpwd = #{userModel.loginpwd},usercode = #{userModel.usercode},usersign = #{userModel.usersign},username = #{userModel.username},userphone = #{userModel.userphone},usertype = #{userModel.usertype},facepic = #{userModel.facepic},classid = #{userModel.classid} where id=#{userModel.id}")
    void updateUser(@Param("userModel") UserModel userModel);
    @Select("update `user` set facepic = #{userModel.facepic} where id=#{userModel.id}")
    void updateUserPic(@Param("userModel") UserModel userModel);
    @Select("update `user` set facepic = #{pic} where id=#{id}")
    void updateUserFacePic(@Param("id") Integer id,@Param("pic") String pic);
    @Select("update `user` set logintime = #{logintime} where id=#{id}")
    void loginUser(@Param("logintime") Long logintime,@Param("id") Integer id);
    @Select("update `user` set pdf = #{path} where id=#{id}")
    void updatePDFPath(@Param("path") String path,@Param("id") Integer id);

    @Select("select id,main_type,content,title,substring(json_value,1,100),create_time from `ex_main` where isdelete = 0 ")
    List<ExMainEntity> getExMainList();
    @Select("update `ex_main` set isdelete = 1 where id=#{id}")
    void delExMain(Integer id);

    @Select("select *from `ex_main` where id =#{id} and isdelete=0 limit 1")
    ExMainEntity getExMainById(@Param("id")Integer id);

//    @Select("select c.*,a.main_id from ex_main_station a LEFT JOIN ex_station b on a.station_id=b.id LEFT JOIN ex_main c on a.main_id=c.id where b.serialid=#{serialid} and c.uid=#{uid} and a.isdelete=0 limit 1")
//    ExMainEntity getExMainBySerialIdAndTeacherId(@Param("serialid")String serialid,@Param("uid")Integer uid);
    @Select("select c.*,b.* from ex_main_station a LEFT JOIN ex_station b on a.station_id=b.id LEFT JOIN ex_main c on a.main_id=c.id where b.serialid=#{serialid} and c.uid=#{uid} and a.isdelete=0 limit 1")
    ExMainAndStationEntity getExMainStationBySerialIdAndTeacherId(@Param("serialid")String serialid,@Param("uid")Integer uid);

    @Select("update `ex_main` set main_type = #{exMainEntity.main_type},content = #{exMainEntity.content},title = #{exMainEntity.title},json_value = #{exMainEntity.json_value} where id=#{exMainEntity.id}")
    void updateExMain(@Param("exMainEntity") ExMainEntity exMainEntity);
    @Select("insert into `ex_main` (main_type,content,title,json_value,create_time,isdelete)VALUES(#{exMainEntity.main_type},#{exMainEntity.content},#{exMainEntity.title},#{exMainEntity.json_value},#{exMainEntity.create_time},0)")
    void insertExMain(@Param("exMainEntity") ExMainEntity exMainEntity);

    @Select("select *from `ex_station` where id =#{id} and isdelete=0 limit 1")
    ExStationEntity getExStationById(@Param("id")Integer id);

    @Select("select *from `ex_station` where serialid =#{serialid} and isdelete=0 limit 1")
    ExStationEntity getExStationBySerialId(@Param("serialid")String serialid);

    @Select("select * from `ex_station` where isdelete=0")
    List<ExStationEntity> getExStationList();

    @Select("update `ex_station` set ex_name = #{exStationEntity.ex_name},ip_address = #{exStationEntity.ip_address},serialid = #{exStationEntity.serialid},ex_code = #{exStationEntity.ex_code},ex_osc = #{exStationEntity.ex_osc} where id=#{exStationEntity.id}")
    void updateExStation(@Param("exStationEntity") ExStationEntity exStationEntity);
    @Select("insert into `ex_station` (ex_name,ip_address,serialid,ex_code,ex_osc,create_time,isdelete)VALUES(#{exStationEntity.ex_name},#{exStationEntity.ip_address},#{exStationEntity.serialid},#{exStationEntity.ex_code},#{exStationEntity.ex_osc},#{exStationEntity.create_time},0)")
    void insertExStation(@Param("exStationEntity") ExStationEntity exStationEntity);
    @Select("update `ex_station` set isdelete=1 where id=#{id}")
    void delExStation(@Param("id") Integer id);

    @Select("select *from `ex_online` where isdelete=0 and uid=#{uid} limit 1")
    ExOnlineEntity getExOnline(@Param("uid") Integer uid);
    @Select("select *from `ex_version` where isdelete=0 limit 1")
    ExVersionEntity getVersion();
    @Select("update `ex_version` set version=#{version} where isdelete=0")
    void updateVersion(Integer version);

    @Select("update `ex_online` set ex_status=if(ex_status=1,0,1),bonus_num=#{bonusNum} where isdelete=0 and uid=#{uid}")
    ExOnlineEntity updateExOnline(@Param("bonusNum") Integer bonusNum,@Param("uid") Integer uid);
    @Select("update `ex_answer` set status=1 where isdelete=0 and teacher_id=#{teacherid}")
    ExOnlineEntity updateExAnswer(@Param("teacherid") Integer teacherid);
    @Select("update `ex_answer` set isdelete=1 where id=#{id}")
    ExOnlineEntity delExAnswer(@Param("id") Integer id);
    @Select("update `ex_answer` set pdf=#{pdf} where id=#{id}")
    ExOnlineEntity updateExAnswerPdf(@Param("id") Integer id,@Param("pdf") String pdf);
    @Select("insert into `ex_answer` (teacher_id,main_id,user_id,station_id,score,answer,create_time,end_time,isdelete,isaddscore)VALUES(#{exAnswerEntity.teacher_id},#{exAnswerEntity.main_id},#{exAnswerEntity.user_id},#{exAnswerEntity.station_id},#{exAnswerEntity.score},#{exAnswerEntity.answer},#{exAnswerEntity.create_time},#{exAnswerEntity.end_time},0,#{exAnswerEntity.isaddscore})")
    void insertExAnswerEntity(@Param("exAnswerEntity") ExAnswerEntity exAnswerEntity);

    @Select("select a.id,a.main_id,b.main_type,a.user_id,d.usercode,a.station_id,a.score,a.create_time,a.end_time,a.isaddscore,b.content mainName,b.title mainTitle,c.ex_name stationName,d.username userName from `ex_answer` a LEFT JOIN ex_main b on a.main_id=b.id LEFT JOIN ex_station c on a.station_id=c.id LEFT JOIN `user` d on a.user_id=d.id where a.isdelete=0 and a.status=#{status} and a.teacher_id=#{teacherid} order by  a.id desc")
    List<ExAnswerEntity> getExAnswerEntityList(@Param("status")Integer status,@Param("teacherid")Integer teacherid);
    @Select("select answer from `ex_answer` where id= #{id} ")
    String getExAnswerJsonValue(@Param("id")Integer id);
    @Select("select * from `ex_answer` where id= #{id} ")
    ExAnswerEntity getExAnswerById(@Param("id")Integer id);
    @Select("SELECT count(*) count FROM `ex_answer` where `status`=0 and isdelete=0")
    Integer getCountAnswer();
    @Select("SELECT count(*) count FROM `ex_answer` where `user_id`=#{userid} and `main_id`=#{mainid} and isdelete=0")
    Integer getUserdAnswer(@Param("userid")Integer userid,@Param("mainid")Integer mainid);

    @Select("select *from `ex_class` where isdelete=0 and uid=#{userid}")
    List<ExClassEntity> getClassList(@Param("userid")Integer userid);
    @Select("select *from `ex_class` where isdelete=0 and id=#{id}")
    ExClassEntity getClassById(@Param("id") Integer id);
    @Select("insert into `ex_class` (classname,createtime,uid,isdelete)VALUES(#{exClassEntity.classname},#{exClassEntity.createtime},#{exClassEntity.uid},0)")
    void insertClass(@Param("exClassEntity") ExClassEntity exClassEntity);
    @Select("update `ex_class` set classname = #{exClassEntity.classname} where id=#{exClassEntity.id}")
    void updateClass(@Param("exClassEntity") ExClassEntity exClassEntity);
    @Select("update `ex_class` set isdelete = 1 where id=#{id}")
    void delClass(@Param("id")Integer id);




    @Select("select *from `ex_question` where isdelete=0 and base_id=#{base_id} order by sort")
    List<ExQuestionEntity> getExQuestionList(@Param("base_id")Integer base_id);
    @Select("insert into `ex_question` (base_id,`maxvalue`,minvalue,type,typename,ispic,score,title,pic,button_text,unit,sort,isdelete)VALUES(#{exQuestionEntity.base_id},#{exQuestionEntity.maxvalue},#{exQuestionEntity.minvalue},#{exQuestionEntity.type},#{exQuestionEntity.typename},#{exQuestionEntity.ispic},#{exQuestionEntity.score},#{exQuestionEntity.title},#{exQuestionEntity.pic},#{exQuestionEntity.button_text},#{exQuestionEntity.unit},#{exQuestionEntity.sort},0)")
    void insertExQuestion(@Param("exQuestionEntity") ExQuestionEntity exQuestionEntity);
    @Select("update `ex_question` set isdelete = 1 where id=#{id}")
    void delExQuestion(@Param("id")Integer id);
    @Select("update `ex_question` set `maxvalue`=#{exQuestionEntity.maxvalue},minvalue=#{exQuestionEntity.minvalue},type=#{exQuestionEntity.type},typename=#{exQuestionEntity.typename},ispic=#{exQuestionEntity.ispic},score=#{exQuestionEntity.score},title=#{exQuestionEntity.title},pic=#{exQuestionEntity.pic},button_text=#{exQuestionEntity.button_text},unit=#{exQuestionEntity.unit},sort=#{exQuestionEntity.sort} where id=#{exQuestionEntity.id}")
    void updateExQuestion(@Param("exQuestionEntity") ExQuestionEntity exQuestionEntity);


    @Select("SELECT a.role_id,b.* FROM menus_roles a LEFT JOIN menu b on a.menu_id=b.id where a.role_id=#{roleid} and a.isdelete=0 order by b.sort")
    List<MenuEntity> getMenuListByRole(@Param("roleid")Integer roleid);

    @Select("SELECT a.id,b.name as menuname,c.name as rolename FROM menus_roles a LEFT JOIN menu b on a.menu_id=b.id LEFT JOIN role c on a.role_id=c.id where a.isdelete=0 order by c.name")
    List<MenuRolesEntity> getMenuRoles();

    @Select("SELECT * FROM  menu  where isdelete=0")
    List<MenuEntity> getMenus();

    @Select("update `menus_roles` set isdelete = 1 where id=#{id}")
    void delMenuRole(@Param("id")Integer id);
    @Select("SELECT * FROM  menus_roles  where role_id=#{roleid} and menu_id=#{menuid}")
    MenuRolesEntity getMenuRoleByMenuidAndRoleid(@Param("roleid")Integer roleid,@Param("menuid")Integer menuid);

    @Select("update `menus_roles` set isdelete = 0 where id=#{id}")
    void updateMenuRole(@Param("id")Integer id);
    @Select("insert into `menus_roles`(menu_id,role_id,isdelete)values(#{menuid},#{roleid},0) ")
    void insertMenuRole(@Param("roleid")Integer roleid,@Param("menuid")Integer menuid);


    @Select("SELECT * FROM  ex_extype  where isdelete=0")
    List<ExExTypeEntity> getExExTypeList();
    @Select("update `ex_extype` set isdelete = 1 where id=#{id}")
    void delExType(@Param("id")Integer id);
    @Select("update `ex_extype` set experimentname = #{exTypeName} where id=#{id}")
    void updateExType(@Param("id")Integer id,@Param("exTypeName")String exTypeName);
    @Select("insert into `ex_extype` (experimentname,isdelete)values(#{exTypeName},0)")
    void insertExType(@Param("exTypeName")String exTypeName);
}

package com.justh5.experiment.service;

import com.alibaba.fastjson.JSON;
import com.justh5.experiment.domain.result.JsonResultModel;
import com.justh5.experiment.mapper.ExperimentMapper;
import com.justh5.experiment.model.ExAnswerEntity;
import com.justh5.experiment.model.ExMainEntity;
import com.justh5.experiment.model.ExOnlineEntity;
import com.justh5.experiment.model.ExStationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExMainService {
    //{"priview":[{"id":1,"title":"当你到淘宝店或者中发柜台购买电阻时，如何才不会被人鄙视：","ispic":0,"answer":[{"name":"A","title":"老板，给我来五斤上好的电阻","istrue":0},{"name":"B","title":"老板，给我来一盘47K 0805电阻","istrue":1},{"name":"C","title":"老板，给我来5只100欧姆电阻","istrue":0},{"name":"D","title":"老板，给我来200只48K直插电阻","istrue":1}]},{"id":2,"title":"挑出下面图片中的电阻","ispic":1,"answer":[{"name":"A","title":"html/1.jpg","istrue":0},{"name":"B","title":"html/2.jpg","istrue":0},{"name":"C","title":"html/3.jpg","istrue":1},{"name":"D","title":"html/4.jpg","istrue":1}]}],"formal":{"previewModels":[{"id":1,"type":"select","ispic":0,"title":"当你到淘宝店或者中发柜台购买电阻时，如何才不会被人鄙视：","answer":[{"name":"A","title":"老板，给我来五斤上好的电阻","istrue":0},{"name":"B","title":"老板，给我来一盘47K 0805电阻","istrue":0},{"name":"C","title":"老板，给我来5只100欧姆电阻","istrue":1},{"name":"D","title":"老板，给我来200只48K直插电阻","istrue":1}]}],"txtModels":[{"id":2,"type":"input","title":"使用万用表欧姆档测量电阻R1的值为：","minvalue":500,"maxvalue":550}],"tableModels":[{"id":3,"type":"table","title":"测量题，不分A/B题。表格中的数据是由学生通过平板上的键盘手动输入进去的，填完之后，与后台答案作对比，允许学生重测，直到测量正确为止","array":[[{"type":"text","name":"标称值"},{"type":"text","name":"R1＝510(Ω)"},{"type":"text","name":"R2＝1000(Ω)"},{"type":"text","name":"R5＝330(Ω)"}],[{"type":"text","name":"测量值/Ω"},{"id":1,"type":"value","minvalue":200,"maxvalue":300},{"id":2,"type":"value","minvalue":200,"maxvalue":300},{"id":3,"type":"value","minvalue":200,"maxvalue":300}]]}]}}
   //{"priview":[{"id":1,"answer":[{"name":"A"}]},{"id":2,"answer":[{"name":"C"},{"name":"D"}]}],"formal":{"previewModels":[{"id":1,"answer":[{"name":"A"}]}],"txtModels":[{"id":2,"value":540}],"tableModels":[{"id":3,"array":[[{"id":1,"value":200},{"id":2,"value":300},{"id":3,"value":200}]]}]}}
    @Autowired
    private ExperimentMapper experimentMapper;
    public List<ExMainEntity> getExMainList(){
        return experimentMapper.getExMainList();
    }
    public void delExMain(Integer id){
        experimentMapper.delExMain(id);
    }
    public ExMainEntity getExMainById(Integer id){
        return experimentMapper.getExMainById(id);
    }
    public void loginUser(Integer uid){
        experimentMapper.loginUser(new Date().getTime(),uid);
    }
    public ExStationEntity getExStationById(Integer id){
        return experimentMapper.getExStationById(id);
    }
    public ExStationEntity getExStationBySerialId(String serialid){
        return experimentMapper.getExStationBySerialId(serialid);
    }
    public List<ExStationEntity> getExStationList(){
        return experimentMapper.getExStationList();
    }
    public void deleteExStation(Integer id){
        experimentMapper.delExStation(id);
    }

    public void deleteReport(Integer id){
        experimentMapper.delExAnswer(id);
    }
    public void updateVersion(Integer version){
        experimentMapper.updateVersion(version);
    }
    public void updateExStation(ExStationEntity exStationEntity){
        experimentMapper.updateExStation(exStationEntity);
    }
    public  void insertExStation(ExStationEntity exStationEntity){
        experimentMapper.insertExStation(exStationEntity);
    }
    public ExOnlineEntity getExOnline(){
        ExOnlineEntity exOnlineEntity=experimentMapper.getExOnline();
        return exOnlineEntity;
    }
    public Integer getUsedAnswer(Integer userid,Integer mainid){
        return experimentMapper.getUserdAnswer(userid,mainid);
    }
    public void updateExMain(ExMainEntity exMainEntity){
        experimentMapper.updateExMain(exMainEntity);
    }
    public void insertExMain(ExMainEntity exMainEntity){
        experimentMapper.insertExMain(exMainEntity);
    }
    public void updateExOnline(Integer bonusNum){
        experimentMapper.updateExOnline(bonusNum);
    }
    public void updateExAnswer(){
        experimentMapper.updateExAnswer();
    }
    public JsonResultModel getResultData(){
        JsonResultModel jsonResultModel =new JsonResultModel();
        try {
            String jsondata = "{\"preview\":[{\"id\":1,\"title\":\"当你到淘宝店或者中发柜台购买电阻时，如何才不会被人鄙视：\",\"score\":2,\"ispic\":0,\"answer\":[{\"name\":\"A\",\"title\":\"老板，给我来五斤上好的电阻\",\"istrue\":0},{\"name\":\"B\",\"title\":\"老板，给我来一盘47K 0805电阻\",\"istrue\":1},{\"name\":\"C\",\"title\":\"老板，给我来5只100欧姆电阻\",\"istrue\":0},{\"name\":\"D\",\"title\":\"老板，给我来200只48K直插电阻\",\"istrue\":1}]},{\"id\":2,\"title\":\"挑出下面图片中的电阻\",\"score\":2,\"ispic\":1,\"answer\":[{\"name\":\"A\",\"title\":\"html/1.jpg\",\"istrue\":0},{\"name\":\"B\",\"title\":\"html/2.jpg\",\"istrue\":0},{\"name\":\"C\",\"title\":\"html/3.jpg\",\"istrue\":1},{\"name\":\"D\",\"title\":\"html/4.jpg\",\"istrue\":1}]}],\"formal\":{\"previewModels\":[{\"id\":1,\"score\":2,\"type\":\"select\",\"ispic\":0,\"title\":\"当你到淘宝店或者中发柜台购买电阻时，如何才不会被人鄙视：\",\"answer\":[{\"name\":\"A\",\"title\":\"老板，给我来五斤上好的电阻\",\"istrue\":0},{\"name\":\"B\",\"title\":\"老板，给我来一盘47K 0805电阻\",\"istrue\":0},{\"name\":\"C\",\"title\":\"老板，给我来5只100欧姆电阻\",\"istrue\":1},{\"name\":\"D\",\"title\":\"老板，给我来200只48K直插电阻\",\"istrue\":1}]}],\"txtModels\":[{\"id\":2,\"score\":2,\"type\":\"input\",\"title\":\"使用万用表欧姆档测量电阻R1的值为：\",\"minvalue\":500,\"maxvalue\":550}],\"tableModels\":[{\"id\":3,\"type\":\"table\",\"title\":\"测量题，不分A/B题。表格中的数据是由学生通过平板上的键盘手动输入进去的，填完之后，与后台答案作对比，允许学生重测，直到测量正确为止\",\"tableSubModels\":[[{\"type\":\"text\",\"name\":\"标称值\"},{\"type\":\"text\",\"name\":\"R1＝510(Ω)\"},{\"type\":\"text\",\"name\":\"R2＝1000(Ω)\"},{\"type\":\"text\",\"name\":\"R5＝330(Ω)\"}],[{\"type\":\"text\",\"name\":\"测量值/Ω\"},{\"id\":1,\"score\":2,\"type\":\"value\",\"minvalue\":200,\"maxvalue\":300},{\"id\":2,\"score\":2,\"type\":\"value\",\"minvalue\":200,\"maxvalue\":300},{\"id\":3,\"score\":2,\"type\":\"value\",\"minvalue\":200,\"maxvalue\":300}]]}]}}";
            jsonResultModel = JSON.parseObject(jsondata, JsonResultModel.class);
        }catch (Exception  ex){
            ex.printStackTrace();
        }
        return jsonResultModel;
    }
    public List<ExAnswerEntity> getExAnswerEntityList(Integer status){
        List<ExAnswerEntity> exAnswerEntities=experimentMapper.getExAnswerEntityList(status);
        return exAnswerEntities;
    }
    public String getExAnswerJsonValue(Integer id){
        return experimentMapper.getExAnswerJsonValue(id);
    }
}

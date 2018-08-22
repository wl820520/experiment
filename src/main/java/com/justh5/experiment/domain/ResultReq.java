package com.justh5.experiment.domain;

import com.justh5.experiment.domain.result.JsonResultModel;

public class ResultReq {
    private Integer score;
    private Integer userid;
    private JsonResultModel jsonResultModel;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public JsonResultModel getJsonResultModel() {
        return jsonResultModel;
    }

    public void setJsonResultModel(JsonResultModel jsonResultModel) {
        this.jsonResultModel = jsonResultModel;
    }
}

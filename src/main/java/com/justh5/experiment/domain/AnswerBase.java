package com.justh5.experiment.domain;

import java.util.ArrayList;

public class AnswerBase {
    private Integer code;
    private ArrayList<AnswerModel> formal;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ArrayList<AnswerModel> getFormal() {
        return formal;
    }

    public void setFormal(ArrayList<AnswerModel> formal) {
        this.formal = formal;
    }
}

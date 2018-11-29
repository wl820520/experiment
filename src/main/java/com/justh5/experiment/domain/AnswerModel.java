package com.justh5.experiment.domain;

import java.util.ArrayList;

public class AnswerModel {
    private Integer id;
    private boolean istrue;
    private boolean myChoose;
    private String name;
    private String type;
    private Integer score;
    private String value;
    private String title;
    private ArrayList<AnswerAnswerModel> answer;
    private ArrayList<ArrayList<AnswerArrayModel>> array;

    public ArrayList<AnswerAnswerModel> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<AnswerAnswerModel> answer) {
        this.answer = answer;
    }

    public ArrayList<ArrayList<AnswerArrayModel>> getArray() {
        return array;
    }

    public void setArray(ArrayList<ArrayList<AnswerArrayModel>> array) {
        this.array = array;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isIstrue() {
        return istrue;
    }

    public void setIstrue(boolean istrue) {
        this.istrue = istrue;
    }

    public boolean isMyChoose() {
        return myChoose;
    }

    public void setMyChoose(boolean myChoose) {
        this.myChoose = myChoose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

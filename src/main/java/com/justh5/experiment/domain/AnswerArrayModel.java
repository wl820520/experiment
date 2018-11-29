package com.justh5.experiment.domain;

public class AnswerArrayModel {
    private Integer id;
    private boolean istrue;
    private double minvalue;
    private double maxvalue;
    private boolean myChoose;
    private String name;
    private String type;
    private double value;

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

    public double getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(double minvalue) {
        this.minvalue = minvalue;
    }

    public double getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(double maxvalue) {
        this.maxvalue = maxvalue;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

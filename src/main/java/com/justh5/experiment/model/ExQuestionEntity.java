package com.justh5.experiment.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExQuestionEntity implements Serializable {
    private Integer id;
    private Integer uid;
    private Integer base_id;
    private double maxvalue;
    private double minvalue;
    private Integer type;
    private String typename;
    private Integer ispic;
    private Integer score;
    private String title;
    private String pic;
    private String button_text;
    private Integer isdelete;
    private String unit;
    private Integer sort;


}

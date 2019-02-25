package com.justh5.experiment.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExAnswerEntity  implements Serializable {
    private Integer rownum;
    private Integer uid;
    private Integer id;
    private  Integer main_id;
    private String mainName;
    private String mainTitle;
    private Integer user_id;
    private String userName;
    private Integer station_id;
    private String stationName;
    private Integer score;
    private String answer;
    private Long create_time;
    private Long end_time;
    private Integer isaddscore;
    private String pdf;
    private String usercode;
    private Integer main_type;
    private Integer teacher_id;
}

package com.justh5.experiment.model;

import lombok.Data;

@Data
public class ExMainAndStationEntity {
    private Integer id;
    private Integer uid;
    private String title;
    private String content;
    private String json_value;
    private Long create_time;
    private Integer isdelete;
    private Integer main_type;
    private Integer main_id;
    private String ex_name;
    private String ip_address;
    private String mainName;
    private String serialid;
    private String ex_code;
    private String ex_osc;
}

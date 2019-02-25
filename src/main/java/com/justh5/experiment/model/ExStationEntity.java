package com.justh5.experiment.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExStationEntity implements Serializable {
    private Integer id;
    private String ex_name;
    private String ip_address;
    private String mainName;
    private String serialid;
    private String ex_code;
    private String ex_osc;
    private Long create_time;
    private Integer isdelete;
    private Integer main_id;
}

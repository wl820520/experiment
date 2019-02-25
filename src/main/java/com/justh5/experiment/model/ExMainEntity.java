package com.justh5.experiment.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExMainEntity  implements Serializable {
    private Integer id;
    private Integer uid;
    private String title;
    private String content;
    private String json_value;
    private Long create_time;
    private Integer isdelete;
    private Integer main_type;
    private Integer main_id;

}

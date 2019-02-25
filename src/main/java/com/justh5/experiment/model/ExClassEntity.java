package com.justh5.experiment.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExClassEntity  implements Serializable {
    private Integer id;
    private Integer uid;
    private String classname;
    private Long createtime;
    private Integer isdelete;

}

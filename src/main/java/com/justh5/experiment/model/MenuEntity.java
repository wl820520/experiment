package com.justh5.experiment.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MenuEntity implements Serializable {
    private Integer id;
    private Integer roleid;
    private String path;
    private String name;
    private Integer sort;
    private String icon;
    private String lefticon;
    private String href;
}

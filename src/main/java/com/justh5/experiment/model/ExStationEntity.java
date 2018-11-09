package com.justh5.experiment.model;

public class ExStationEntity {
    private Integer id;
    private String ex_name;
    private String ip_address;
    private Integer mainid;
    private String mainName;
    private String serialid;
    private String ex_code;
    private String ex_osc;
    private Long create_time;
    private Integer isdelete;

    public String getEx_osc() {
        return ex_osc;
    }

    public void setEx_osc(String ex_osc) {
        this.ex_osc = ex_osc;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEx_name() {
        return ex_name;
    }

    public void setEx_name(String ex_name) {
        this.ex_name = ex_name;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public Integer getMainid() {
        return mainid;
    }

    public void setMainid(Integer mainid) {
        this.mainid = mainid;
    }

    public String getSerialid() {
        return serialid;
    }

    public void setSerialid(String serialid) {
        this.serialid = serialid;
    }

    public String getEx_code() {
        return ex_code;
    }

    public void setEx_code(String ex_code) {
        this.ex_code = ex_code;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }
}

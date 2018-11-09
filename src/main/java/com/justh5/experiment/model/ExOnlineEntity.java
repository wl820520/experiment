package com.justh5.experiment.model;

public class ExOnlineEntity {
    private Integer id;
    private Integer main_type;
    private Integer ex_status;
    private Integer version;
    private Integer bonus_num;

    public Integer getBonus_num() {
        return bonus_num;
    }

    public void setBonus_num(Integer bonus_num) {
        this.bonus_num = bonus_num;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMain_type() {
        return main_type;
    }

    public void setMain_type(Integer main_type) {
        this.main_type = main_type;
    }

    public Integer getEx_status() {
        return ex_status;
    }

    public void setEx_status(Integer ex_status) {
        this.ex_status = ex_status;
    }
}

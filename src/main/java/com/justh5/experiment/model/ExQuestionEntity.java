package com.justh5.experiment.model;

public class ExQuestionEntity {
    private Integer id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(double maxvalue) {
        this.maxvalue = maxvalue;
    }

    public double getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(double minvalue) {
        this.minvalue = minvalue;
    }

    public Integer getIspic() {
        return ispic;
    }

    public void setIspic(Integer ispic) {
        this.ispic = ispic;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getButton_text() {
        return button_text;
    }

    public void setButton_text(String button_text) {
        this.button_text = button_text;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBase_id() {
        return base_id;
    }

    public void setBase_id(Integer base_id) {
        this.base_id = base_id;
    }


}

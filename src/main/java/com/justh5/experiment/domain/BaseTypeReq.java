package com.justh5.experiment.domain;

public class BaseTypeReq {
    private Integer id;
    private Integer baseid;
    private String typename;
    private String typetitle;
    private String val;
    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBaseid() {
        return baseid;
    }

    public void setBaseid(Integer baseid) {
        this.baseid = baseid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getTypetitle() {
        return typetitle;
    }

    public void setTypetitle(String typetitle) {
        this.typetitle = typetitle;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}

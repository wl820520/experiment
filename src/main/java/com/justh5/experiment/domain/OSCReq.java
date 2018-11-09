package com.justh5.experiment.domain;


public class OSCReq {
    private String userid;
    private String serialid;
    private String oSCCOde;
    private String type;

    public String getoSCCOde() {
        return oSCCOde;
    }

    public void setoSCCOde(String oSCCOde) {
        this.oSCCOde = oSCCOde;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSerialid() {
        return serialid;
    }

    public void setSerialid(String serialid) {
        this.serialid = serialid;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

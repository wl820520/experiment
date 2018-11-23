package com.justh5.experiment.domain;

public class OSCResp {
    private String deviceName;
    private String instruct;
    private String type;
    private String transferType;

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getInstruct() {
        return instruct;
    }

    public void setInstruct(String instruct) {
        this.instruct = instruct;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.justh5.experiment.domain;

import java.nio.channels.SocketChannel;
import java.util.List;

public class ChannelModel {
    private SocketChannel socketChannel;
    private String randKey;
    private List<OSCRespModel> oscRespModelList;

    public List<OSCRespModel> getOscRespModelList() {
        return oscRespModelList;
    }

    public void setOscRespModelList(List<OSCRespModel> oscRespModelList) {
        this.oscRespModelList = oscRespModelList;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public String getRandKey() {
        return randKey;
    }

    public void setRandKey(String randKey) {
        this.randKey = randKey;
    }

}

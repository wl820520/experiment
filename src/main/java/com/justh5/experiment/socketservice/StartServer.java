package com.justh5.experiment.socketservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartServer {
    @Autowired
    SocketServer socketServer;
    public void StartNow(){
        socketServer.startSocketServer(8022);
    }
}

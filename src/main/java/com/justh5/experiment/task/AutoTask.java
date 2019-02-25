package com.justh5.experiment.task;
import com.justh5.experiment.service.AppService;
import com.justh5.experiment.socketservice.SocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by wangliang3 on 2017/12/27.
*/
@Component
public class AutoTask {
    @Autowired
    private AppService appService;
    @Autowired
    private SocketServer socketServer;
    @Scheduled(cron = "0 */5 * * * ?")
    public void autoreportfor23hour() {
        //appService.sendSMS();
    }
    @Scheduled(cron = "*/5 * * * * ?")
    public void autoRunSocket() {
        if(socketServer!=null) {
            socketServer.startSocketServer(8023);
        }else{
            socketServer=new SocketServer();
            socketServer.startSocketServer(8023);
        }
    }

}
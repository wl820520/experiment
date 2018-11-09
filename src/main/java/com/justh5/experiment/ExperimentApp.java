package com.justh5.experiment;

import com.justh5.experiment.socketservice.SocketServer;
import com.justh5.experiment.socketservice.StartServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Timer;
import java.util.TimerTask;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.justh5.experiment.mapper")
public class ExperimentApp {
    public static void main(String[] args){
        SpringApplication.run(ExperimentApp.class, args);

    }
}

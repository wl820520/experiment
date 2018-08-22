package com.justh5.experiment;

import com.justh5.experiment.socketservice.SocketServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.justh5.experiment.mapper")
public class ExperimentApp {
    public static void main(String[] args){
        SpringApplication.run(ExperimentApp.class, args);
//        SocketServer server = new SocketServer();
//        server.startSocketServer(502);
    }
}

package com.justh5.experiment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.justh5.experiment.mapper")
public class HotelApp {
    public static void main(String[] args){
        SpringApplication.run(HotelApp.class, args);
    }
}

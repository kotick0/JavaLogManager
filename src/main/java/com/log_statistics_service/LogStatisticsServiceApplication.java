package com.log_statistics_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LogStatisticsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogStatisticsServiceApplication.class, args);
    }

}

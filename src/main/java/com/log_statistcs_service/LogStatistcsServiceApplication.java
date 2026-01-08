package com.log_statistcs_service;

import com.log_statistcs_service.logic.FileWrite;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogStatistcsServiceApplication {

    public static void main(String[] args) {
//        FileWrite.writeToFile("src/main/resources/test.txt", 0);
        SpringApplication.run(LogStatistcsServiceApplication.class, args);
    }

}

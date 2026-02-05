package com.log_statistics_service;

import com.log_statistics_service.domain.NextLogResult;

import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;

public class MainTestRun {
    public static void main(String[] args) {
        StringBuilder logBuilder = new StringBuilder();
        try(RandomAccessFile raf = new RandomAccessFile("input_log/mats-2026-01-26.0.log", "r")) {
            raf.seek(0L);
            String line;
            while ((line = raf.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

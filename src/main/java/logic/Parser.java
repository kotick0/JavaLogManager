package logic;

import domain.LogEntry;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Parser {
    private FileOperations file = new FileOperations();

    public void parseLog() {

       String currentLog = file.readNextLog(4).toString(); //fixme: tylko do testów

        String dateTimeString = currentLog.substring(0, 16);
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
       LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

       String logLevel;
       if (currentLog.contains("DEBUG")) {
           logLevel = "DEBUG";
       } else if (currentLog.contains("INFO")) {
            logLevel = "INFO";
       } else if (currentLog.contains("WARN")) {
           logLevel = "WARN";
       } else {
           logLevel = "ERROR";
       }

       int indexofStartTag = currentLog.lastIndexOf('[');
       int indexofCloseTag = currentLog.lastIndexOf(']');
       String tagsString = currentLog.substring(indexofStartTag + 1, indexofCloseTag);
       String[] tags = tagsString.split(",");
       System.out.println("Found date: " + dateTime + "\nFound time: " + logLevel + "\nFound tags: " + Arrays.toString(tags));
    }
}

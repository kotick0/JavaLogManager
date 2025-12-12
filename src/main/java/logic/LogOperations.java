package logic;

import domain.LogEntry;

import java.util.List;

public class LogOperations {

    public int countLogLevel(List<LogEntry> logEntries) {
        for(LogEntry logEntry : logEntries){
            System.out.println(logEntry);
        }
        return -1;
    }
}

package domain;

import java.time.LocalDateTime;
import java.util.HashMap;

public class LogEntry {
    private LocalDateTime timestamp;
    public enum LevelEnum {
        DEBUG, INFO, WARN, ERROR
    }
    private HashMap<LevelEnum, Integer> levelMap;
    private HashMap<String, Integer> tagMap;
    private int numberOfEntries;

    public LogEntry(LocalDateTime timestamp, HashMap<LevelEnum, Integer> levelMap, HashMap<String, Integer> tagMap) {
        this.timestamp = timestamp;
        this.levelMap = levelMap;
        this.tagMap = tagMap;
        this.numberOfEntries = this.levelMap.size() + this.tagMap.size();
    }
}

//package domain;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//
//
//public class LogEntry {
//    private LocalDateTime timestamp;
//    private String logLevel;
//    private String[] tags;
//
//    public LogEntry(LocalDateTime timestamp, String logLevel, String[] tags) {
//        this.timestamp = timestamp;
//        this.logLevel = logLevel;
//        this.tags = tags;
//    }
//    @Override
//    public String toString() {
//        return "Timestamp: " + timestamp + ", LogLevel: " + logLevel + ", Tags: " + Arrays.toString(tags);
//    }
//}



//    private LocalDateTime dateTime;
//    private Map<String, Integer> logLevelCount;
//    private Map<String, Integer> tagsCount;
//    private int allLogCount;
//

//
//    public LogEntry(LocalDateTime dateTime, Map<String, Integer> logLevelCount, Map<String, Integer> tagsCount) {
//        this.dateTime = dateTime;
//        this.logLevelCount = logLevelCount;
//        this.tagsCount = tagsCount;
//
//    }
//
//    public int getLogCount() {
//        return this.allLogCount;
//    }
//
////    public int setLogCount(int logCount) {
////        return this.allLogCount = logLevelCount.keySet().stream().mapToInt(Enum::ordinal).sum();
////    }
//
//    public LocalDateTime getDateTime() {
//        return this.dateTime;
//    }

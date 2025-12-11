package domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class LogEntry {
    private LocalDateTime timestamp;
    public enum LevelEnum {
        DEBUG, INFO, WARN, ERROR
    }
    private Map<LevelEnum, Integer> levelMap = new HashMap<LevelEnum, Integer>();
    private Map<String, Integer> tagMap =  new HashMap<String, Integer>();
    private int numberOfEntries;

    public LogEntry(LocalDateTime timestamp, Map<LevelEnum, Integer> levelMap, Map<String, Integer> tagMap) {
        this.timestamp = timestamp;
        this.levelMap = levelMap;
        this.tagMap = tagMap;
        this.numberOfEntries = 0;

        for(Integer value : this.levelMap.values()) {
            this.numberOfEntries += value;
        }
    }

    @Override
    public String toString() {
        return "Timestamp: " + String.valueOf(timestamp) + "\n" +
                "LogLevelMap: " + this.levelMap + "\n" +
                "TagMap: " + this.tagMap + "\n" +
                "Overall count: " + this.numberOfEntries + "\n";
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

package domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class LogEntry {
    private LocalDateTime timestamp;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Map<LevelEnum, Integer> getLevelMap() {
        return levelMap;
    }
    public Map<String, Integer> getTagMap() {
        return tagMap;
    }
    public int getNumberOfEntries() {
        return numberOfEntries;
    }
}

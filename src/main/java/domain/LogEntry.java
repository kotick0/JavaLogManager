package domain;

import java.time.LocalDateTime;
import java.util.Arrays;

public class LogEntry {
    private LocalDateTime timestamp;
    private LevelEnum logLevel;
    private String[] tags;

    public LogEntry(LocalDateTime timestamp, LevelEnum logLevel, String[] tags) {
        this.timestamp = timestamp;
        this.logLevel = logLevel;
        this.tags = tags;

    }

    @Override
    public String toString() {
        return "Timestamp: " + String.valueOf(timestamp) + "\n" +
                "LogLevel: " + this.logLevel + "\n" +
                "Tags: " + Arrays.toString(this.tags) + "\n\n";
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public LevelEnum getLevelMap() {
        return logLevel;
    }
    public String[] getTagMap() {
        return tags;
    }
}

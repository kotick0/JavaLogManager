package domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class LogEntry {
    private LocalDateTime dateTime;
    private Map<LOG_LEVEL, Integer> logLevelCount = new HashMap<>();
    private Map<String, Integer> tagsCount = new HashMap<>();
    private int allLogCount;

    private enum LOG_LEVEL {
        DEBUG, INFO, WARN, ERROR
    }

    public LogEntry(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.logLevelCount = logLevelCount;
        this.tagsCount = tagsCount;
        this.allLogCount = 0;
    }

    public int getLogCount() {
        return this.allLogCount;
    }

    public int setLogCount(int logCount) {
        return this.allLogCount = logLevelCount.keySet().stream().mapToInt(Enum::ordinal).sum();
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

}

package domain;

import java.time.LocalDateTime;
import java.util.HashMap;

public class LogEntry {
    private int logCount;
    private LocalDateTime dateTime;
    private final HashMap<LOG_LEVEL, Integer> LogLevelCount = new HashMap<>();

    private enum LOG_LEVEL {
        DEBUG, INFO, WARN, ERROR
    }

    public LogEntry() {
        this.logCount = 0;
        this.dateTime = LocalDateTime.now();
    }

    public int getLogCount() {
        return this.logCount;
    }

    public int setLogCount(int logCount) {
        return this.logCount = LogLevelCount.keySet().stream().mapToInt(Enum::ordinal).sum();
    }

    public boolean getDateTime(LocalDateTime now) {
        now = LocalDateTime.now();
        return (this.dateTime.isAfter(now));
    }

}

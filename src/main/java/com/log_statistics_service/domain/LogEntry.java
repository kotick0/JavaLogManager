package com.log_statistics_service.domain;

import java.time.LocalDate;
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
        return "Timestamp: " + String.valueOf(timestamp) + "\n" + "LogLevel: " + this.logLevel + "\n" + "Tags: " + Arrays.toString(this.tags) + "\n\n";
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public LocalDate getTimestampDate() {
        return this.timestamp.toLocalDate();
    }

    public LevelEnum getLevel() {
        return this.logLevel;
    }

    public String[] getTags() {
        return this.tags;
    }
}

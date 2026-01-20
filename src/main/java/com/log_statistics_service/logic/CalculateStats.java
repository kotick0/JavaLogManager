package com.log_statistics_service.logic;

import com.log_statistics_service.domain.DateStats;
import com.log_statistics_service.domain.LevelEnum;
import com.log_statistics_service.domain.LogEntry;
import com.log_statistics_service.domain.OverallStats;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CalculateStats {

    private String outputPath;
    private final LogOperations logOperations;

    public CalculateStats(LogOperations logOperations) {
        this.logOperations = logOperations;
    }

    public OverallStats calculateOverallStats(List<LogEntry> logEntries) {
        Map<LevelEnum, Integer> byLogLevel = logOperations.countLogLevel(logEntries);
        Map<String, Integer> byTags = logOperations.countTags(logEntries);
        Map<LocalDate, Integer> byDate = logOperations.countDates(logEntries);
        return new OverallStats(byLogLevel, byTags, byDate);
    }

    public List<DateStats> calculateDateStats(List<LogEntry> logEntries) {
        Map<LocalDate, DateStats> resultMap = new HashMap<>();

        for (LogEntry entry : logEntries) {
            DateStats dateStats = resultMap.get(entry.getDate());
            if (dateStats != null) {
                resultMap.put(entry.getDate(), updateDateStats(dateStats, entry));
            } else {
                resultMap.put(entry.getDate(), createNewDateStats(entry));
            }
        }
        return resultMap.values().stream().toList();
    }

    private DateStats createNewDateStats(LogEntry entry) {
        Map<LevelEnum, Integer> byLogLevel = new HashMap<>();
        byLogLevel.put(entry.getLevel(), 1);
        Map<String, Integer> byTag = new HashMap<>();
        Arrays.stream(entry.getTags())
                .forEach(tag -> byTag.put(tag, 1));
        return new DateStats(entry.getDate(), byLogLevel, byTag);
    }

    private DateStats updateDateStats(DateStats dateStats, LogEntry entry) {
        Map<LevelEnum, Integer> byLogLevel = new HashMap<>(Map.copyOf(dateStats.byLogLevel()));
        byLogLevel.merge(entry.getLevel(), 1, Integer::sum);
        Map<String, Integer> byTag = new HashMap<>(Map.copyOf(dateStats.byTag()));
        Arrays.stream(entry.getTags()).forEach(tag -> {
            byTag.merge(tag, 1, Integer::sum);
        });

        return new DateStats(dateStats.date(), byLogLevel, byTag);
    }
}

package com.log_statistcs_service.logic;

import com.log_statistcs_service.domain.DateStats;
import com.log_statistcs_service.domain.LevelEnum;
import com.log_statistcs_service.domain.LogEntry;
import com.log_statistcs_service.domain.OverallStats;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalculateStats {

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

    public DateStats calculateDateStats(LocalDate date, List<LogEntry> logEntries) {
        Map<LevelEnum, Integer> byLogLevel= new HashMap<>();
        Map<String, Integer> byTags = new HashMap<>();
        for (LogEntry logEntry : logEntries) {
            if (date.equals(logEntry.getTimestampDate())) {
                byLogLevel.merge(logEntry.getLevel(), 1, Integer::sum);
                String[] tags = logEntry.getTags();
                for (String tag : tags) {
                    byTags.merge(tag, 1, Integer::sum);
                }
            }
        }
        return new DateStats(date, byLogLevel, byTags);
    }
}

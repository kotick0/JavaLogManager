package logic;

import domain.DateStats;
import domain.LevelEnum;
import domain.LogEntry;
import domain.OverallStats;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogOperations {

    public OverallStats calculateOverallStats(List<LogEntry> logEntries) {
        LogOperations logOperations = new LogOperations();
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

    private Map<LevelEnum, Integer> countLogLevel(List<LogEntry> logEntries) {
        Map<LevelEnum, Integer> levelMap = new HashMap<>();

        for (LogEntry logEntry : logEntries) {
            LevelEnum currentLevel = logEntry.getLevel();
            levelMap.merge(currentLevel, 1, Integer::sum);
        }

        return levelMap;
    }

    private Map<String, Integer> countTags(List<LogEntry> logEntries) {
        Map<String, Integer> tagsMap = new HashMap<>();

        for (LogEntry logEntry : logEntries) {
            String[] tags = logEntry.getTags();

            for (String tag : tags) {
                tagsMap.merge(tag, 1, Integer::sum);
            }
        }
        return tagsMap;
    }

    private Map<LocalDate, Integer> countDates(List<LogEntry> logEntries) {
        Map<LocalDate, Integer> datesMap = new HashMap<>();
        for (LogEntry logEntry : logEntries) {
            LocalDate date = logEntry.getTimestampDate();
            datesMap.merge(date, 1, Integer::sum);
        }
        return datesMap;
    }


    private int countAllLogs(List<LogEntry> logEntries) {
        int overallCount = 0;
        for (LogEntry logEntry : logEntries) {
            overallCount++;
        }
        return overallCount;
    }
}

package logic;

import domain.LevelEnum;
import domain.LogEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogOperations {

    public Map<LevelEnum, Integer> countLogLevel(List<LogEntry> logEntries) {
        Map<LevelEnum, Integer> levelMap = new HashMap<>();

        for (LogEntry logEntry : logEntries) {
            LevelEnum currentLevel = logEntry.getLevel();
            levelMap.merge(currentLevel, 1, Integer::sum);
        }

        return levelMap;
    }

    public Map<String, Integer> countTags(List<LogEntry> logEntries) {
        Map<String, Integer> tagsMap = new HashMap<>();

        for (LogEntry logEntry : logEntries) {
            String[] tags = logEntry.getTags();

            for (String tag : tags) {
                tagsMap.merge(tag, 1, Integer::sum);
            }
        }
        return tagsMap;
    }

    public Map<LocalDate, Integer> countDates(List<LogEntry> logEntries) {
        Map<LocalDate, Integer> datesMap = new HashMap<>();
        for (LogEntry logEntry : logEntries) {
            LocalDate date = logEntry.getTimestampDate();
            datesMap.merge(date, 1, Integer::sum);
        }
        return datesMap;
    }

    public int countAllLogs(List<LogEntry> logEntries) {
        int overallCount = 0;
        for (LogEntry logEntry : logEntries) {
            overallCount++;
        }
        return overallCount;
    }
}

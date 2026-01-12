package com.log_statistics_service.logic;

import com.log_statistics_service.domain.LevelEnum;
import com.log_statistics_service.domain.LogEntry;
import com.log_statistics_service.domain.NextLogResult;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class Parser {

    public List<LogEntry> parseLog(List<NextLogResult> logResultArrayList) {
        LevelEnum logLevel;
        LocalDateTime timestamp;
        List<LogEntry> logEntries = new ArrayList<>();

        for (NextLogResult logResult : logResultArrayList) {

            String logResultString = logResult.nextLog();

            String dateTimeString = logResultString.substring(0, 16);
            timestamp = parseDateTime(dateTimeString);
            logLevel = parseLoglevel(logResultString);
            String[] tags = parseTag(logResultString);


            LogEntry logEntry = new LogEntry(timestamp, logLevel, tags);
            logEntries.add(logEntry);
        }
        return logEntries;
    }

    private LocalDateTime parseDateTime(String dateTimeLogPart) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeLogPart, formatter);

        return dateTime;
    }

    public LocalDate parseDate(String dateTimeLogPart) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateTimeLogPart, formatter);

        return date;
    }

    private LevelEnum parseLoglevel(String log) {
        LevelEnum logLevel;

        if (log.contains("DEBUG")) {
            logLevel = LevelEnum.DEBUG;
        } else if (log.contains("INFO")) {
            logLevel = LevelEnum.INFO;
        } else if (log.contains("WARN")) {
            logLevel = LevelEnum.WARN;
        } else {
            logLevel = LevelEnum.ERROR;
        }
        return logLevel;
    }

    public String[] parseTag(String log) {
        String[] tags = new String[0];

        String trimmedLog = log.substring(60).replace(" ", "");
        int indexofStartTag = trimmedLog.lastIndexOf('[');
        int indexofCloseTag = trimmedLog.lastIndexOf(']');

        if (trimmedLog.contains("[") && trimmedLog.contains("]") && indexofCloseTag > indexofStartTag) {
            String tagsTrimmed = (trimmedLog.substring(indexofStartTag + 1, indexofCloseTag) + ",").replace(" ", "");
            tags = tagsTrimmed.split(",");
        }
        return tags;
    }
}

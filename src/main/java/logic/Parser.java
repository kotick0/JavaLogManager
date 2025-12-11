package logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.LogEntry;
import domain.LogEntry.LevelEnum;
import domain.NextLogResult;

public class Parser {

    public List<LogEntry> parseLog(List<NextLogResult> logResultArrayList) {
        LevelEnum logLevel;
        Map<LevelEnum, Integer> levelMap = new HashMap<>();
        Map<String, Integer> tagMap = new HashMap<>();
        LocalDateTime timestamp;
        List<LogEntry> logEntries = new ArrayList<>();

        for(NextLogResult logResult : logResultArrayList) {

            String logResultString = logResult.nextLog();

             String dateTimeString = logResultString.substring(0, 16);
            timestamp = parseDateTime(dateTimeString);

            logLevel = parseLoglevel(logResultString);
            levelMap.merge(logLevel, 1, Integer::sum);

            String[] tags = parseTag(logResultString);
                for (String tag : tags) {
                tagMap.merge(tag, 1, Integer::sum);
            }

            LogEntry logEntry = new LogEntry(timestamp, new HashMap<>(levelMap), new HashMap<>(tagMap));
            logEntries.add(logEntry); //fixme: Z jakiegoś powodu kiedy dodaje do listy logEntries. Wartości sa nie per log. chyba .merge ??

            //System.out.println("From for loop: " + logEntry + "\n\n");
        }
       // System.out.println("From ArrayList: " + logEntries);
        return logEntries;
    }

    private LocalDateTime parseDateTime(String dateTimeLogPart) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeLogPart, formatter);

        return dateTime;
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

    private String[] parseTag(String log) {
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

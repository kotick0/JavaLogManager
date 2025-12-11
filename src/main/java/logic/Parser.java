package logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.LogEntry;
import domain.LogEntry.LevelEnum;

public class Parser {

    public void parseLog(List<NextLogResult> logResultArrayList) {
        LevelEnum logLevel;
        Map<LevelEnum, Integer> levelMap = new HashMap<>();
        Map<String, Integer> tagMap = new HashMap<>();
        LocalDateTime timestamp = LocalDateTime.now();
        List<LogEntry> logEntries = new ArrayList<>();
        for(NextLogResult logResult : logResultArrayList) {

            String logResultString = logResult.nextLog();

             String dateTimeString = logResultString.substring(0, 16);
            timestamp = parseDateTime(dateTimeString);

            logLevel = parseLoglevel(logResultString);
            levelMap.merge(logLevel, 1, Integer::sum);


            String trimmedLog = logResultString.substring(60).replace(" ", ""); //fixme wyrzucic do oddzielnej metody
            int indexofStartTag = trimmedLog.lastIndexOf('[');
            int indexofCloseTag = trimmedLog.lastIndexOf(']');

            if (!(trimmedLog.contains("[")) || !(trimmedLog.contains("]"))) {

                continue; //fixme: Log bez nawias kwadratowy nie zostaje zapisany

            }

            String tagsTrimmed = (trimmedLog.substring(indexofStartTag + 1, indexofCloseTag) + ",").replace(" ", "");
            String[] tags = tagsTrimmed.split(",");
            for (String tag : tags) {
               // System.out.println(tag);
                tagMap.merge(tag, 1, Integer::sum);
            }
            LogEntry logEntry = new LogEntry(timestamp,levelMap,tagMap);
            System.out.println(logEntry); //fixme
        }

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

}

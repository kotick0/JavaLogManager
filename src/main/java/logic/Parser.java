package logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import domain.LogEntry;
import domain.LogEntry.LevelEnum;

public class Parser {

    public void parseLog(ArrayList<NextLogResult> logResultArrayList) {
        LevelEnum logLevel;
        HashMap<LevelEnum, Integer> levelMap = new HashMap<>();
        HashMap<String, Integer> tagMap = new HashMap<>();
        LocalDateTime timestamp = LocalDateTime.now();

        for(NextLogResult logResult : logResultArrayList) {
            String dateTimeString = logResult.toString().substring(0, 16);
            timestamp = parseDateTime(dateTimeString);

            logLevel = parseLoglevel(logResult.toString());
            levelMap.merge(logLevel, 1, Integer::sum);


            String trimmedLog = logResult.toString().substring(60).replace(" ", "");;
            int indexofStartTag = trimmedLog.lastIndexOf('[');
            int indexofCloseTag = trimmedLog.lastIndexOf(']');
            if (indexofStartTag == -1 || indexofCloseTag == -1 || indexofStartTag > indexofCloseTag) {
                continue;
            }
            String tagsTrimmed = (trimmedLog.substring(indexofStartTag + 1, indexofCloseTag) + ",").replace(" ", "");
            String[] tags = tagsTrimmed.split(",");
            for (String tag : tags) {
               // System.out.println(tag);
                tagMap.merge(tag, 1, Integer::sum);
            }

            LogEntry logEntry = new LogEntry(timestamp, levelMap, tagMap);

            System.out.println(logEntry.toString()); //fixme Zjada log z roku 2022 bo prawdopodonie nie znjaduje tagów \\ "[" "]"
        }

//        System.out.println(tagMap.toString());
//        System.out.println(levelMap.toString()); //fixme
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

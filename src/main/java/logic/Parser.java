package logic;

import domain.LogEntry;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import domain.LogEntry.LevelEnum;

public class Parser {

    public void parseLog(ArrayList<NextLogResult> logResultArrayList) {
        LevelEnum logLevel;
        HashMap<LevelEnum, Integer> levelMap = new HashMap<>();
        HashMap<String, Integer> tagMap = new HashMap<>();

        for(NextLogResult logResult : logResultArrayList) {
            String dateTimeString = logResult.toString().substring(0, 16);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime timestamp = LocalDateTime.parse(dateTimeString, formatter);

            if (logResult.toString().contains("DEBUG")) {
                logLevel = LevelEnum.DEBUG;
            } else if (logResult.toString().contains("INFO")) {
                logLevel = LevelEnum.INFO;
            } else if (logResult.toString().contains("WARN")) {
                logLevel = LevelEnum.WARN;
            } else {
                logLevel = LevelEnum.ERROR;
            }
            levelMap.merge(logLevel, 1, Integer::sum);

            String currentLine = logResult.toString().substring(50);
            int indexofStartTag = currentLine.lastIndexOf('[');
            int indexofCloseTag = currentLine.lastIndexOf(']');

            if (indexofStartTag == -1 || indexofCloseTag == -1 || indexofStartTag > indexofCloseTag) {
                // brak tagów – nic nie rób
                continue;
            }

            String currentLineTags = currentLine.substring(indexofStartTag + 1, indexofCloseTag);
            System.out.println(currentLineTags);

        }
        System.out.println(levelMap.toString()); //fixme

    }
}

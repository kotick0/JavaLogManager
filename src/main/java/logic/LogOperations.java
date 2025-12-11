package logic;

import domain.LogEntry;
import domain.NextLogResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogOperations extends Parser {

    public int countPerLevel() {
        FileOperations file = new FileOperations();
        List<NextLogResult> logResults = file.readAllFromOffset(0);
        List<LogEntry> logEntries = parseLog(logResults);

        Map<LogEntry.LevelEnum, Integer> logLevelMap = new HashMap<>();
        for(LogEntry logEntry : logEntries) {
            logLevelMap = logEntry.getLevelMap(logLevelMap.size());
        }
        return -1;
    }
}

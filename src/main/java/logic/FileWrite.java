package logic;

import domain.LogEntry;
import domain.NextLogResult;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileWrite {
    public void writeLogFilePerDateTags(List<LogEntry> logEntries, String logFilePath) {
        String fileName;

        int i = 0;
        FileRead fileReader = new FileRead();
        List<NextLogResult> logResults = fileReader.readAllFromOffset(0, logFilePath);

        for (LogEntry logEntry : logEntries) {
            if (logEntry.getTags().length > 0) {
                fileName = logEntry.getTimestampDate() + "_" + Arrays.toString(logEntry.getTags()).replace("[", "").replace("]", "").replace(", ", "_");
            } else {
                fileName = String.valueOf(logEntry.getTimestampDate());
            }
            writeToFile("resources/" + fileName, logResults.get(i).nextLog()); //fixme: Duplikaty, nie dziala dziala
            i++;
        }
    }

    private void writeToFile(String path, String content) {
        try (FileWriter fw = new FileWriter(path, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

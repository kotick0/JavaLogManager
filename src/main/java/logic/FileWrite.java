package logic;

import domain.LogEntry;
import domain.NextLogResult;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileWrite {
    public void CreateFilePerDateTags(List<LogEntry> logEntries) {
        String fileName = "";

        for (LogEntry logEntry : logEntries) {
            if (logEntry.getTags().length > 0) {
                fileName = String.valueOf(logEntry.getTimestampDate()) + "_" + Arrays.toString(logEntry.getTags()).replace("[", "").replace("]", "").replace(", ", "_");
            } else {
                fileName = String.valueOf(logEntry.getTimestampDate());
            }

            writeToFile("resources/" + fileName, ""); //fixme
        }
    }

    public void populateFiles() {

    }

    private void writeToFile(String path, String content) {
        try {
            FileWriter fw = new FileWriter(path);
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

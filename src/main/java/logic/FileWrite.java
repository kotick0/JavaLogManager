package logic;

import domain.LogEntry;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWrite {
    public void writePerDate(List<LogEntry> logEntries) {
        //System.out.println(logEntries);
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

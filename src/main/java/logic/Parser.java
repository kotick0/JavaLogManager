package logic;

import domain.LogEntry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser extends LogEntry {
    public void detectLine() {
        fileOperations file =  new fileOperations();
        String logs = file.fileRead();


        String[] lines = logs.;
        for (String line : lines) {
            System.out.println(line);
        }
    }
}
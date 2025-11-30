package logic;

import domain.LogEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.time.Instant;
import java.time.LocalDateTime;


import static java.lang.IO.print;
import static java.lang.IO.println;

public class fileOperations extends LogEntry {
    private ArrayList<LogEntry> logEntries = new ArrayList<>();
    private int endLine = 5;
    private LocalDateTime endTime = LocalDateTime.now();
    //private end = end - startLine; //TODO:

    public fileOperations() {
        this.endLine = 1;
    }

    public void fileParse(String filePath) throws IOException {
        while (true) {
            if (endLine <= 0) {
                break;
            } else {
                try (BufferedReader reader = Files.newBufferedReader(

                        Paths.get(filePath), StandardCharsets.UTF_8)) {
                }
            }


        }

    }
}


public void openFile() {
}
}



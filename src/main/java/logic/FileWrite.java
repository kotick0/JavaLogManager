package logic;


import domain.LogEntry;
import domain.NextLogResult;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class FileWrite {
    public static void writeToFile(String inputFileName, int offset) {
        //Sprawdzic offset
        FileRead fileRead = new FileRead();
        FileWrite fileWrite = new FileWrite();
        Parser parser = new Parser();
        List<NextLogResult> currentReadLogs;
        List<LogEntry> currentLogsParsed;

        LocalDate date;
        currentReadLogs = fileRead.readAllFromOffset(offset, "resources/test.txt");
        String outputFileName;

        for (NextLogResult log : currentReadLogs) {
            String logResultString = log.nextLog();
            String dateString = logResultString.substring(0, 10);
            date = parser.parseDate(dateString);
            String[] tags = parser.parseTag(logResultString);
            //System.out.println(date + " " + tags.length);
            if (tags.length < 1) {
                outputFileName = String.valueOf(date);
            } else {
                outputFileName = String.valueOf(date) + "_" + Arrays.toString(tags).replace("[", "").replace("]", "").replace(", ", "_");
            }
            System.out.println(outputFileName);
            fileWrite.write("resources/" + outputFileName, logResultString); //fixme
        }
    }

    private void write(String fileName, String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

            writer.write(content + '\n'); //fixme
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




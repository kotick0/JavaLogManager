package logic;


import domain.LogEntry;
import domain.NextLogResult;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FileWrite {
    public static void writeToFile(String path, int offset) {
        //Sprawdzic offset
        FileRead fileRead = new FileRead();
        FileWrite fileWrite = new FileWrite();
        Parser parser = new Parser();
        List<NextLogResult> currentReadLogs;
        List<LogEntry> currentLogsParsed;

        LocalDate date;
        currentReadLogs = fileRead.readAllFromOffset(offset, path);
        String outputFileName;

        for (NextLogResult log : currentReadLogs) {
            String logResultString = log.nextLog();
            String dateString = logResultString.substring(0, 10);
            date = parser.parseDate(dateString);
            String[] tags = parser.parseTag(logResultString);

            if (tags.length > 0) {
                for (String tag : tags) {
                    outputFileName = String.valueOf(date) + "_" + tag;
                    fileWrite.write("output_log/per_tag/" + outputFileName + ".log", logResultString); //fixme
                }
            }
            outputFileName = String.valueOf(date);
            fileWrite.write("output_log/per_date/" + outputFileName + ".log", logResultString); //fixme
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




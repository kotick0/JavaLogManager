package com.log_statistics_service.logic;


import com.log_statistics_service.domain.LogEntry;
import com.log_statistics_service.domain.NextLogResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
public class FileWrite {

    private final FileRead fileRead;
    private final Parser parser;

    @Value("${dir.output_path}")
    private String outputPath;

    public FileWrite(FileRead fileRead, Parser parser) {
        this.fileRead = fileRead;
        this.parser = parser;
    }

    public void writeToFile(List<NextLogResult> logResults) {
        //Sprawdzic offset

        LocalDate date;
        String outputFileName;

        for (NextLogResult log : logResults) {
            String logResultString = log.nextLog();
            String dateString = logResultString.substring(0, 10);
            date = parser.parseDate(dateString);
            String[] tags = parser.parseTag(logResultString);
            createDirectories();
            if (tags.length > 0) {
                for (String tag : tags) {
                    outputFileName = String.valueOf(date) + "_" + tag;
                    write(outputPath + "/per_tag/" + outputFileName + ".log", logResultString); //fixme
                }
            }
            outputFileName = String.valueOf(date);
            write(outputPath + "/per_date/" + outputFileName + ".log", logResultString); //fixme
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

    private void createDirectories() {
        List<Path> dirs = List.of(Path.of(outputPath), Path.of(outputPath + "/per_tag"), Path.of(outputPath + "/per_date"));

        for (Path directory : dirs) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}




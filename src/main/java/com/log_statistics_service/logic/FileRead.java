package com.log_statistics_service.logic;

import com.log_statistics_service.domain.NextLogResult;
import org.apache.el.stream.Stream;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class FileRead {


    public FileRead() {
    }

    //  mutable on scanner
//    private static void skipLines(int offset, Scanner scanner) {
//        for (int i = 0; i < offset; i++) {
//            //fixme 99.25% cpu time
//            if(scanner.hasNextLine()) {
//                scanner.nextLine();
//            }
//        }
//    }
    private static void skipLines(int offset, BufferedReader reader) throws IOException {
        for (int i = 0; i < offset; i++) {
            if (reader.readLine() == null) {
                break;
            }
        }
    }

    public NextLogResult readNextLog(int offset, String inputFile) {

        StringBuilder lines = new StringBuilder();
        try (BufferedReader reader = new BufferedReader((new FileReader(inputFile)))) { //fixme
            skipLines(offset, reader);
            // wczytać pierwszy log (pierwsze x lini, do kolejnego rozpoczęcia logu)
            String line = reader.readLine();
            if (line == null) {
                return new NextLogResult("", offset);
            }
            boolean isLogStart = isLogStart(line);
            if (!isLogStart) {
                throw new IllegalArgumentException("File must start with a log line");
            }
            lines.append(line);
            offset++;
            // czytać do momentu kolejnego rozpoczęcia loga
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                if (isLogStart(nextLine)) {
                    return new NextLogResult(lines.toString(), offset);
                } else {
                    lines.append("\n").append(nextLine);
                    offset++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new NextLogResult(lines.toString(), offset);
    }

    public List<NextLogResult> readAllFromOffset(int offset, String inputFile) {

        List<NextLogResult> logList = new ArrayList<>();

        int currentOffset = offset;
        boolean shouldContinue = true;
        while (shouldContinue) {
            NextLogResult result = readNextLog(currentOffset, inputFile);
            currentOffset = result.offset();
            if (!result.nextLog().isEmpty()) {
                logList.add(result);
            } else {
                shouldContinue = false;
            }
        }
        return logList;
    }

    private boolean isLogStart(String currentLine) {
        String trimmed = currentLine.trim();
        StringBuilder sb = new StringBuilder();
        sb.append(trimmed);
        sb.setLength(23);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        boolean isDate;
        try {
            LocalDateTime.parse(sb.toString(), dtf);
            isDate = true;
        } catch (DateTimeParseException e) {
            isDate = false;
        }
        if (isDate && trimmed.charAt(24) == '[') {
            return true;
        } else {
            return false;
        }

    }

}



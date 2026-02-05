package com.log_statistics_service.logic;

import com.log_statistics_service.domain.NextLogResult;
import org.springframework.stereotype.Service;


import java.io.RandomAccessFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileRead {
    public FileRead() {
    }

    public NextLogResult readNextLog(long offset, String inputFile) {
        StringBuilder logBuilder = new StringBuilder();
        try (RandomAccessFile raf = new RandomAccessFile(inputFile, "r")) {
            raf.seek(offset);
            String line = raf.readLine();
            if (line == null) {
                return new NextLogResult("", offset);
            }
            if (!isLogStart(line)) {
                throw new IllegalArgumentException("Log start line is invalid");
            }
            logBuilder.append(line).append("\n");
            offset = raf.getFilePointer();

            String nextLine;
            while ((nextLine = raf.readLine()) != null) {
                if (isLogStart(nextLine)) {
                    return new NextLogResult(logBuilder.toString(), offset);
                } else {
                    logBuilder.append(nextLine).append("\n");
                    offset = raf.getFilePointer();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new NextLogResult(logBuilder.toString(), offset);
    }


    public List<NextLogResult> readAllFromOffset(long offset, String inputFile) {

        List<NextLogResult> logList = new ArrayList<>();

        long currentOffset = offset;
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
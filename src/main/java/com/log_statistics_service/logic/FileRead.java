package com.log_statistics_service.logic;

import com.log_statistics_service.domain.NextLogResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class FileRead {

    @Value("${file.input_path}")
    private String filePath;

    public FileRead() {
    }

    public NextLogResult readNextLog(int offset) {
        StringBuilder lines = new StringBuilder();
        try (Scanner scanner = new Scanner(Paths.get(filePath))) {
            skipLines(offset, scanner);
            // wczytać pierwszy log (pierwsze x lini, do kolejnego rozpoczęcia logu)
            if (!scanner.hasNextLine()) {
                return new NextLogResult("", offset);
            }
            String firstLine = scanner.nextLine();
            boolean isLogStart = isLogStart(firstLine);
            if (!isLogStart) {
                throw new IllegalArgumentException("File starts with no-log line");
            }
            lines.append(firstLine);
            offset++;
            // czytać do momentu kolejnego rozpoczęcia loga
            while (scanner.hasNextLine()) {
                String nextLine = readNextLine(scanner);
                if (isLogStart(nextLine)) {
                    return new NextLogResult(lines.toString(), offset);
                } else {
                    lines.append(nextLine);
                    offset++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new NextLogResult(lines.toString(), offset);
    }

    public List<NextLogResult> readAllFromOffset(int offset) {

        List<NextLogResult> logList = new ArrayList<>();

        int currentOffset = offset;
        boolean shouldContinue = true;
        while (shouldContinue) {
            NextLogResult result = readNextLog(currentOffset);
            currentOffset = result.offset();
            if (!result.nextLog().isEmpty()) {
                logList.add(result);
            } else {
                shouldContinue = false;
            }
        }
        return logList;
    }

    // mutable on scanner
    private static void skipLines(int offset, Scanner scanner) {
        for (int i = 0; i < offset; i++) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        }
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

    private String readNextLine(Scanner scanner) {
        if (scanner.hasNextLine()) {
            return "\n" + scanner.nextLine();
        }
        return "";
    }

}



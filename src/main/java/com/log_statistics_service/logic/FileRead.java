package com.log_statistics_service.logic;

import com.log_statistics_service.domain.NextLogResult;
import org.springframework.stereotype.Service;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;

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
            BufferedInputStream bis = new BufferedInputStream(
                    Channels.newInputStream(raf.getChannel()), 8192);

            LineRead result = bufferedReadLine(bis);
            if (result == null) {
                return new NextLogResult("", offset);
            }
            if (!isLogStart(result.line())) {
                throw new IllegalArgumentException("Log start line is invalid");
            }
            logBuilder.append(result.line()).append("\n");
            offset += result.bytesConsumed();

            LineRead next;
            while ((next = bufferedReadLine(bis)) != null) {
                if (isLogStart(next.line())) {
                    return new NextLogResult(logBuilder.toString(), offset);
                } else {
                    logBuilder.append(next.line()).append("\n");
                    offset += next.bytesConsumed();
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

    private LineRead bufferedReadLine(BufferedInputStream bis) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(256);
        long count = 0;
        int b;
        while ((b = bis.read()) != -1) {
            count++;
            if (b == '\n') {
                return new LineRead(baos.toString(StandardCharsets.ISO_8859_1), count);
            }
            if (b == '\r') {
                bis.mark(1);
                int next = bis.read();
                if (next == '\n') {
                    count++;
                } else if (next != -1) {
                    bis.reset();
                }
                return new LineRead(baos.toString(StandardCharsets.ISO_8859_1), count);
            }
            baos.write(b);
        }
        if (baos.size() == 0) {
            return null;
        }
        return new LineRead(baos.toString(StandardCharsets.ISO_8859_1), count);
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

    private record LineRead(String line, long bytesConsumed) {}
}

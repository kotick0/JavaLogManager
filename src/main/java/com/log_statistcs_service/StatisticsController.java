package com.log_statistcs_service;

import domain.DateStats;
import domain.LogEntry;
import domain.NextLogResult;
import domain.OverallStats;
import logic.FileRead;
import logic.LogOperations;
import logic.Parser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class StatisticsController {

    @GetMapping("/OverallStatistics")
    public OverallStats getOverallStatistics(@RequestParam Integer offset, @RequestParam String path) {
        FileRead fileReader = new FileRead();
        Parser parser = new Parser();
        LogOperations logOperations = new LogOperations();

        List<NextLogResult> fileReadResults = fileReader.readAllFromOffset(offset, path);
        List<LogEntry> parserResult = parser.parseLog(fileReadResults);

        return logOperations.calculateOverallStats(parserResult);
    }

    @GetMapping("/DateStatistics")
    public DateStats getDateStatistics(@RequestParam LocalDate date, @RequestParam String path) {
        FileRead fileReader = new FileRead();
        Parser parser = new Parser();
        LogOperations logOperations = new LogOperations();

        List<NextLogResult> fileReadResults = fileReader.readAllFromOffset(0, path); //fixme
        List<LogEntry> parserResult = parser.parseLog(fileReadResults);

        return logOperations.calculateDateStats(date, parserResult);
    }
}

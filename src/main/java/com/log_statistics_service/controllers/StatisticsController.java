package com.log_statistics_service.controllers;

import com.log_statistics_service.domain.DateStats;
import com.log_statistics_service.domain.OverallStats;
import com.log_statistics_service.logic.CalculateStats;
import com.log_statistics_service.logic.FileRead;
import com.log_statistics_service.logic.Parser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class StatisticsController {
    private final FileRead fileRead;
    private final Parser parser;
    private final CalculateStats calculateStats;

    public StatisticsController(FileRead fileRead, Parser parser, CalculateStats calculateStats) {
        this.fileRead = fileRead;
        this.parser = parser;
        this.calculateStats = calculateStats;
    }

    @GetMapping("/OverallStatistics")
    public OverallStats getOverallStatistics() {

        //List<NextLogResult> fileReadResults = fileRead.readAllFromOffset(offset);
        // List<LogEntry> parserResult = parser.parseLog(fileReadResults);

        //return calculateStats.calculateOverallStats(parserResult);
        return null; //fixme
    }

    @GetMapping("/DateStatistics")
    public DateStats getDateStatistics(@RequestParam LocalDate date) {

        //List<NextLogResult> fileReadResults = fileRead.readAllFromOffset(0); //fixme
        //List<LogEntry> parserResult = parser.parseLog(fileReadResults);

        //return calculateStats.calculateDateStats(date, parserResult);
        return null;
    }
}

package com.log_statistcs_service.controllers;

import com.log_statistcs_service.domain.DateStats;
import com.log_statistcs_service.domain.LogEntry;
import com.log_statistcs_service.domain.NextLogResult;
import com.log_statistcs_service.domain.OverallStats;
import com.log_statistcs_service.logic.CalculateStats;
import com.log_statistcs_service.logic.FileRead;
import com.log_statistcs_service.logic.LogOperations;
import com.log_statistcs_service.logic.Parser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

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
    public OverallStats getOverallStatistics(@RequestParam Integer offset) {

        List<NextLogResult> fileReadResults = fileRead.readAllFromOffset(offset);
        List<LogEntry> parserResult = parser.parseLog(fileReadResults);

        return calculateStats.calculateOverallStats(parserResult);
    }

    @GetMapping("/DateStatistics")
    public DateStats getDateStatistics(@RequestParam LocalDate date) {

        List<NextLogResult> fileReadResults = fileRead.readAllFromOffset(0); //fixme
        List<LogEntry> parserResult = parser.parseLog(fileReadResults);

        return calculateStats.calculateDateStats(date, parserResult);
    }
}

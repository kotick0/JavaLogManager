package com.log_statistics_service.controllers;

import com.log_statistics_service.domain.DateStats;
import com.log_statistics_service.domain.OverallStats;
import com.log_statistics_service.logic.CalculateStats;
import com.log_statistics_service.logic.FileRead;
import com.log_statistics_service.logic.Parser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.File;
import java.time.LocalDate;

@RestController
public class StatisticsController {
    private final FileRead fileRead;
    private final Parser parser;
    private final CalculateStats calculateStats;

    @Value("${dir.statistics_path}")
    private String statisticsPath;

    public StatisticsController(FileRead fileRead, Parser parser, CalculateStats calculateStats) {
        this.fileRead = fileRead;
        this.parser = parser;
        this.calculateStats = calculateStats;
    }

    @GetMapping("/OverallStatistics")
    public OverallStats getOverallStatistics() {
        File file = new File(statisticsPath + "OverallStatistics.json");
        JsonMapper mapper = JsonMapper.builder()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();
        OverallStats stats = mapper.readValue(file, OverallStats.class);
        return stats; // Spring converts this to JSON automatically
    }

    @GetMapping("/DateStatistics")
    public DateStats getDateStatistics(@RequestParam LocalDate date) {

        //List<NextLogResult> fileReadResults = fileRead.readAllFromOffset(0); //fixme
        //List<LogEntry> parserResult = parser.parseLog(fileReadResults);

        //return calculateStats.calculateDateStats(date, parserResult);
        return null;
    }
}

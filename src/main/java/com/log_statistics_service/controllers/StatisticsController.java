package com.log_statistics_service.controllers;

import com.log_statistics_service.domain.DateStats;
import com.log_statistics_service.domain.LogEntry;
import com.log_statistics_service.domain.NextLogResult;
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
import java.util.List;

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
        return stats;
    }

    @GetMapping("/DateStatistics")
    public List<DateStats> getDateStatistics() {

        List<NextLogResult> fileReadResults = fileRead.readAllFromOffset(0, "input_log/test.log"); //fixme
        List<LogEntry> parserResult = parser.parseLog(fileReadResults);

        return calculateStats.calculateDateStats(parserResult);
//        return null;
    }
}

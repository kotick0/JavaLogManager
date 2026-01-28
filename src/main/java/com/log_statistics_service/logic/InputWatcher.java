package com.log_statistics_service.logic;

import com.log_statistics_service.database.OffsetEntries;
import com.log_statistics_service.database.OffsetEntriesRepository;
import com.log_statistics_service.domain.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

@Service
@EnableScheduling
public class InputWatcher {

    private final FileRead fileRead;
    private final FileWrite fileWrite;
    private final CalculateStats calculateStats;
    private final Parser parser;
    private final OffsetEntriesRepository offsetEntriesRepository;

    @Value("${dir.input_path}")
    private String inputDirectory;

    @Value("${dir.statistics_path}")
    private String statisticsPath;

    public InputWatcher(FileRead fileRead, OffsetEntriesRepository lastLineRepository, FileWrite fileWrite, CalculateStats calculateStats, Parser parser) {
        this.fileRead = fileRead;
        this.offsetEntriesRepository = lastLineRepository;
        this.fileWrite = fileWrite;
        this.calculateStats = calculateStats;
        this.parser = parser;
    }

    @Scheduled(fixedRate = 120000)
    private void populateDatabase() {
        File directory = new File(inputDirectory);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (offsetEntriesRepository.findByInputFile(file.getName()).isEmpty()) {
                    offsetEntriesRepository.save(new OffsetEntries(file.getName(), 0));
                }
            }
        }

    }

    @Scheduled(fixedRate = 60000)
    private void watchDirectory() {
        List<String> fileNames = Stream.of(new File(inputDirectory).listFiles()).filter(file -> !file.isDirectory()).map(File::getName).toList();
        List<OffsetEntries> offsetEntriesList = offsetEntriesRepository.findAllByInputFileIn(fileNames);
        List<LogEntry> logEntries = new ArrayList<>();
        for (OffsetEntries offsetEntry : offsetEntriesList) {
            String inputPath = inputDirectory + offsetEntry.getInputFile();
            List<NextLogResult> currentLogList = fileRead.readAllFromOffset(offsetEntry.getLastLine(), inputPath);
            if (!currentLogList.isEmpty()) {
                if (currentLogList.getLast().offset() != offsetEntry.getLastLine()) { //fixme naprawic brak zapisu
                    logEntries.addAll(parser.parseLog(currentLogList));
                    fileWrite.writeToFile(currentLogList);
                    offsetEntriesRepository.save(new OffsetEntries(offsetEntry.getInputFile(), currentLogList.getLast().offset()));
                }
            }
        }
        OverallStats overallStats = calculateStats.calculateOverallStats(logEntries);
        List<DateStats> dateStats = calculateStats.calculateDateStats(logEntries);
        writeOverallStatisticsToJSON(overallStats);
        writeDateStatisticsToJSON(dateStats);
    }

    private void writeOverallStatisticsToJSON(OverallStats StatsObject) {
        JsonMapper mapper = JsonMapper.builder().enable(SerializationFeature.INDENT_OUTPUT).build();
        Path path = Path.of(statisticsPath + "OverallStatistics.json");
        OverallStats previousStats;
        if (Files.exists(path)) {
            previousStats = mapper.readValue(path.toFile(), OverallStats.class);
        } else {
            previousStats = new OverallStats(new HashMap<>(), new HashMap<>(), new HashMap<>());
        }

        previousStats.merge(StatsObject);

        mapper.writeValue(path.toFile(), previousStats);
    }

    private void writeDateStatisticsToJSON(List<DateStats> StatsObject) {
        JsonMapper mapper = JsonMapper.builder().enable(SerializationFeature.INDENT_OUTPUT).build();
        Path path = Path.of(statisticsPath + "DateStatistics.json");

        List<DateStats> previousStats;
        if (Files.exists(path)) {
            previousStats = mapper.readValue(path.toFile(), new TypeReference<List<DateStats>>() {
            });
        } else {
            previousStats = List.of();
        }
        List<DateStats> merged = mergeLists(previousStats, StatsObject);
        mapper.writeValue(path.toFile(), merged);
    }


    private List<DateStats> mergeLists(List<DateStats> previous, List<DateStats> current) {
        Map<LocalDate, DateStats> result = new HashMap<>();

        for (DateStats stats : previous) {
            result.put(stats.date(), stats);
        }

        for (DateStats stats : current) {
            result.merge(stats.date(), stats, DateStats::merge);
        }

        return new ArrayList<>(result.values());
    }

}

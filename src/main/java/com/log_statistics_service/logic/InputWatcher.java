package com.log_statistics_service.logic;

import com.log_statistics_service.database.OffsetEntries;
import com.log_statistics_service.database.OffsetEntriesRepository;
import com.log_statistics_service.domain.LogEntry;
import com.log_statistics_service.domain.NextLogResult;
import com.log_statistics_service.domain.OverallStats;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

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

    public InputWatcher(FileRead fileRead, OffsetEntriesRepository lastLineRepository, FileWrite fileWrite,  CalculateStats calculateStats, Parser parser) {
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
            if (files.length > offsetEntriesRepository.count()) {
                for (File file : files) {
                    offsetEntriesRepository.save(new OffsetEntries(file.getName(), 0));
                }
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    private void watchDirectory() {
        List<OffsetEntries> offsetEntriesList = offsetEntriesRepository.findAll();
        for (OffsetEntries offsetEntry : offsetEntriesList) {
            String inputPath = inputDirectory + "/" + offsetEntry.getInputFile();
            List<NextLogResult> currentLogList = fileRead.readAllFromOffset(offsetEntry.getLastLine(), inputPath);
            if(!currentLogList.isEmpty()){
                if (currentLogList.getLast().offset() != offsetEntry.getLastLine()) {
                    // TODO Licz statystyki
//                    List<NextLogResult> fileReadResults = fileRead.readAllFromOffset(offsetEntry.getLastLine(), inputPath);
//                    List<LogEntry> parserResult = parser.parseLog(fileReadResults);
                    //calculateStats.calculateOverallStats(parserResult);
                    fileWrite.writeToFile(inputPath, offsetEntry.getLastLine());
                    offsetEntriesRepository.save(new OffsetEntries(offsetEntry.getInputFile(), currentLogList.getLast().offset()));

                }
            }
            System.out.println(currentLogList + "\n\n");
        }
        System.out.println("Watching directory: " + inputDirectory); //fixme

    }
}

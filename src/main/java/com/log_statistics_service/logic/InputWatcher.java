package com.log_statistics_service.logic;

import com.log_statistics_service.database.OffsetEntries;
import com.log_statistics_service.database.OffsetEntriesRepository;
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
    private final OffsetEntriesRepository offsetEntriesRepository;

    @Value("${dir.input_path}")
    private String inputDirectory;

    public InputWatcher(FileRead fileRead, OffsetEntriesRepository lastLineRepository) {
        this.fileRead = fileRead;
        this.offsetEntriesRepository = lastLineRepository;
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

    @Scheduled(fixedRate = 60000) //fixme: na 1 min
    private void watchDirectory() {
        List<OffsetEntries> offsetEntriesList = offsetEntriesRepository.findAll();
        for (OffsetEntries offsetEntry : offsetEntriesList) {
            System.out.println(offsetEntry.getLastLine());
        }
        System.out.println("Watching directory: " + inputDirectory); //fixme

    }
}

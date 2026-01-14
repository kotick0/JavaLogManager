package com.log_statistics_service.logic;

import com.log_statistics_service.database.LastLineRead;
import com.log_statistics_service.database.LastLineRepository;
import com.log_statistics_service.domain.NextLogResult;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.file.*;
import java.util.List;

@Service
@EnableScheduling
public class InputWatcher {

    private final FileRead fileRead;
    private final LastLineRepository lastLineRepository;

    @Value("${dir.input_path}")
    private String inputDirectory;

    public InputWatcher(FileRead fileRead, LastLineRepository lastLineRepository) {
        this.fileRead = fileRead;
        this.lastLineRepository = lastLineRepository;
    }

    @Scheduled(fixedRate = 5000) //fixme: na 1 min
    private void watchDirectory() {
        System.out.println("Watching directory: " + inputDirectory); //fixme
        lastLineRepository.save(new LastLineRead(inputDirectory + "/" + "test.log", 0));
    }
}

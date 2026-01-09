package com.log_statistcs_service.logic;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;

@Service
public class InputWatcher {
    @Value("${dir.input_path}")
    private String inputDirectory;

    @PostConstruct
    public void watchDirectory() {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path directory = Paths.get(inputDirectory);
            WatchKey watchKey = directory.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    System.out.println(event.kind());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

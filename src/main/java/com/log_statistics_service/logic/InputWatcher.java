package com.log_statistics_service.logic;

import com.log_statistics_service.domain.NextLogResult;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.file.*;
import java.util.List;

@Service
public class InputWatcher implements ApplicationRunner {

    private final FileRead fileRead;

    @Value("${dir.input_path}")
    private String inputDirectory;

    public InputWatcher(FileRead fileRead) {
        this.fileRead = fileRead;
    }

    @Override
    public void run(ApplicationArguments args) {
        watchDirectory();
    }

    private void watchDirectory() {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path directory = Paths.get(inputDirectory);
            WatchKey watchKey = directory.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
            int offset = 0;
            while (true) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {
//                    Path file = directory.resolve((Path) event.context());
                    List<NextLogResult> logList = fileRead.readAllFromOffset(offset);
                    System.out.println(logList.toString());
                    //offset = logList.getLast().offset();
                    //System.out.println(event.kind() + ": " + event.context() + ": " + file.toFile().lastModified());
                    System.out.println(offset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

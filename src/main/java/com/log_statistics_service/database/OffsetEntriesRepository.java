package com.log_statistics_service.database;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface OffsetEntriesRepository extends JpaRepository<OffsetEntries, Long> {
    Optional<OffsetEntries> findByInputFile(String inputFile);
    List<OffsetEntries> findAllByInputFileIn(List<String> inputFiles);
}
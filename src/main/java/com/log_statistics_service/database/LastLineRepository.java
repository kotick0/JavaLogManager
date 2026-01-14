package com.log_statistics_service.database;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LastLineRepository extends JpaRepository<LastLineRead, Long> {
    Optional<LastLineRead> findByInputFile(String inputFile);
}
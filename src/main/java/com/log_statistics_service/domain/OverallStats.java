package com.log_statistics_service.domain;

import java.time.LocalDate;
import java.util.Map;

public record OverallStats(
        Map<LevelEnum, Integer> byLogLevel,
        Map<String, Integer> byTag,
        Map<LocalDate, Integer> byDate
) {
}

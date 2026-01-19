package com.log_statistics_service.domain;

import java.time.LocalDate;
import java.util.Map;

public record OverallStats(
        Map<LevelEnum, Integer> byLogLevel,
        Map<String, Integer> byTag,
        Map<LocalDate, Integer> byDate
) {
    public void merge(OverallStats other) {
        other.byLogLevel.forEach((k, v) -> this.byLogLevel.merge(k, v, Integer::sum));
        other.byTag.forEach((k, v) -> this.byTag.merge(k, v, Integer::sum));
        other.byDate.forEach((k, v) -> this.byDate.merge(k, v, Integer::sum));
    }
}

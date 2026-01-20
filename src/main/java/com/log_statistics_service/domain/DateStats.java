package com.log_statistics_service.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public record DateStats(
        LocalDate date,
        Map<LevelEnum, Integer> byLogLevel,
        Map<String, Integer> byTag
) {
    public void merge(DateStats other) {
        other.byLogLevel.forEach((k, v) -> this.byLogLevel.merge(k, v, Integer::sum));
        other.byTag.forEach((k, v) -> this.byTag.merge(k, v, Integer::sum));
    }
}

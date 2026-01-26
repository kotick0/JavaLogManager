package com.log_statistics_service.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record DateStats(
        LocalDate date,
        Map<LevelEnum, Integer> byLogLevel,
        Map<String, Integer> byTag
) {
    public static DateStats merge(DateStats stats1, DateStats stats2) {
        if(!stats1.date().equals(stats2.date())) {
            ;
        }
        Map<LevelEnum, Integer> mergedLevels = new HashMap<>(stats1.byLogLevel());
        stats2.byLogLevel().forEach((k, v) -> mergedLevels.merge(k, v, Integer::sum));

        Map<String, Integer> mergedTags = new HashMap<>(stats1.byTag());
        stats2.byTag().forEach((k, v) -> mergedTags.merge(k, v, Integer::sum));

        return new DateStats(stats1.date(), mergedLevels, mergedTags);
    }
}

package com.log_statistics_service.logic;

import com.log_statistics_service.domain.DateStats;
import com.log_statistics_service.domain.LevelEnum;
import com.log_statistics_service.domain.LogEntry;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculateStatsTest {

    CalculateStats calculateStats = new CalculateStats(new LogOperations());

    @Test
    void calculateDateStatsSmokeTest() {
        String[] tags1 = new String[]{"ADD", "CREATE"};
        String[] tags2 = new String[]{"ADD", "MODIFY"};
        LogEntry log1 = new LogEntry(LocalDateTime.of(2026, 1, 20, 15, 48, 0), LevelEnum.INFO, tags1);
        LogEntry log2 = new LogEntry(LocalDateTime.of(2026, 1, 20, 15, 48, 0), LevelEnum.INFO, tags2);
        List<LogEntry> input = List.of(log1, log2);
        List<DateStats> result = calculateStats.calculateDateStats(input);

        assertEquals(1, result.size());
        DateStats resultElement = result.getFirst();
        assertTrue(resultElement.date().isEqual(LocalDate.of(2026,1,20)));
        assertEquals(1, resultElement.byLogLevel().size());
        assertEquals(2, (int) resultElement.byLogLevel().get(LevelEnum.INFO));

        assertEquals(3, resultElement.byTag().size());
        assertEquals(2, resultElement.byTag().get("ADD"));
        assertEquals(1, resultElement.byTag().get("CREATE"));
        assertEquals(1, resultElement.byTag().get("MODIFY"));
    }

    @Test
    void calculateDateStatsDifferentTime() {
        String[] tags1 = new String[]{"ADD", "CREATE"};
        String[] tags2 = new String[]{"ADD", "MODIFY"};
        LogEntry log1 = new LogEntry(LocalDateTime.of(2026, 1, 20, 15, 48, 0), LevelEnum.INFO, tags1);
        LogEntry log2 = new LogEntry(LocalDateTime.of(2026, 1, 20, 15, 50, 0), LevelEnum.INFO, tags2);
        List<LogEntry> input = List.of(log1, log2);
        List<DateStats> result = calculateStats.calculateDateStats(input);

        assertEquals(1, result.size());
        DateStats resultElement = result.getFirst();
        assertTrue(resultElement.date().isEqual(LocalDate.of(2026,1,20)));
        assertEquals(1, resultElement.byLogLevel().size());
        assertEquals(2, (int) resultElement.byLogLevel().get(LevelEnum.INFO));

        assertEquals(3, resultElement.byTag().size());
        assertEquals(2, resultElement.byTag().get("ADD"));
        assertEquals(1, resultElement.byTag().get("CREATE"));
        assertEquals(1, resultElement.byTag().get("MODIFY"));
    }

    @Test
    void calculateDateStatsDifferentDates() {
        String[] tags1 = new String[]{"ADD", "CREATE"};
        String[] tags2 = new String[]{"ADD", "MODIFY"};
        LogEntry log1 = new LogEntry(LocalDateTime.of(2026, 1, 20, 15, 48, 0), LevelEnum.INFO, tags1);
        LogEntry log2 = new LogEntry(LocalDateTime.of(2026, 1, 21, 15, 48, 0), LevelEnum.INFO, tags2);
        List<LogEntry> input = List.of(log1, log2);
        List<DateStats> result = calculateStats.calculateDateStats(input);

        assertEquals(2, result.size());
        var sortedResult = result.stream().sorted(Comparator.comparing(DateStats::date)).toList();
        DateStats first = sortedResult.getFirst();
        assertTrue(first.date().isEqual(LocalDate.of(2026,1,20)));
        DateStats last = sortedResult.getLast();
        assertTrue(last.date().isEqual(LocalDate.of(2026,1,21)));
        assertEquals(1, first.byLogLevel().size());
        assertEquals(1, (int) first.byLogLevel().get(LevelEnum.INFO));
        assertEquals(1, last.byLogLevel().size());
        assertEquals(1, (int) last.byLogLevel().get(LevelEnum.INFO));

        assertEquals(2, first.byTag().size());
        assertEquals(1, first.byTag().get("ADD"));
        assertEquals(1, first.byTag().get("CREATE"));
        assertEquals(2, last.byTag().size());
        assertEquals(1, last.byTag().get("ADD"));
        assertEquals(1, last.byTag().get("MODIFY"));
    }
}
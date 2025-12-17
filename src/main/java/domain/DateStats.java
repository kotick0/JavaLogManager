package domain;

import java.time.LocalDate;
import java.util.Map;

public record DateStats(
  LocalDate date,
  Map<LevelEnum, Integer> byLogLevel,
  Map<LevelEnum, Integer> byTag
) {
}

package domain;

import java.util.Map;

public record OverallStats(
  Map<LevelEnum, Integer> byDate,
  Map<LevelEnum, Integer> byLogLevel,
  Map<LevelEnum, Integer> byTag
) {
}

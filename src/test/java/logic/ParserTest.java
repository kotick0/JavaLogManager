package logic;

import domain.LogEntry;
import domain.NextLogResult;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

  @Test
  void adding() {
    Parser parser = new Parser();
    NextLogResult nextLogResult = new NextLogResult(
      "2020-02-05 04:54:23.678 [http-nio-8083-exec-11] WARN p.i.p.d.f.FlightDailyScheduleServiceImpl - [FlightDailySchedule, EPKS, CREATE] FlightDailySchedule test this is a test ()",
      0
    );
    List<LogEntry> parseResultList = parser.parseLog(List.of(nextLogResult));

    assertEquals(1, parseResultList.size());
    LogEntry parseResult = parseResultList.getFirst();
    assertEquals(LocalDateTime.of(2020, 2, 5, 4, 54), parseResult.getTimestamp());
  }
}
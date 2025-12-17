import domain.LogEntry;
import domain.NextLogResult;
import logic.FileRead;
import logic.FileWrite;
import logic.LogOperations;
import logic.Parser;

void main() {
    FileRead file = new FileRead();
    Parser parser = new Parser();
    LogOperations logOperations = new LogOperations();

    List<NextLogResult> logResults = file.readAllFromOffset(0);
    List<LogEntry> parseResultList = parser.parseLog(logResults);
//    logOperations.calculateOverallStats(parseResultList);
//    logOperations.calculateDateStats(date, parseResultList);

    FileWrite fileWrite = new FileWrite();
    fileWrite.writeFilePerDateTags(parseResultList);
}
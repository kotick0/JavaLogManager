import domain.LogEntry;
import domain.NextLogResult;
import logic.FileOperations;
import logic.LogOperations;
import logic.Parser;

void main() {
    FileOperations file = new FileOperations();
    Parser parser = new Parser();
    LogOperations logOperations = new LogOperations();

    List<NextLogResult> logResults = file.readAllFromOffset(0);
    List<LogEntry> parseResultList = parser.parseLog(logResults);
}
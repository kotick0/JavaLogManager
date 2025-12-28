import domain.LogEntry;
import domain.NextLogResult;
import logic.FileRead;
import logic.FileWrite;
import logic.LogOperations;
import logic.Parser;

void main() {
//    FileWrite fileWrite = new FileWrite();
//    FileRead fileReader = new FileRead();
//    Parser parser = new Parser();
//
//    List<NextLogResult> fileReadResults = fileReader.readAllFromOffset(0, "resources/test.txt");
//    List<LogEntry> parserResult = parser.parseLog(fileReadResults);

    //fileWrite.makeFiles(parserResult);
    //System.out.println(this.countStatisticsFromFile(0, "resources/test2.txt"));
    FileWrite.writeToFile("test", 11);

}
public String countStatisticsFromFile(int offset,  String path) {
    FileRead fileReader = new FileRead();
    Parser parser = new Parser();
    LogOperations logOperations = new LogOperations();

    List<NextLogResult> fileReadResults = fileReader.readAllFromOffset(offset, path);
    List<LogEntry> parserResult = parser.parseLog(fileReadResults);

    StringBuilder perDateResult = new StringBuilder();
    for(LogEntry logEntry : parserResult) {
        if(!perDateResult.toString().contains(logEntry.getTimestampDate().toString()))
            perDateResult.append(logOperations.calculateDateStats(logEntry.getTimestampDate(), parserResult).toString()).append("\n");
        }

    return logOperations.calculateOverallStats(parserResult).toString() + "\n\nDate Stats:\n" + perDateResult;
}
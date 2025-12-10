import logic.FileOperations;
import logic.NextLogResult;
import logic.Parser;

void main() {
    FileOperations file = new FileOperations();
//    Parser parser = new Parser();
//    parser.parseLog(file.readNextLog(0).toString());
//    System.out.println(file.readAllFromOffset(1));
//    System.out.println(file.readAllFromOffset(1).size());
    Parser parser = new Parser();
  List<NextLogResult> logResults = file.readAllFromOffset(0);
  parser.parseLog(logResults);
}
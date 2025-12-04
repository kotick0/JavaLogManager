import logic.FileOperations;
import logic.NextLogResult;
import logic.Parser;

void main() {
    FileOperations file = new FileOperations();
    Parser parser = new Parser();

    NextLogResult res = file.readNextLog(0); //NOTE: NIE ROZUMIEM DZIALANIA
    parser.parseLog();
    System.out.println(file.countLines());
}
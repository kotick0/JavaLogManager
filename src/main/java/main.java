import logic.Parser;
import logic.FileOperations;

void main() {
    FileOperations fileOperations = new FileOperations();
    System.out.println(fileOperations.readNextLog(0));
    Parser test = new Parser();
//    test.detectLine();
}
import logic.Parser;
import logic.fileOperations;

void main() {
    fileOperations fileOperations = new fileOperations();
    System.out.println(fileOperations.fileRead());
    Parser test = new Parser();
    test.detectLine();
}
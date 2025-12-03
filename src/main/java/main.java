import logic.FileOperations;
import logic.NextLogResult;
import logic.Parser;

void main() {
//    FileOperations file = new FileOperations();
//    NextLogResult res = file.readNextLog(0);
//    NextLogResult res2 = file.readNextLog(res.offset());
//    NextLogResult res3 = file.readNextLog(17);
//    System.out.println(res);
//    System.out.println(res3); //Nie rozumiem działania
    Parser p = new Parser();
    p.parseLog();
}
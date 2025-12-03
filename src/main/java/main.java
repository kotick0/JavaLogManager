import logic.FileOperations;
import logic.NextLogResult;

void main() {
    FileOperations file = new FileOperations();
    NextLogResult res = file.readNextLog(0);
    NextLogResult res2 = file.readNextLog(res.offset());
    NextLogResult res3 = file.readNextLog(res2.offset());
    System.out.println(res);
    System.out.println(res2); //Nie rozumiem działania

}
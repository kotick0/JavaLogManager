package logic;


public class FileWrite {

}
//    private final Path INPUT_FILE = Paths.get("resorces/test.txt"); //fixme
//    private final Path OFFSET_STATE_FILE = Paths.get("resorces/offset.state"); //fixme
//    private  final Path OUTPUT_DIR = Paths.get("resources/output"); //fixme
//    private FileRead fileReader = new FileRead();
//
//    public void createFilesAndAddLogs() {
//
//    }
    //public void makeFiles(List<LogEntry> logEntries) {
//    String fileName;
//    for (LogEntry logEntry : logEntries) {
//        if (logEntry.getTags().length > 0) {
//            fileName = logEntry.getTimestampDate() + "_" + Arrays.toString(logEntry.getTags()).replace("[", "").replace("]", "").replace(", ", "_");
//        } else {
//            fileName = String.valueOf(logEntry.getTimestampDate());
//        }
//    }
//}




//    public void createFilesAndAddLogs(List<LogEntry> logEntries) {
//        String fileName;
//        FileRead fileReader = new FileRead();
//
//        //        int i = 0;
//            if (logEntry.getTags().length > 0) {
//                fileName = logEntry.getTimestampDate() + "_" + Arrays.toString(logEntry.getTags()).replace("[", "").replace("]", "").replace(", ", "_");
//            } else {
//                fileName = String.valueOf(logEntry.getTimestampDate());
//            }
////
////            Path path = Paths.get("resources/" + fileName);
////            List<NextLogResult> logResults = fileReader.readAllFromOffset(0, "resources/test.txt");
////            writeToFile(String.valueOf(path), logResults.get(i).nextLog());
////            i++;
////        }
//    }
//
//    private void writeToFile(String path, String content) {
//        try (FileWriter fw = new FileWriter(path, true);
//             BufferedWriter bw = new BufferedWriter(fw);
//             PrintWriter out = new PrintWriter(bw)) {
//            out.println(content);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


package logic;


public record NextLogResult(
        String nextLog,
        int offset
) {
    @Override
    public String toString() {
        return nextLog + " offset: " + offset;
    }
}


//public class NextLogResult {
//    private final String nextLog;
//    private final int offset;
//
//    public NextLogResult(String nextLog, int offset) {
//        this.nextLog = nextLog;
//        this.offset = offset;         // NOTE: To samo co to na górze
//    }
//
//    public String getNextLog() {
//        return nextLog;
//    }
//
//    public int getOffset() {
//        return offset;
//    }
//}

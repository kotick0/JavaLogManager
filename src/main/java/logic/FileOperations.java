package logic;

import domain.LogEntry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.nio.file.Paths;

public class FileOperations extends LogEntry {

    public FileOperations() {
    }

    public String readNextLog(int offset) {
      // Skip {offset} number of lines // TODO
        StringBuilder lines = new StringBuilder();
        try (Scanner scanner = new Scanner(Paths.get("resources/test.txt"))) {
          // wczytać pierwszy log (pierwsze x lini, do kolejnego rozpoczęcia logu)
          String firstLine = scanner.nextLine();
          boolean isLogStart = isLogStart(firstLine);
          if (!isLogStart) {
            throw new IllegalArgumentException("File starts with no-log line");
          }
          lines.append(firstLine);
          // czytać do momentu kolejnego rozpoczęcia loga
          while (scanner.hasNextLine()) {
            String nextLine = readNextLine(scanner);
            if (isLogStart(nextLine)) {
              return lines.toString();
            }
            lines.append(nextLine);
            // set offset // TODO
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines.toString();
    }

  private boolean isLogStart(String currentLine) {
        StringBuilder sb =  new StringBuilder();
        sb.append(currentLine);
        sb.setLength(23);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        boolean isDate = false;
        try {
          LocalDateTime.parse(sb.toString(), dtf);
          isDate = true;
      } catch (DateTimeParseException e) {
          isDate = false;
      }
      if(isDate == true && currentLine.charAt(24) == '[') {
          return true;
      } else {
          return false;
      }

  }

  private String readNextLine(Scanner scanner) {
      if (scanner.hasNextLine()) {
        return scanner.nextLine();
      }
      return "";
  }
}




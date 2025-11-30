package logic;

import domain.LogEntry;

import java.util.Arrays;
import java.util.Scanner;
import java.nio.file.Paths;

public class fileOperations extends LogEntry {
    private int offset;
    private int currentLine;

    public fileOperations() {
        this.offset = 1;
        this.currentLine = 0;
    }

    public String fileRead() {
        StringBuilder lines = new StringBuilder();
        try (Scanner scanner = new Scanner(Paths.get("resources\\test.txt"))) {
            while (scanner.hasNextLine() && currentLine < offset) {
                offset++;
                lines.append(scanner.nextLine());
                //TODO: ZAMIENIC NA WYKRYWANIE LINIJEK
            }
            currentLine = offset;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines.toString();
    }
}




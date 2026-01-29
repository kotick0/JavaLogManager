package com.log_statistics_service.database;

import jakarta.persistence.*;

@Entity
@Table()
public class OffsetEntries {

    @Id
    private String inputFile;
    private Integer lastLine;

    protected OffsetEntries() {
    }

    public OffsetEntries(String input_file, Integer last_line) {
        this.inputFile = input_file;
        this.lastLine = last_line;
    }

    public Integer getLastLine() {
        return lastLine;
    }

    public String getInputFile() {
        return inputFile;
    }
}
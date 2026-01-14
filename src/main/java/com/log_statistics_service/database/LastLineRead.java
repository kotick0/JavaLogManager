package com.log_statistics_service.database;

import jakarta.persistence.*;

@Entity
@Table(name = "LastLineRead")
public class LastLineRead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inputFile;
    private Integer lastLine;

    public LastLineRead(String input_file, Integer last_line) {
        this.inputFile = input_file;
        this.lastLine = last_line;
    }
}

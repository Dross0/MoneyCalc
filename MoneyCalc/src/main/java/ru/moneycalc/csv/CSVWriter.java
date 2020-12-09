package ru.moneycalc.csv;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

public class CSVWriter {
    private static final Logger logger = LoggerFactory.getLogger(CSVWriter.class);
    @NotNull
    private final File csvFile;

    public CSVWriter(@NotNull File csvFile) {
        this.csvFile = Objects.requireNonNull(csvFile);
    }

    public CSVWriter(@NotNull String csvFileName) {
        this(new File(csvFileName));
    }

    public void write(@NotNull List<String> data) {
        try (PrintWriter writer = new PrintWriter(csvFile)) {
            for (String string : data) {
                writer.println(string);
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
        }

    }
}

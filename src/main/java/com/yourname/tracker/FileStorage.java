package com.yourname.tracker;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private final Path file;

    public FileStorage(Path file) {
        this.file = file;
    }

    public void save(List<Expense> expenses) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Expense e : expenses) {
            String note = e.getNote().replace(",", ";"); // keep CSV simple
            lines.add(e.getId() + "," + e.getAmount() + "," + e.getCategory()
                    + "," + e.getDate() + "," + note);
        }
        Files.write(file, lines);
    }

    public List<Expense> load() throws IOException {
        List<Expense> result = new ArrayList<>();
        if (!Files.exists(file)) {
            return result; // first run, nothing saved yet
        }
        for (String line : Files.readAllLines(file)) {
            if (line.isBlank()) continue;
            String[] p = line.split(",", 5);
            result.add(new Expense(
                    Integer.parseInt(p[0]),
                    new BigDecimal(p[1]),
                    Category.valueOf(p[2]),
                    LocalDate.parse(p[3]),
                    p.length > 4 ? p[4] : ""));
        }
        return result;
    }
}

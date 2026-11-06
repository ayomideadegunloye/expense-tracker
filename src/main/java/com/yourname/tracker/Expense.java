package com.yourname.tracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Expense {
    private final int id;
    private final BigDecimal amount;
    private final Category category;
    private final LocalDate date;
    private final String note;

    public Expense(int id, BigDecimal amount, Category category, LocalDate date, String note) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidExpenseException("Amount must be greater than zero");
        }
        if (category == null) {
            throw new InvalidExpenseException("Category is required");
        }
        if (date == null) {
            throw new InvalidExpenseException("Date is required");
        }
        this.id = id;
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.category = category;
        this.date = date;
        this.note = note == null ? "" : note.trim();
    }

    public int getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public Category getCategory() { return category; }
    public LocalDate getDate() { return date; }
    public String getNote() { return note; }

    @Override
    public String toString() {
        return String.format("#%d | %s | %-9s | %8s | %s", id, date, category, amount, note);
    }
}

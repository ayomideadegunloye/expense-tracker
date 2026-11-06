package com.yourname.tracker;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ExpenseTracker {
    private final List<Expense> expenses = new ArrayList<>();
    private int nextId = 1;

    public Expense add(BigDecimal amount, Category category, LocalDate date, String note) {
        Expense expense = new Expense(nextId, amount, category, date, note);
        expenses.add(expense);
        nextId++;
        return expense;
    }

    /** Used when loading from file, keeps the original id. */
    public void addExisting(Expense expense) {
        expenses.add(expense);
        nextId = Math.max(nextId, expense.getId() + 1);
    }

    public boolean delete(int id) {
        return expenses.removeIf(e -> e.getId() == id);
    }

    public List<Expense> getAll() {
        return new ArrayList<>(expenses);
    }

    public BigDecimal total() {
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<Category, BigDecimal> totalsByCategory() {
        return expenses.stream().collect(Collectors.groupingBy(
                Expense::getCategory,
                TreeMap::new,
                Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)));
    }

    public Map<YearMonth, BigDecimal> totalsByMonth() {
        return expenses.stream().collect(Collectors.groupingBy(
                e -> YearMonth.from(e.getDate()),
                TreeMap::new,
                Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)));
    }
}

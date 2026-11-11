package com.mide.tracker;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        FileStorage storage = new FileStorage(Path.of("expenses.csv"));
        ExpenseTracker tracker = new ExpenseTracker();
        storage.load().forEach(tracker::addExisting);

        Scanner in = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Expense Tracker ===");
            System.out.println("1) Add expense");
            System.out.println("2) List expenses");
            System.out.println("3) Delete expense");
            System.out.println("4) Totals by category");
            System.out.println("5) Totals by month");
            System.out.println("6) Save and quit");
            System.out.print("Choose: ");

            switch (in.nextLine().trim()) {
                case "1" -> addExpense(in, tracker);
                case "2" -> tracker.getAll().forEach(System.out::println);
                case "3" -> {
                    System.out.print("Id to delete: ");
                    try {
                        boolean ok = tracker.delete(Integer.parseInt(in.nextLine().trim()));
                        System.out.println(ok ? "Deleted." : "No expense with that id.");
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a number.");
                    }
                }
                case "4" -> tracker.totalsByCategory()
                        .forEach((c, t) -> System.out.printf("%-10s %8s%n", c, t));
                case "5" -> tracker.totalsByMonth()
                        .forEach((m, t) -> System.out.printf("%s %8s%n", m, t));
                case "6" -> {
                    storage.save(tracker.getAll());
                    System.out.println("Saved. Total spent: " + tracker.total());
                    running = false;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void addExpense(Scanner in, ExpenseTracker tracker) {
        try {
            System.out.print("Amount: ");
            BigDecimal amount = new BigDecimal(in.nextLine().trim());
            System.out.print("Category (FOOD, TRANSPORT, RENT, FUN, OTHER): ");
            Category category = Category.valueOf(in.nextLine().trim().toUpperCase());
            System.out.print("Date (YYYY-MM-DD, blank for today): ");
            String d = in.nextLine().trim();
            LocalDate date = d.isEmpty() ? LocalDate.now() : LocalDate.parse(d);
            System.out.print("Note: ");
            String note = in.nextLine();

            System.out.println("Added: " + tracker.add(amount, category, date, note));
        } catch (InvalidExpenseException e) {
            System.out.println("Invalid expense: " + e.getMessage());
        } catch (IllegalArgumentException | java.time.format.DateTimeParseException e) {
            System.out.println("Bad input, expense not added.");
        }
    }
}

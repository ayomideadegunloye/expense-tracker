# Java Expense Tracker

A command-line expense tracker built in plain Java. Add expenses, view them,ay
and see totals by category and by month. Data is saved to a CSV file.

## Features
- Add, list and delete expenses
- Totals by category and by month (Java streams)
- CSV persistence between runs
- Input validation (no zero/negative amounts, bad dates or categories)
- Money handled with `BigDecimal`, dates with `LocalDate`
- JUnit 5 unit tests

## Run it
Requires Java 17+ and Maven.

```
mvn test
mvn compile exec:java -Dexec.mainClass=com.mide.tracker.Main
```

Or compile manually:

```
javac -d out src/main/java/com/mide/tracker/*.java
java -cp out com.mide.tracker.Main
```

## Project structure
- `Expense` – one expense, validates itself
- `Category` – enum of categories
- `ExpenseTracker` – business logic (add, delete, totals)
- `FileStorage` – save/load CSV
- `Main` – text menu

## Design decisions
- `BigDecimal` instead of `double` to avoid rounding errors with money
- Validation lives in the `Expense` constructor so an invalid expense can never exist
- Logic is separate from input/output so it can be unit tested

## Future ideas
- Budget limits with warnings per category
- Edit existing expenses
- Spring Boot REST API on top

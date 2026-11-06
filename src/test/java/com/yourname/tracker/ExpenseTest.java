package com.yourname.tracker;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ExpenseTest {
    @Test
    void rejectsZeroAmount() {
        assertThrows(InvalidExpenseException.class,
                () -> new Expense(1, BigDecimal.ZERO, Category.FOOD, LocalDate.now(), ""));
    }

    @Test
    void rejectsNegativeAmount() {
        assertThrows(InvalidExpenseException.class,
                () -> new Expense(1, new BigDecimal("-5"), Category.FOOD, LocalDate.now(), ""));
    }

    @Test
    void roundsToTwoDecimals() {
        Expense e = new Expense(1, new BigDecimal("2.555"), Category.FOOD, LocalDate.now(), "");
        assertEquals(new BigDecimal("2.56"), e.getAmount());
    }
}

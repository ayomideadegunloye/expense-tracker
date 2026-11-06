package com.yourname.tracker;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExpenseTrackerTest {
    private ExpenseTracker tracker;

    @BeforeEach
    void setUp() {
        tracker = new ExpenseTracker();
        tracker.add(new BigDecimal("10.00"), Category.FOOD, LocalDate.of(2026, 10, 1), "lunch");
        tracker.add(new BigDecimal("5.50"), Category.FOOD, LocalDate.of(2026, 10, 2), "coffee");
        tracker.add(new BigDecimal("20.00"), Category.TRANSPORT, LocalDate.of(2026, 11, 1), "train");
    }

    @Test
    void totalAddsEverything() {
        assertEquals(new BigDecimal("35.50"), tracker.total());
    }

    @Test
    void totalsByCategory() {
        assertEquals(new BigDecimal("15.50"), tracker.totalsByCategory().get(Category.FOOD));
        assertEquals(new BigDecimal("20.00"), tracker.totalsByCategory().get(Category.TRANSPORT));
    }

    @Test
    void totalsByMonth() {
        assertEquals(new BigDecimal("15.50"), tracker.totalsByMonth().get(YearMonth.of(2026, 10)));
    }

    @Test
    void deleteRemovesExpense() {
        assertTrue(tracker.delete(1));
        assertEquals(2, tracker.getAll().size());
        assertFalse(tracker.delete(99));
    }
}

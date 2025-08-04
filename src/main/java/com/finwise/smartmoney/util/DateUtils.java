package com.finwise.smartmoney.util;

import com.finwise.smartmoney.enums.Frequency;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

public class DateUtils {

    /**
     * Returns the start and end dates of a given month and year.
     * Index 0 = first day, Index 1 = last day
     */
    public static MonthRange getMonthRange(int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        return new MonthRange(ym.atDay(1), ym.atEndOfMonth());
    }

    /**
     * Returns true if the given month/year is in the future.
     */
    public static boolean isFutureMonth(int year, int month) {
        YearMonth requested = YearMonth.of(year, month);
        YearMonth current = YearMonth.now();
        return requested.isAfter(current);
    }

    /**
     * Calculates the next valid due date based on recurring day and frequency.
     * It adjusts for:
     * - months without the 31st
     * - weekends (moves back to Friday if Sat/Sun)
     */
    public static LocalDate getNextValidDueDate(LocalDate fromDate, int recurringDay, Frequency frequency) {
        LocalDate baseDate = switch (frequency) {
            case WEEKLY -> fromDate.plusWeeks(1);
            case MONTHLY -> fromDate.plusMonths(1);
            case YEARLY -> fromDate.plusYears(1);
        };

        // Clamp day to end-of-month
        int validDay = Math.min(recurringDay, baseDate.lengthOfMonth());
        LocalDate targetDate = baseDate.withDayOfMonth(validDay);

        // Adjust weekends: If Sat/Sun, go back to Friday
        if (targetDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            targetDate = targetDate.minusDays(1);
        } else if (targetDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            targetDate = targetDate.minusDays(2);
        }

        return targetDate;
    }
}

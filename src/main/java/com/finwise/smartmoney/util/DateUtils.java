package com.finwise.smartmoney.util;

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
}

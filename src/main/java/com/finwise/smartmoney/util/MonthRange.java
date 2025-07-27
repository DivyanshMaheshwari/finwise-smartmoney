package com.finwise.smartmoney.util;

import java.time.LocalDate;

public class MonthRange {
    private LocalDate start;
    private LocalDate end;

    public MonthRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }
}

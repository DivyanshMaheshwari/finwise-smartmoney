package com.finwise.smartmoney.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingRequestDTO {
    private BigDecimal amount;
    private String category;
    private LocalDate date;
    private Boolean isRecurring;
    private String note;
    private String frequency;      // WEEKLY, MONTHLY, YEARLY
    private LocalDate endDate;     // Optional
    private Integer recurringDay;  // Optional (defaults to transaction date)


    // Getters and Setters
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Boolean getIsRecurring() { return isRecurring; }
    public void setIsRecurring(Boolean isRecurring) { this.isRecurring = isRecurring; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getRecurringDay() {
        return recurringDay;
    }

    public void setRecurringDay(Integer recurringDay) {
        this.recurringDay = recurringDay;
    }
}

package com.finwise.smartmoney.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IncomeRequestDTO {
    private BigDecimal amount;
    private LocalDate date;
    private String category;
    private String source;
    private Boolean isRecurring;
    private String note;
    private String frequency;      // WEEKLY, MONTHLY, YEARLY
    private LocalDate endDate;     // Optional
    private Integer recurringDay;
    private String userId;

    // Getters and Setters


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getIsRecurring() {
        return isRecurring;
    }

    public void setIsRecurring(Boolean recurring) {
        isRecurring = recurring;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

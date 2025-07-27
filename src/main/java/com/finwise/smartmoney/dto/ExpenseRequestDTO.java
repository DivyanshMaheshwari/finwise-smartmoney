package com.finwise.smartmoney.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

    public class ExpenseRequestDTO {
        private BigDecimal amount;
        private LocalDate date;
        private String category;
        private String type;
        private String paymentMode;
        private Boolean isRecurring;
        private String note;
        private String frequency;      // WEEKLY, MONTHLY, YEARLY
        private LocalDate endDate;     // Optional
        private Integer recurringDay;  // Optional (defaults to transaction date)


        // Getters and setters

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
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
    }
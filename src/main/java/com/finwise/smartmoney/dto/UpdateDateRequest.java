package com.finwise.smartmoney.dto;

import java.time.LocalDate;

public class UpdateDateRequest {
    private LocalDate newDueDate;

    public LocalDate getNewDueDate() {
        return newDueDate;
    }

    public void setNewDueDate(LocalDate newDueDate) {
        this.newDueDate = newDueDate;
    }
}

package com.finwise.smartmoney.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingResponseDTO {
    private Long id;
    private BigDecimal amount;
    private String category;
    private LocalDate date;
    private Boolean isRecurring;
    private String note;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
}

package com.finwise.smartmoney.entity;

import com.finwise.smartmoney.enums.TransactionType;
import com.finwise.smartmoney.enums.Frequency;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "recurring_transaction")
public class RecurringTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private Long referenceId; // Points to Income/Saving/Expense table

    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    private LocalDate startDate;
    private LocalDate endDate;

    private Integer recurringDay; // Day of month to recur on

    private LocalDate nextDueDate;

    private Boolean active = true;
    private String userId;

    @Column(name = "is_date_adjustable")
    private Boolean isDateAdjustable = true;

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
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

    public LocalDate getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(LocalDate nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

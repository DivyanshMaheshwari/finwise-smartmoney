package com.finwise.smartmoney.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BudgetPlanResponseDTO {
    private Long incomeId;
    private BigDecimal salaryAmount;

    private LocalDate salaryDate;
    private BigDecimal calculatedNeeds;
    private BigDecimal calculatedWants;
    private BigDecimal calculatedInvestments;

    // Getters and Setters

    public Long getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Long incomeId) {
        this.incomeId = incomeId;
    }

    public LocalDate getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(LocalDate salaryDate) {
        this.salaryDate = salaryDate;
    }

    public BigDecimal getCalculatedNeeds() {
        return calculatedNeeds;
    }

    public void setCalculatedNeeds(BigDecimal calculatedNeeds) {
        this.calculatedNeeds = calculatedNeeds;
    }

    public BigDecimal getCalculatedWants() {
        return calculatedWants;
    }

    public void setCalculatedWants(BigDecimal calculatedWants) {
        this.calculatedWants = calculatedWants;
    }

    public BigDecimal getCalculatedInvestments() {
        return calculatedInvestments;
    }

    public void setCalculatedInvestments(BigDecimal calculatedInvestments) {
        this.calculatedInvestments = calculatedInvestments;
    }

    public BigDecimal getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(BigDecimal salaryAmount) {
        this.salaryAmount = salaryAmount;
    }
}

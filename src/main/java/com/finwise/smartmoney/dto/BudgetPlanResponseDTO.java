package com.finwise.smartmoney.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BudgetPlanResponseDTO {
    @JsonProperty("Income ID")
    private Long incomeId;
    @JsonProperty("Salary date")
    private LocalDate salaryDate;
    @JsonProperty("Needs")
    private BigDecimal calculatedNeeds;
    @JsonProperty("Wants")
    private BigDecimal calculatedWants;
    @JsonProperty("Investments")
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
}

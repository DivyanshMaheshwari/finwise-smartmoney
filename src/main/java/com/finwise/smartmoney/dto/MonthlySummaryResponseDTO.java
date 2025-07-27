package com.finwise.smartmoney.dto;

import java.math.BigDecimal;

public class MonthlySummaryResponseDTO {
    private String month;
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal netSavings;

    private BigDecimal budgetedNeeds;
    private BigDecimal budgetedWants;
    private BigDecimal budgetedInvestments;

    private BigDecimal actualNeeds;
    private BigDecimal actualWants;
    private BigDecimal actualInvestments;

    // Getters and setters...

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getNetSavings() {
        return netSavings;
    }

    public void setNetSavings(BigDecimal netSavings) {
        this.netSavings = netSavings;
    }

    public BigDecimal getBudgetedNeeds() {
        return budgetedNeeds;
    }

    public void setBudgetedNeeds(BigDecimal budgetedNeeds) {
        this.budgetedNeeds = budgetedNeeds;
    }

    public BigDecimal getBudgetedWants() {
        return budgetedWants;
    }

    public void setBudgetedWants(BigDecimal budgetedWants) {
        this.budgetedWants = budgetedWants;
    }

    public BigDecimal getBudgetedInvestments() {
        return budgetedInvestments;
    }

    public void setBudgetedInvestments(BigDecimal budgetedInvestments) {
        this.budgetedInvestments = budgetedInvestments;
    }

    public BigDecimal getActualNeeds() {
        return actualNeeds;
    }

    public void setActualNeeds(BigDecimal actualNeeds) {
        this.actualNeeds = actualNeeds;
    }

    public BigDecimal getActualWants() {
        return actualWants;
    }

    public void setActualWants(BigDecimal actualWants) {
        this.actualWants = actualWants;
    }

    public BigDecimal getActualInvestments() {
        return actualInvestments;
    }

    public void setActualInvestments(BigDecimal actualInvestments) {
        this.actualInvestments = actualInvestments;
    }
}

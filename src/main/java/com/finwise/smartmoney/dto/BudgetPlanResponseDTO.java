package com.finwise.smartmoney.dto;

import java.math.BigDecimal;

public class BudgetPlanResponseDTO {
    private Long salaryId;
    private BigDecimal needsPercent;
    private BigDecimal wantsPercent;
    private BigDecimal investPercent;

    private BigDecimal calculatedNeeds;
    private BigDecimal calculatedWants;
    private BigDecimal calculatedInvestments;

    // Getters and Setters


    public Long getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Long salaryId) {
        this.salaryId = salaryId;
    }

    public BigDecimal getNeedsPercent() {
        return needsPercent;
    }

    public void setNeedsPercent(BigDecimal needsPercent) {
        this.needsPercent = needsPercent;
    }

    public BigDecimal getWantsPercent() {
        return wantsPercent;
    }

    public void setWantsPercent(BigDecimal wantsPercent) {
        this.wantsPercent = wantsPercent;
    }

    public BigDecimal getInvestPercent() {
        return investPercent;
    }

    public void setInvestPercent(BigDecimal investPercent) {
        this.investPercent = investPercent;
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

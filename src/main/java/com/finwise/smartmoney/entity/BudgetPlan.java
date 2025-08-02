package com.finwise.smartmoney.entity;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.math.BigDecimal;

@Entity
@Table(name = "budget_plan")
public class BudgetPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @OneToOne
    @JoinColumn(name = "income_id")
    private Income income;
    @Column(name = "needs_percent")
    private BigDecimal needsPercent;
    @Column(name = "wants_percent")
    private BigDecimal wantsPercent;
    @Column(name = "invest_percent")
    private BigDecimal investPercent;
    @Column(name = "calculated_needs")
    private BigDecimal calculatedNeeds;
    @Column(name = "calculated_wants")
    private BigDecimal calculatedWants;
    @Column(name = "calculated_investments")
    private BigDecimal calculatedInvestments;
    private String userId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Income getIncome() {
        return income;
    }

    public void setIncome(Income income) {
        this.income = income;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

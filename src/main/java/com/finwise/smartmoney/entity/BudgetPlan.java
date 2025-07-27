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
    @JoinColumn(name = "salary_id")
    private Salary salary;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
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

package com.finwise.smartmoney.service;

import com.finwise.smartmoney.dto.BudgetPlanResponseDTO;
import com.finwise.smartmoney.entity.BudgetPlan;
import com.finwise.smartmoney.entity.Income;
import com.finwise.smartmoney.repository.BudgetPlanRepository;
import com.finwise.smartmoney.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class BudgetPlanService {

    @Autowired
    private BudgetPlanRepository budgetPlanRepository;
    @Autowired
    IncomeRepository incomeRepository;

    public String generateBudgetFromLatestSalary() {
        Income salaryIncome = incomeRepository
                .findTopByCategoryIgnoreCaseOrderByDateDesc("salary")
                .orElseThrow(() -> new RuntimeException("No salary income found"));

        // Check if a budget plan already exists for this salary
        Optional<BudgetPlan> existing = budgetPlanRepository.findByIncome(salaryIncome);
        if (existing.isPresent()) {
            throw new RuntimeException("Budget plan already exists for this salary income");
        }
        // Use standard 50/30/20 rule
        // 50% for needs, 30% for wants, 20% for investments
        BigDecimal total = salaryIncome.getAmount();
        BigDecimal needsPercent = BigDecimal.valueOf(50);
        BigDecimal wantsPercent = BigDecimal.valueOf(30);
        BigDecimal investPercent = BigDecimal.valueOf(20);

        BudgetPlan budgetPlan = new BudgetPlan();
        budgetPlan.setIncome(salaryIncome);
        budgetPlan.setNeedsPercent(needsPercent);
        budgetPlan.setWantsPercent(wantsPercent);
        budgetPlan.setInvestPercent(investPercent);
        budgetPlan.setCalculatedNeeds(total.multiply(needsPercent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        budgetPlan.setCalculatedWants(total.multiply(wantsPercent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        budgetPlan.setCalculatedInvestments(total.multiply(investPercent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));

        BudgetPlan savedBudgetPlan = budgetPlanRepository.save(budgetPlan);

        return "Budget plan generated successfully for income ID: " + savedBudgetPlan.getIncome().getId();
    }

    public BudgetPlanResponseDTO getBudgetByLatestSalary() {
        Income salaryIncome = incomeRepository
                .findTopByCategoryIgnoreCaseOrderByDateDesc("salary")
                .orElseThrow(() -> new RuntimeException("No salary income found"));

        BudgetPlan budgetPlan = budgetPlanRepository.findByIncome(salaryIncome)
                .orElseThrow(() -> new RuntimeException("No budget found for the latest salary income"));

        return mapToDTO(budgetPlan);
    }

    private BudgetPlanResponseDTO mapToDTO(BudgetPlan plan) {
        BudgetPlanResponseDTO dto = new BudgetPlanResponseDTO();
        dto.setIncomeId(plan.getIncome().getId());
        dto.setSalaryDate(plan.getIncome().getDate());
        dto.setCalculatedNeeds(plan.getCalculatedNeeds());
        dto.setCalculatedWants(plan.getCalculatedWants());
        dto.setCalculatedInvestments(plan.getCalculatedInvestments());
        return dto;
    }
}

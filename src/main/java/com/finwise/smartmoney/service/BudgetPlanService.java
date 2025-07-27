package com.finwise.smartmoney.service;

import com.finwise.smartmoney.dto.BudgetPlanResponseDTO;
import com.finwise.smartmoney.entity.BudgetPlan;
import com.finwise.smartmoney.entity.Salary;
import com.finwise.smartmoney.repository.BudgetPlanRepository;
import com.finwise.smartmoney.repository.SalaryRepository;
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
    SalaryRepository salaryRepository;

    public String generateBudgetForSalary(Long salaryId) {
        Salary salary = salaryRepository.findById(salaryId)
                .orElseThrow(() -> new RuntimeException("Salary not found with id: " + salaryId));

        // ✅ Check if a budget plan already exists for this salary
        Optional<BudgetPlan> existing = budgetPlanRepository.findBySalary(salary);
        if (existing.isPresent()) {
            throw new RuntimeException("Budget plan already exists for salary ID: " + salaryId);
        }
        // Use standard 50/30/20 rule
        // 50% for needs, 30% for wants, 20% for investments
        BigDecimal total = salary.getAmount();
        BigDecimal needsPercent = BigDecimal.valueOf(50);
        BigDecimal wantsPercent = BigDecimal.valueOf(30);
        BigDecimal investPercent = BigDecimal.valueOf(20);

        BudgetPlan budgetPlan = new BudgetPlan();
        budgetPlan.setSalary(salary);
        budgetPlan.setNeedsPercent(needsPercent);
        budgetPlan.setWantsPercent(wantsPercent);
        budgetPlan.setInvestPercent(investPercent);
        budgetPlan.setCalculatedNeeds(total.multiply(needsPercent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        budgetPlan.setCalculatedWants(total.multiply(wantsPercent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        budgetPlan.setCalculatedInvestments(total.multiply(investPercent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));

        BudgetPlan savedBudgetPlan = budgetPlanRepository.save(budgetPlan);

        return "Budget plan generated successfully for salary ID: " + savedBudgetPlan.getSalary().getId();
    }

    public BudgetPlanResponseDTO getBudgetBySalaryId(Salary salaryId) {
        Salary salary = salaryRepository.findById(salaryId.getId())
                .orElseThrow(() -> new RuntimeException("Salary not found with id: " + salaryId.getId()));

        BudgetPlan budgetPlan = budgetPlanRepository.findBySalary(salary)
                .orElseThrow(() -> new RuntimeException("Budget plan not found for salary ID: " + salaryId.getId()));

        return mapToDTO(budgetPlan);
    }

    private BudgetPlanResponseDTO mapToDTO(BudgetPlan plan) {
        BudgetPlanResponseDTO dto = new BudgetPlanResponseDTO();
        dto.setSalaryId(plan.getSalary().getId());
        dto.setNeedsPercent(plan.getNeedsPercent());
        dto.setWantsPercent(plan.getWantsPercent());
        dto.setInvestPercent(plan.getInvestPercent());
        dto.setCalculatedNeeds(plan.getCalculatedNeeds());
        dto.setCalculatedWants(plan.getCalculatedWants());
        dto.setCalculatedInvestments(plan.getCalculatedInvestments());
        return dto;
    }
}

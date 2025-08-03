package com.finwise.smartmoney.service;

import com.finwise.smartmoney.dto.BudgetPlanResponseDTO;
import com.finwise.smartmoney.entity.BudgetPlan;
import com.finwise.smartmoney.entity.Income;
import com.finwise.smartmoney.repository.BudgetPlanRepository;
import com.finwise.smartmoney.repository.IncomeRepository;
import com.finwise.smartmoney.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetPlanService {

    @Autowired
    private BudgetPlanRepository budgetPlanRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    public String generateBudgetFromLatestSalary() {
        String userId = extractUserIdFromToken();

        Income salaryIncome = incomeRepository
                .findTopByUserIdAndCategoryIgnoreCaseOrderByDateDesc(userId, "salary")
                .orElseThrow(() -> new RuntimeException("No salary income found for user"));

        Optional<BudgetPlan> existing = budgetPlanRepository.findByIncomeAndUserId(salaryIncome, userId);
        if (existing.isPresent()) {
            return "Budget already exists for this salary.";
        }

        BigDecimal total = salaryIncome.getAmount();
        BigDecimal needsPercent = BigDecimal.valueOf(50);
        BigDecimal wantsPercent = BigDecimal.valueOf(30);
        BigDecimal investPercent = BigDecimal.valueOf(20);

        BudgetPlan budgetPlan = new BudgetPlan();
        budgetPlan.setIncome(salaryIncome);
        budgetPlan.setUserId(userId);
        budgetPlan.setNeedsPercent(needsPercent);
        budgetPlan.setWantsPercent(wantsPercent);
        budgetPlan.setInvestPercent(investPercent);
        budgetPlan.setCalculatedNeeds(total.multiply(needsPercent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        budgetPlan.setCalculatedWants(total.multiply(wantsPercent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        budgetPlan.setCalculatedInvestments(total.multiply(investPercent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));

        BudgetPlan savedBudgetPlan = budgetPlanRepository.save(budgetPlan);

        return "Budget plan generated successfully for income ID: " + savedBudgetPlan.getIncome().getId();
    }
    public List<BudgetPlanResponseDTO> getAllBudgetPlans() {
        String userId = extractUserIdFromToken();

        List<BudgetPlan> plans = budgetPlanRepository.findAllByUserIdOrderByIncome_DateDesc(userId);

        return plans.stream()
                .map(this::mapToDTO)
                .toList();
    }
    public String deleteBudgetPlan(Long budgetPlanId) {
        String userId = extractUserIdFromToken();

        BudgetPlan plan = budgetPlanRepository.findById(budgetPlanId)
                .orElseThrow(() -> new RuntimeException("Budget plan not found"));

        if (!plan.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized to delete this budget plan");
        }

        budgetPlanRepository.delete(plan);
        return "Budget plan deleted successfully";
    }


    private BudgetPlanResponseDTO mapToDTO(BudgetPlan plan) {
        BudgetPlanResponseDTO dto = new BudgetPlanResponseDTO();
        dto.setIncomeId(plan.getIncome().getId());
        dto.setSalaryDate(plan.getIncome().getDate());
        dto.setCalculatedNeeds(plan.getCalculatedNeeds());
        dto.setCalculatedWants(plan.getCalculatedWants());
        dto.setCalculatedInvestments(plan.getCalculatedInvestments());
        dto.setSalaryAmount(plan.getIncome().getAmount());
        return dto;
    }

    private String extractUserIdFromToken() {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return jwtUtil.extractUserId(token.substring(7));
        }
        throw new RuntimeException("Missing or invalid Authorization header.");
    }
}

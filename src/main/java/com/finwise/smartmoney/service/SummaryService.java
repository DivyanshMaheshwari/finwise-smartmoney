package com.finwise.smartmoney.service;

import com.finwise.smartmoney.dto.MonthlySummaryResponseDTO;
import com.finwise.smartmoney.entity.BudgetPlan;
import com.finwise.smartmoney.entity.Expense;
import com.finwise.smartmoney.entity.Income;
import com.finwise.smartmoney.entity.Saving;
import com.finwise.smartmoney.repository.BudgetPlanRepository;
import com.finwise.smartmoney.repository.ExpenseRepository;
import com.finwise.smartmoney.repository.IncomeRepository;
import com.finwise.smartmoney.repository.SavingRepository;
import com.finwise.smartmoney.util.DateUtils;
import com.finwise.smartmoney.util.JwtUtil;
import com.finwise.smartmoney.util.MonthRange;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class SummaryService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BudgetPlanRepository budgetPlanRepository;

    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    public MonthlySummaryResponseDTO getSummary(int month, int year) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserId(token);

        MonthRange range = DateUtils.getMonthRange(year, month);
        LocalDate start = range.getStart();
        LocalDate end = range.getEnd();

        List<Income> incomes = incomeRepository.findByUserIdAndDateBetween(userId, start, end);
        List<Expense> expenses = expenseRepository.findByUserIdAndDateBetween(userId, start, end);
        List<Saving> savings = savingRepository.findByUserIdAndDateBetween(userId, start, end);

        BigDecimal totalIncome = BigDecimal.ZERO;
        for (Income income : incomes) {
            if (income.getAmount() != null) {
                totalIncome = totalIncome.add(income.getAmount());
            }
        }

        BigDecimal totalExpenses = BigDecimal.ZERO;
        for (Expense expense : expenses) {
            if (expense.getAmount() != null) {
                totalExpenses = totalExpenses.add(expense.getAmount());
            }
        }

        BigDecimal netSavings = totalIncome.subtract(totalExpenses);

        BigDecimal actualNeeds = BigDecimal.ZERO;
        BigDecimal actualWants = BigDecimal.ZERO;
        BigDecimal actualInvestments = BigDecimal.ZERO;

        for (Expense expense : expenses) {
            if ("needs".equalsIgnoreCase(expense.getType()) && expense.getAmount() != null) {
                actualNeeds = actualNeeds.add(expense.getAmount());
            } else if ("wants".equalsIgnoreCase(expense.getType()) && expense.getAmount() != null) {
                actualWants = actualWants.add(expense.getAmount());
            }
        }

        for (Saving saving : savings) {
            actualInvestments = actualInvestments.add(saving.getAmount());
        }

        MonthlySummaryResponseDTO dto = new MonthlySummaryResponseDTO();
        dto.setMonth(year + "-" + (month < 10 ? "0" + month : String.valueOf(month)));
        dto.setTotalIncome(totalIncome);
        dto.setTotalExpenses(totalExpenses);
        dto.setNetSavings(netSavings);
        dto.setActualNeeds(actualNeeds);
        dto.setActualWants(actualWants);
        dto.setActualInvestments(actualInvestments);

        Income salaryIncome = incomes.stream()
                .filter(in -> in.getCategory() != null && in.getCategory().equalsIgnoreCase("salary"))
                .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                .findFirst()
                .orElse(null);

        if (salaryIncome != null) {
            BudgetPlan budget = budgetPlanRepository.findByIncomeAndUserId(salaryIncome,userId).orElse(null);
            if (budget != null) {
                dto.setBudgetedNeeds(budget.getCalculatedNeeds());
                dto.setBudgetedWants(budget.getCalculatedWants());
                dto.setBudgetedInvestments(budget.getCalculatedInvestments());
            }
        }

        return dto;
    }
}

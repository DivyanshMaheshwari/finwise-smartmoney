package com.finwise.smartmoney.service;

import com.finwise.smartmoney.dto.ExpenseRequestDTO;
import com.finwise.smartmoney.dto.ExpenseResponseDTO;
import com.finwise.smartmoney.entity.Expense;
import com.finwise.smartmoney.entity.RecurringTransaction;
import com.finwise.smartmoney.enums.Frequency;
import com.finwise.smartmoney.enums.TransactionType;
import com.finwise.smartmoney.repository.ExpenseRepository;
import com.finwise.smartmoney.repository.RecurringTransactionRepository;
import com.finwise.smartmoney.util.DateUtils;
import com.finwise.smartmoney.util.JwtUtil;
import com.finwise.smartmoney.util.MonthRange;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private RecurringTransactionRepository recurringTransactionRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    public String saveExpense(ExpenseRequestDTO expenseRequestDTO) {
        String userId = extractUserIdFromToken();

        Expense expense = new Expense();
        expense.setUserId(userId);
        expense.setAmount(expenseRequestDTO.getAmount());
        expense.setCategory(expenseRequestDTO.getCategory());
        expense.setType(expenseRequestDTO.getType());
        expense.setNote(expenseRequestDTO.getNote());
        expense.setPaymentMode(expenseRequestDTO.getPaymentMode());
        expense.setDate(expenseRequestDTO.getDate() != null ? expenseRequestDTO.getDate() : LocalDate.now());
        expense.setIsRecurring(expenseRequestDTO.getIsRecurring());

        Expense savedExpense = expenseRepository.save(expense);

        if (Boolean.TRUE.equals(expenseRequestDTO.getIsRecurring())) {
            RecurringTransaction tx = new RecurringTransaction();
            tx.setUserId(userId);
            tx.setType(TransactionType.EXPENSE);
            tx.setReferenceId(savedExpense.getId());
            tx.setStartDate(savedExpense.getDate());

            Frequency frequency = Frequency.MONTHLY;
            if (expenseRequestDTO.getFrequency() != null) {
                try {
                    frequency = Frequency.valueOf(expenseRequestDTO.getFrequency().toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid frequency. Allowed: WEEKLY, MONTHLY, YEARLY");
                }
            }

            tx.setFrequency(frequency);
            tx.setEndDate(expenseRequestDTO.getEndDate());
            Integer recurringDay = expenseRequestDTO.getRecurringDay() != null
                    ? expenseRequestDTO.getRecurringDay()
                    : savedExpense.getDate().getDayOfMonth();
            tx.setRecurringDay(recurringDay);
            tx.setNextDueDate(getNextDate(savedExpense.getDate(), frequency));
            tx.setActive(true);

            recurringTransactionRepository.save(tx);
        }

        return "Expense saved successfully";
    }

    public List<ExpenseResponseDTO> getExpensesByMonth(int month, int year) {
        String userId = extractUserIdFromToken();

        MonthRange range = DateUtils.getMonthRange(year, month);
        List<Expense> expenses = expenseRepository.findByUserIdAndDateBetween(userId, range.getStart(), range.getEnd());

        return expenses.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ExpenseResponseDTO mapToDTO(Expense expense) {
        ExpenseResponseDTO dto = new ExpenseResponseDTO();
        dto.setId(expense.getId());
        dto.setAmount(expense.getAmount());
        dto.setCategory(expense.getCategory());
        dto.setDate(expense.getDate());
        dto.setType(expense.getType());
        dto.setPaymentMode(expense.getPaymentMode());
        dto.setNote(expense.getNote());
        dto.setIsRecurring(expense.getIsRecurring());
        return dto;
    }

    private LocalDate getNextDate(LocalDate from, Frequency freq) {
        return switch (freq) {
            case WEEKLY -> from.plusWeeks(1);
            case MONTHLY -> from.plusMonths(1);
            case YEARLY -> from.plusYears(1);
        };
    }

    private String extractUserIdFromToken() {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return jwtUtil.extractUserId(token.substring(7));
        }
        throw new RuntimeException("Missing or invalid Authorization header.");
    }
    @Transactional
    public String deleteIncome(Long id) {
        expenseRepository.deleteById(id);
        recurringTransactionRepository.deleteByReferenceIdAndType(id, TransactionType.EXPENSE);
        return "Expense and associated recurring transaction deleted successfully";
    }
}

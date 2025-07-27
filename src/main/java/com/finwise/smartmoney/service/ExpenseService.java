package com.finwise.smartmoney.service;

import com.finwise.smartmoney.dto.ExpenseRequestDTO;
import com.finwise.smartmoney.dto.ExpenseResponseDTO;
import com.finwise.smartmoney.dto.IncomeResponseDTO;
import com.finwise.smartmoney.entity.Expense;
import com.finwise.smartmoney.repository.ExpenseRepository;
import com.finwise.smartmoney.util.DateUtils;
import com.finwise.smartmoney.util.MonthRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public String saveExpense(ExpenseRequestDTO dto) {
        Expense expense = new Expense();
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());
        expense.setCategory(dto.getCategory());
        expense.setType(dto.getType());
        expense.setPaymentMode(dto.getPaymentMode());
        expense.setIsRecurring(dto.getIsRecurring());
        expense.setNote(dto.getNote());

        expenseRepository.save(expense);
        return "Expense saved successfully";
    }

    public List<ExpenseResponseDTO> getExpensesByMonth(int month, int year) {

        if (DateUtils.isFutureMonth(year, month)) {
            throw new IllegalArgumentException("Cannot fetch expenses for a future month.");
        }

        MonthRange range = DateUtils.getMonthRange(year, month);
        LocalDate start = range.getStart();
        LocalDate end = range.getEnd();

        List<Expense> expenses = expenseRepository.findByDateBetween(range.getStart(), range.getEnd());

        if (expenses.isEmpty()) {
            throw new RuntimeException("No expenses found for the given month.");
        }

        return expenses.stream()
                .map(this::mapToDTO)
                .toList();
    }

    private ExpenseResponseDTO mapToDTO(Expense e) {
        ExpenseResponseDTO dto = new ExpenseResponseDTO();
        dto.setId(e.getId());
        dto.setAmount(e.getAmount());
        dto.setDate(e.getDate());
        dto.setCategory(e.getCategory());
        dto.setType(e.getType());
        dto.setPaymentMode(e.getPaymentMode());
        dto.setIsRecurring(e.getIsRecurring());
        dto.setNote(e.getNote());
        return dto;
    }
}

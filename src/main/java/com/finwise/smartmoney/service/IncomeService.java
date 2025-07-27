package com.finwise.smartmoney.service;

import com.finwise.smartmoney.dto.IncomeRequestDTO;
import com.finwise.smartmoney.dto.IncomeResponseDTO;
import com.finwise.smartmoney.entity.Expense;
import com.finwise.smartmoney.entity.Income;
import com.finwise.smartmoney.repository.IncomeRepository;
import com.finwise.smartmoney.util.DateUtils;
import com.finwise.smartmoney.util.MonthRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public String saveIncome(IncomeRequestDTO incomeRequestDto) {
        // Convert DTO to entity
        Income income = new Income();
        income.setAmount(incomeRequestDto.getAmount());
        income.setDate(incomeRequestDto.getDate());
        income.setCategory(incomeRequestDto.getCategory());
        income.setSource(incomeRequestDto.getSource());
        income.setRecurring(incomeRequestDto.getIsRecurring());
        income.setNote(incomeRequestDto.getNote());

        // Save the income entity
        incomeRepository.save(income);
        return "Income saved successfully";
    }

    public List<IncomeResponseDTO> getIncomesByMonth(int month, int year) {

        if (DateUtils.isFutureMonth(year, month)) {
            throw new IllegalArgumentException("Cannot fetch expenses for a future month.");
        }
        MonthRange range = DateUtils.getMonthRange(year, month);
        List<Income> income = incomeRepository.findByDateBetween(range.getStart(), range.getEnd());

        if (income.isEmpty()) {
            throw new RuntimeException("No expenses found for the given month.");
        }
        return income
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private IncomeResponseDTO mapToDTO(Income income) {
        IncomeResponseDTO dto = new IncomeResponseDTO();
        dto.setId(income.getId());
        dto.setAmount(income.getAmount());
        dto.setDate(income.getDate());
        dto.setCategory(income.getCategory());
        dto.setSource(income.getSource());
        dto.setRecurring(income.getRecurring());
        dto.setNote(income.getNote());
        return dto;
    }
}

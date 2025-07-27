package com.finwise.smartmoney.service;

import com.finwise.smartmoney.dto.IncomeRequestDTO;
import com.finwise.smartmoney.dto.IncomeResponseDTO;
import com.finwise.smartmoney.entity.Income;
import com.finwise.smartmoney.entity.RecurringTransaction;
import com.finwise.smartmoney.enums.Frequency;
import com.finwise.smartmoney.enums.TransactionType;
import com.finwise.smartmoney.repository.IncomeRepository;
import com.finwise.smartmoney.repository.RecurringTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private RecurringTransactionRepository recurringTransactionRepository;

    public String saveIncome(IncomeRequestDTO incomeRequestDTO) {
        Income income = new Income();
        income.setAmount(incomeRequestDTO.getAmount());
        income.setDate(incomeRequestDTO.getDate() != null ? incomeRequestDTO.getDate() : LocalDate.now());
        income.setCategory(incomeRequestDTO.getCategory());
        income.setSource(incomeRequestDTO.getSource());
        income.setNote(incomeRequestDTO.getNote());
        income.setIsRecurring(incomeRequestDTO.getIsRecurring());

        Income savedIncome = incomeRepository.save(income);

        // 💡 Handle recurring logic
        if (incomeRequestDTO.getIsRecurring() != null && incomeRequestDTO.getIsRecurring()) {
            RecurringTransaction tx = new RecurringTransaction();
            tx.setType(TransactionType.INCOME);
            tx.setReferenceId(savedIncome.getId());
            tx.setStartDate(savedIncome.getDate());

            // Set frequency
            Frequency frequency = Frequency.MONTHLY; // Default
            if (incomeRequestDTO.getFrequency() != null) {
                try {
                    frequency = Frequency.valueOf(incomeRequestDTO.getFrequency().toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid frequency. Allowed: WEEKLY, MONTHLY, YEARLY");
                }
            }
            tx.setFrequency(frequency);

            // Set end date if given
            tx.setEndDate(incomeRequestDTO.getEndDate());

            // Set recurring day (defaults to transaction day)
            Integer recurringDay = incomeRequestDTO.getRecurringDay() != null
                    ? incomeRequestDTO.getRecurringDay()
                    : savedIncome.getDate().getDayOfMonth();
            tx.setRecurringDay(recurringDay);

            tx.setNextDueDate(getNextDate(savedIncome.getDate(), frequency));
            tx.setActive(true);

            recurringTransactionRepository.save(tx);
        }

        return "Income saved successfully";
    }

    public List<IncomeResponseDTO> getIncomesByMonth(int month, int year) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Income> incomes = incomeRepository.findByDateBetween(start, end);

        return incomes.stream()
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
        dto.setNote(income.getNote());
        dto.setIsRecurring(income.getIsRecurring());
        return dto;
    }

    private LocalDate getNextDate(LocalDate from, Frequency freq) {
        return switch (freq) {
            case WEEKLY -> from.plusWeeks(1);
            case MONTHLY -> from.plusMonths(1);
            case YEARLY -> from.plusYears(1);
        };
    }
}

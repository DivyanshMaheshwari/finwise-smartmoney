package com.finwise.smartmoney.service;

import com.finwise.smartmoney.dto.IncomeRequestDTO;
import com.finwise.smartmoney.dto.IncomeResponseDTO;
import com.finwise.smartmoney.entity.Income;
import com.finwise.smartmoney.entity.RecurringTransaction;
import com.finwise.smartmoney.enums.Frequency;
import com.finwise.smartmoney.enums.TransactionType;
import com.finwise.smartmoney.repository.IncomeRepository;
import com.finwise.smartmoney.repository.RecurringTransactionRepository;
import com.finwise.smartmoney.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private RecurringTransactionRepository recurringTransactionRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    public String saveIncome(IncomeRequestDTO incomeRequestDTO) {
        String userId = extractUserIdFromToken();

        Income income = new Income();
        income.setUserId(userId);
        income.setAmount(incomeRequestDTO.getAmount());
        income.setDate(incomeRequestDTO.getDate() != null ? incomeRequestDTO.getDate() : LocalDate.now());
        income.setCategory(incomeRequestDTO.getCategory());
        income.setSource(incomeRequestDTO.getSource());
        income.setNote(incomeRequestDTO.getNote());
        income.setIsRecurring(incomeRequestDTO.getIsRecurring());

        Income savedIncome = incomeRepository.save(income);

        if (Boolean.TRUE.equals(incomeRequestDTO.getIsRecurring())) {
            RecurringTransaction tx = new RecurringTransaction();
            tx.setType(TransactionType.INCOME);
            tx.setReferenceId(savedIncome.getId());
            tx.setStartDate(savedIncome.getDate());

            Frequency frequency = Frequency.MONTHLY; // Default
            if (incomeRequestDTO.getFrequency() != null) {
                try {
                    frequency = Frequency.valueOf(incomeRequestDTO.getFrequency().toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid frequency. Allowed: WEEKLY, MONTHLY, YEARLY");
                }
            }
            tx.setFrequency(frequency);
            tx.setEndDate(incomeRequestDTO.getEndDate());

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
        String userId = extractUserIdFromToken();

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Income> incomes = incomeRepository.findByUserIdAndDateBetween(userId, start, end);

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
        dto.setUserId(income.getUserId());
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
}

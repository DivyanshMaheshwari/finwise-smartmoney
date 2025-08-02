package com.finwise.smartmoney.service;

import com.finwise.smartmoney.dto.SavingRequestDTO;
import com.finwise.smartmoney.dto.SavingResponseDTO;
import com.finwise.smartmoney.entity.RecurringTransaction;
import com.finwise.smartmoney.entity.Saving;
import com.finwise.smartmoney.enums.Frequency;
import com.finwise.smartmoney.enums.TransactionType;
import com.finwise.smartmoney.repository.RecurringTransactionRepository;
import com.finwise.smartmoney.repository.SavingRepository;
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
public class SavingService {

    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private RecurringTransactionRepository recurringTransactionRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    public String saveSaving(SavingRequestDTO savingRequestDTO) {
        String userId = extractUserIdFromToken();

        Saving saving = new Saving();
        saving.setUserId(userId);
        saving.setAmount(savingRequestDTO.getAmount());
        saving.setCategory(savingRequestDTO.getCategory());
        saving.setNote(savingRequestDTO.getNote());
        saving.setDate(savingRequestDTO.getDate() != null ? savingRequestDTO.getDate() : LocalDate.now());
        saving.setIsRecurring(savingRequestDTO.getIsRecurring());

        Saving savedSaving = savingRepository.save(saving);

        if (savingRequestDTO.getIsRecurring() != null && savingRequestDTO.getIsRecurring()) {
            RecurringTransaction tx = new RecurringTransaction();
            tx.setType(TransactionType.SAVING);
            tx.setReferenceId(savedSaving.getId());
            tx.setStartDate(savedSaving.getDate());

            Frequency frequency = Frequency.MONTHLY;
            if (savingRequestDTO.getFrequency() != null) {
                try {
                    frequency = Frequency.valueOf(savingRequestDTO.getFrequency().toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid frequency. Allowed: WEEKLY, MONTHLY, YEARLY");
                }
            }

            tx.setFrequency(frequency);
            tx.setEndDate(savingRequestDTO.getEndDate());
            Integer recurringDay = savingRequestDTO.getRecurringDay() != null
                    ? savingRequestDTO.getRecurringDay()
                    : savedSaving.getDate().getDayOfMonth();
            tx.setRecurringDay(recurringDay);
            tx.setNextDueDate(getNextDate(savedSaving.getDate(), frequency));
            tx.setActive(true);
            tx.setUserId(userId);
            recurringTransactionRepository.save(tx);
        }

        return "Saving saved successfully";
    }

    public List<SavingResponseDTO> getSavingsByMonth(int month, int year) {
        String userId = extractUserIdFromToken();

        MonthRange range = DateUtils.getMonthRange(year, month);
        List<Saving> savings = savingRepository.findByUserIdAndDateBetween(userId, range.getStart(), range.getEnd());

        return savings.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private SavingResponseDTO mapToDTO(Saving saving) {
        SavingResponseDTO dto = new SavingResponseDTO();
        dto.setId(saving.getId());
        dto.setAmount(saving.getAmount());
        dto.setCategory(saving.getCategory());
        dto.setDate(saving.getDate());
        dto.setNote(saving.getNote());
        dto.setIsRecurring(saving.getIsRecurring());
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
        savingRepository.deleteById(id);
        recurringTransactionRepository.deleteByReferenceIdAndType(id, TransactionType.SAVING);
        return "Saving and associated recurring transaction deleted successfully";
    }
}

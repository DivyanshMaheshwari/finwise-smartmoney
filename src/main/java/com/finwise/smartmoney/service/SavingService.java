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
import com.finwise.smartmoney.util.MonthRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavingService {

    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private RecurringTransactionRepository recurringTransactionRepository;

    public String saveSaving(SavingRequestDTO savingRequestDTO) {
        Saving saving = new Saving();
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
            recurringTransactionRepository.save(tx);
        }

        return "Saving saved successfully";
    }

    public List<SavingResponseDTO> getSavingsByMonth(int month, int year) {
        MonthRange range = DateUtils.getMonthRange(year, month);
        List<Saving> savings = savingRepository.findByDateBetween(range.getStart(), range.getEnd());
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
}

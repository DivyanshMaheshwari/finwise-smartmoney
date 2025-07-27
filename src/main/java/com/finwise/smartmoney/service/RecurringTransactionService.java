package com.finwise.smartmoney.service;

import com.finwise.smartmoney.entity.*;
import com.finwise.smartmoney.enums.Frequency;
import com.finwise.smartmoney.repository.*;
import com.finwise.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class RecurringTransactionService {

    private static final Logger logger = Logger.getLogger(RecurringTransactionService.class.getName());

    @Autowired
    private RecurringTransactionRepository recurringRepo;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    // Cron job runs daily at 2 AM
//    @Scheduled(cron = "0 0 2 * * *")
    @Scheduled(cron = "0/30 * * * * *") // For testing, runs every 30 seconds

    public void processRecurringTransactions() {
        logger.info("Scheduled Job Started: Processing Recurring Transactions");

        List<RecurringTransaction> transactions = recurringRepo
                .findByNextDueDateLessThanEqualAndActiveIsTrue(LocalDate.now());

        logger.info("Found " + transactions.size() + " due recurring transactions");

        for (RecurringTransaction tx : transactions) {
            if (tx.getNextDueDate().isAfter(LocalDate.now())) {
                logger.info("Skipping transaction ID " + tx.getId() + " — Not due yet");
                continue;
            }

            logger.info("Processing transaction ID " + tx.getId() + " of type " + tx.getType());

            switch (tx.getType()) {
                case INCOME -> {
                    Optional<Income> incomeOpt = incomeRepository.findById(tx.getReferenceId());
                    if (incomeOpt.isPresent()) {
                        Income original = incomeOpt.get();
                        Income copy = new Income();
                        copy.setAmount(original.getAmount());
                        copy.setCategory(original.getCategory());
                        copy.setDate(LocalDate.now());
                        copy.setNote(original.getNote());
                        copy.setSource(original.getSource());
                        copy.setIsRecurring(true);
                        incomeRepository.save(copy);
                        logger.info("Created new INCOME entry for transaction ID " + tx.getId());
                    } else {
                        logger.warning("Original INCOME not found for transaction ID " + tx.getId());
                    }
                }

                case SAVING -> {
                    Optional<Saving> savingOpt = savingRepository.findById(tx.getReferenceId());
                    if (savingOpt.isPresent()) {
                        Saving original = savingOpt.get();
                        Saving copy = new Saving();
                        copy.setAmount(original.getAmount());
                        copy.setCategory(original.getCategory());
                        copy.setDate(LocalDate.now());
                        copy.setNote(original.getNote());
                        copy.setIsRecurring(true);
                        savingRepository.save(copy);
                        logger.info("Created new SAVING entry for transaction ID " + tx.getId());
                    } else {
                        logger.warning("Original SAVING not found for transaction ID " + tx.getId());
                    }
                }

                case EXPENSE -> {
                    Optional<Expense> expenseOpt = expenseRepository.findById(tx.getReferenceId());
                    if (expenseOpt.isPresent()) {
                        Expense original = expenseOpt.get();
                        Expense copy = new Expense();
                        copy.setAmount(original.getAmount());
                        copy.setCategory(original.getCategory());
                        copy.setDate(LocalDate.now());
                        copy.setNote(original.getNote());
                        copy.setPaymentMode(original.getPaymentMode());
                        copy.setType(original.getType());
                        copy.setIsRecurring(true);
                        expenseRepository.save(copy);
                        logger.info("Created new EXPENSE entry for transaction ID " + tx.getId());
                    } else {
                        logger.warning("Original EXPENSE not found for transaction ID " + tx.getId());
                    }
                }
            }

            if (tx.getEndDate() != null && !tx.getEndDate().isAfter(LocalDate.now())) {
                tx.setActive(false);
                logger.info("Deactivated recurring transaction ID " + tx.getId() + " — Reached end date");
            } else {
                LocalDate next = getNextDate(tx.getNextDueDate(), tx.getFrequency());
                tx.setNextDueDate(next);
                logger.info("Updated nextDueDate for transaction ID " + tx.getId() + " to " + next);
            }
        }

        recurringRepo.saveAll(transactions);
        logger.info("Scheduled Job Completed: All due transactions processed");
    }

    private LocalDate getNextDate(LocalDate current, Frequency freq) {
        return switch (freq) {
            case WEEKLY -> current.plusWeeks(1);
            case MONTHLY -> current.plusMonths(1);
            case YEARLY -> current.plusYears(1);
        };
    }

    public void updateNextDueDate(Long id, LocalDate newDate) {
        RecurringTransaction rt = recurringRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RecurringTransaction not found"));
        rt.setNextDueDate(newDate);
        recurringRepo.save(rt);
        logger.info("Updated nextDueDate manually for transaction ID " + id + " to " + newDate);
    }

    public void disableRecurring(Long id) {
        RecurringTransaction rt = recurringRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RecurringTransaction not found"));

        rt.setActive(false);

        recurringRepo.save(rt);
    }

    public void enableRecurring(Long id) {
        RecurringTransaction rt = recurringRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RecurringTransaction not found"));

        rt.setActive(true);

        recurringRepo.save(rt);
    }

    public void deleteRecurringTransaction(Long id) {
        RecurringTransaction rt = recurringRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RecurringTransaction not found"));

        recurringRepo.delete(rt);
    }


}

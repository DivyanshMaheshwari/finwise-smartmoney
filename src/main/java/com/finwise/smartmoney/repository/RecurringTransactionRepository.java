package com.finwise.smartmoney.repository;

import com.finwise.smartmoney.entity.RecurringTransaction;
import com.finwise.smartmoney.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, Long> {

    List<RecurringTransaction> findByNextDueDateLessThanEqualAndActiveIsTrue(LocalDate today);
    RecurringTransaction findByReferenceIdAndType(Long referenceId, TransactionType type);
    void deleteByReferenceIdAndType(Long referenceId, TransactionType type);

    List<RecurringTransaction> findByUserIdAndNextDueDateLessThanEqualAndActiveIsTrue(String userId, LocalDate today);
}

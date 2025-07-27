package com.finwise.smartmoney.repository;

import com.finwise.smartmoney.entity.RecurringTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, Long> {
    List<RecurringTransaction> findByNextDueDateLessThanEqualAndActiveIsTrue(LocalDate today);
}

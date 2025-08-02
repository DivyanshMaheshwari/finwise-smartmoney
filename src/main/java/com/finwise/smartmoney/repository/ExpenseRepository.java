package com.finwise.smartmoney.repository;

import com.finwise.smartmoney.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserIdAndDateBetween(String userId,LocalDate start, LocalDate end);
}

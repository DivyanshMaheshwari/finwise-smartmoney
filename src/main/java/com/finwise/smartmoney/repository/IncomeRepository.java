package com.finwise.smartmoney.repository;

import com.finwise.smartmoney.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByUserIdAndDateBetween(String userId, LocalDate start, LocalDate end);
    List<Income> findByUserId(String userId);
    Optional<Income> findTopByUserIdAndCategoryIgnoreCaseOrderByDateDesc(String userId, String category);
}

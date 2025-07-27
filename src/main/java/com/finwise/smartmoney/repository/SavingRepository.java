package com.finwise.smartmoney.repository;

import com.finwise.smartmoney.entity.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Long> {
    List<Saving> findByDateBetween(LocalDate start, LocalDate end);
}

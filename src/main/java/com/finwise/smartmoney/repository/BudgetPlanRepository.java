package com.finwise.smartmoney.repository;

import com.finwise.smartmoney.entity.BudgetPlan;
import com.finwise.smartmoney.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BudgetPlanRepository extends JpaRepository<BudgetPlan, Long> {
    Optional<BudgetPlan> findBySalary(Salary salary);
}

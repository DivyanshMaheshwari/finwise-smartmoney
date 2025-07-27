package com.finwise.smartmoney.controller;

import com.finwise.smartmoney.dto.BudgetPlanResponseDTO;
import com.finwise.smartmoney.entity.Salary;
import com.finwise.smartmoney.service.BudgetPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/FinWise/budget")
public class BudgetController {

    @Autowired
    private BudgetPlanService budgetPlanService;

    @PostMapping("/generate/{salaryId}")
    public ResponseEntity<String> generateBudget(@PathVariable Long salaryId) {
        String response = budgetPlanService.generateBudgetForSalary(salaryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/salary/{salaryId}")
    public ResponseEntity<BudgetPlanResponseDTO> getBudgetBySalaryId(@PathVariable Salary salaryId) {
        BudgetPlanResponseDTO response = budgetPlanService.getBudgetBySalaryId(salaryId);
        return ResponseEntity.ok(response);
    }
}

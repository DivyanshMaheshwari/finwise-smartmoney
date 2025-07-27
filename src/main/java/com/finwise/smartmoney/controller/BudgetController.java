package com.finwise.smartmoney.controller;

import com.finwise.smartmoney.dto.BudgetPlanResponseDTO;
import com.finwise.smartmoney.service.BudgetPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/FinWise/budget")
public class BudgetController {

    @Autowired
    private BudgetPlanService budgetPlanService;

    @PostMapping("/generate/latest-salary")
    public ResponseEntity<String> generateFromLatestSalary() {
        String response = budgetPlanService.generateBudgetFromLatestSalary();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest-salary")
    public ResponseEntity<BudgetPlanResponseDTO> getLatestSalaryBudget() {
        BudgetPlanResponseDTO response = budgetPlanService.getBudgetByLatestSalary();
        return ResponseEntity.ok(response);
    }
}

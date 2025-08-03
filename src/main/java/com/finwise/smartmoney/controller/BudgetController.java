package com.finwise.smartmoney.controller;

import com.finwise.smartmoney.dto.BudgetPlanResponseDTO;
import com.finwise.smartmoney.service.BudgetPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/all")
    public ResponseEntity<List<BudgetPlanResponseDTO>> getAllBudgets() {
        return ResponseEntity.ok(budgetPlanService.getAllBudgetPlans());
    }
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> deleteBudget(@PathVariable Long id) {
        return ResponseEntity.ok(budgetPlanService.deleteBudgetPlan(id));
    }
}

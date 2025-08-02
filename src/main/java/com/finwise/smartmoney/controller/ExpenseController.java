package com.finwise.smartmoney.controller;

import com.finwise.smartmoney.dto.ExpenseRequestDTO;
import com.finwise.smartmoney.dto.ExpenseResponseDTO;
import com.finwise.smartmoney.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/FinWise/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<String> saveExpense(@RequestBody ExpenseRequestDTO dto) {
        return ResponseEntity.ok(expenseService.saveExpense(dto));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> getByMonth(
            @RequestParam int month,
            @RequestParam int year
    ) {
        return ResponseEntity.ok(expenseService.getExpensesByMonth(month, year));
    }
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.deleteIncome(id));
    }
}

package com.finwise.smartmoney.controller;

import com.finwise.smartmoney.dto.IncomeRequestDTO;
import com.finwise.smartmoney.dto.IncomeResponseDTO;
import com.finwise.smartmoney.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/FinWise/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping
    public ResponseEntity<String> saveIncome(@RequestBody IncomeRequestDTO dto) {
        return ResponseEntity.ok(incomeService.saveIncome(dto));
    }

    @GetMapping
    public ResponseEntity<List<IncomeResponseDTO>> getIncomes(
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(incomeService.getIncomesByMonth(month, year));
    }
}

package com.finwise.smartmoney.controller;

import com.finwise.smartmoney.dto.SalaryRequestDTO;
import com.finwise.smartmoney.dto.SalaryResponseDTO;
import com.finwise.smartmoney.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/FinWise/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping
    public ResponseEntity<SalaryResponseDTO> saveSalary(@RequestBody SalaryRequestDTO salaryRequestDto) {
        SalaryResponseDTO response = salaryService.saveSalary(salaryRequestDto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/all")
    public ResponseEntity<List<SalaryResponseDTO>> getAllSalaries() {
        List<SalaryResponseDTO> response = salaryService.getAllSalaries();
        return ResponseEntity.ok(response);
    }
}

package com.finwise.smartmoney.controller;

import com.finwise.smartmoney.dto.SavingRequestDTO;
import com.finwise.smartmoney.dto.SavingResponseDTO;
import com.finwise.smartmoney.service.SavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/FinWise/savings")
public class SavingController {

    @Autowired
    private SavingService savingService;

    @PostMapping
    public ResponseEntity<String> saveSaving(@RequestBody SavingRequestDTO dto) {
        return ResponseEntity.ok(savingService.saveSaving(dto));
    }

    @GetMapping
    public ResponseEntity<List<SavingResponseDTO>> getSavingsByMonth(
            @RequestParam("month") int month,
            @RequestParam("year") int year
    ) {
        return ResponseEntity.ok(savingService.getSavingsByMonth(month, year));
    }
}

package com.finwise.smartmoney.controller;

import com.finwise.smartmoney.dto.MonthlySummaryResponseDTO;
import com.finwise.smartmoney.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/FinWise/summary")
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @GetMapping
    public ResponseEntity<MonthlySummaryResponseDTO> getMonthlySummary(
            @RequestParam("month") int month,
            @RequestParam("year") int year
    ) {
        MonthlySummaryResponseDTO summary = summaryService.getSummary(month, year);
        return ResponseEntity.ok(summary);
    }
}

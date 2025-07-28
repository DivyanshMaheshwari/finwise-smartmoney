package com.finwise.smartmoney.controller;

import com.finwise.smartmoney.dto.MonthlySummaryResponseDTO;
import com.finwise.smartmoney.service.SummaryService;
import com.finwise.smartmoney.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/FinWise/summary")
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<MonthlySummaryResponseDTO> getMonthlySummary(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            HttpServletRequest request
    ) {
        // Extract token from Authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build(); // or throw a custom exception
        }
        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserId(token); // Extract userId from token

        MonthlySummaryResponseDTO summary = summaryService.getSummary(userId, month, year);
        return ResponseEntity.ok(summary);
    }
}

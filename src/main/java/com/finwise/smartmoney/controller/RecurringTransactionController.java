package com.finwise.smartmoney.controller;

import com.finwise.smartmoney.dto.UpdateDateRequest;
import com.finwise.smartmoney.service.RecurringTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("FinWise/recurring")
public class RecurringTransactionController {
    @Autowired
    private RecurringTransactionService recurringTransactionService;

    @PatchMapping("/{id}/update-date")
    public ResponseEntity<?> updateNextDueDate(@PathVariable Long id, @RequestBody UpdateDateRequest request) {
        recurringTransactionService.updateNextDueDate(id, request.getNewDueDate());
        return ResponseEntity.ok("Next due date updated successfully.");
    }
    @PatchMapping("/disable/{id}")
    public ResponseEntity<Void> disableRecurring(@PathVariable Long id) {
        recurringTransactionService.disableRecurring(id);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/enable/{id}")
    public ResponseEntity<String> enableRecurring(@PathVariable Long id) {
        recurringTransactionService.enableRecurring(id);
        return ResponseEntity.ok("Recurring transaction enabled successfully.");
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity<String> deleteRecurringTransaction(@PathVariable Long id) {
        recurringTransactionService.deleteRecurringTransaction(id);
        return ResponseEntity.ok("Recurring transaction deleted successfully.");
    }

}

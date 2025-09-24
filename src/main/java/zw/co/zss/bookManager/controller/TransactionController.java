package zw.co.zss.bookManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.zss.bookManager.model.Transaction;
import zw.co.zss.bookManager.service.TransactionService;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/purchase")
    public ResponseEntity<Transaction> processPurchase(@Valid @RequestBody Transaction transaction) {
        transaction.setType(Transaction.TransactionType.PURCHASE);
        if (transaction.getExtendedType() == null) {
            transaction.setExtendedType(Transaction.ExtendedType.NONE);
        }
        
        Transaction processedTransaction = transactionService.processTransaction(transaction);
        return ResponseEntity.status(HttpStatus.OK).body(processedTransaction);
    }

    @PostMapping("/refund")
    public ResponseEntity<Transaction> processRefund(@Valid @RequestBody Transaction transaction) {
        transaction.setType(Transaction.TransactionType.REFUND);
        Transaction processedTransaction = transactionService.processTransaction(transaction);
        return ResponseEntity.status(HttpStatus.OK).body(processedTransaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(transaction -> ResponseEntity.ok().body(transaction))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/reference/{reference}")
    public ResponseEntity<Transaction> getTransactionByReference(@PathVariable String reference) {
        return transactionService.getTransactionByReference(reference)
                .map(transaction -> ResponseEntity.ok().body(transaction))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Transaction>> getTransactionsByType(@PathVariable Transaction.TransactionType type) {
        List<Transaction> transactions = transactionService.getTransactionsByType(type);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/response-code/{responseCode}")
    public ResponseEntity<List<Transaction>> getTransactionsByResponseCode(@PathVariable String responseCode) {
        List<Transaction> transactions = transactionService.getTransactionsByResponseCode(responseCode);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Transaction>> getTransactionsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<Transaction> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/amount-range")
    public ResponseEntity<List<Transaction>> getTransactionsByAmountRange(
            @RequestParam BigDecimal minAmount,
            @RequestParam BigDecimal maxAmount) {
        List<Transaction> transactions = transactionService.getTransactionsByAmountRange(minAmount, maxAmount);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<Transaction>> getTransactionsByCardId(@PathVariable String cardId) {
        List<Transaction> transactions = transactionService.getTransactionsByCardId(cardId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/stats/successful")
    public ResponseEntity<Long> getSuccessfulTransactionCount() {
        Long count = transactionService.getSuccessfulTransactionCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/failed")
    public ResponseEntity<Long> getFailedTransactionCount() {
        Long count = transactionService.getFailedTransactionCount();
        return ResponseEntity.ok(count);
    }
}

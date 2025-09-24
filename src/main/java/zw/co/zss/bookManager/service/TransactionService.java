package zw.co.zss.bookManager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import zw.co.zss.bookManager.model.Transaction;
import zw.co.zss.bookManager.model.TransactionRequest;
import zw.co.zss.bookManager.model.TransactionResponse;
import zw.co.zss.bookManager.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${transaction-api.base-url}") String baseUrl;
    @Value("${transaction-api.api-token}") String apiToken;


    public Transaction processTransaction(Transaction transaction) {
        log.info("processTransaction {}", transaction);
        if (transaction.getCreated() == null) {
            transaction.setCreated(LocalDateTime.now());
        }
        if (transaction.getReference() == null || transaction.getReference().isEmpty()) {
            transaction.setReference(UUID.randomUUID().toString());
        }
        Map<String, Object> additionalData = new HashMap<>();
        additionalData.put("reference", transaction.getReference());
        var request = TransactionRequest.builder()
                .additionalData(additionalData)
                .card(transaction.getCard())
                .amount(transaction.getAmount())
                .reference(transaction.getReference())
                .narration(transaction.getNarration())
                .extendedType(transaction.getExtendedType().name())
                .created(transaction.getCreated())
                .type(transaction.getType().name())
                .build();
        log.info("request {}", request);
        if (isValidTransaction(transaction)) {
            try{
                TransactionResponse response = submit(request);
                log.info("response {}", response);
                transaction.setResponseCode(response.getResponseCode());
                transaction.setResponseDescription(response.getResponseDescription());
                transaction.setDebitReference(UUID.randomUUID().toString());
                return transactionRepository.save(transaction);

            }catch (Exception e){
                log.error(e.getMessage());
                transaction.setResponseCode("014");
                transaction.setResponseDescription(e.getMessage());
                return transaction;
            }
        } else {
            transaction.setResponseCode("013");
            transaction.setResponseDescription("Failed Transaction Validation");
            return transaction;
        }

    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public Optional<Transaction> getTransactionByReference(String reference) {
        return transactionRepository.findByReference(reference);
    }

    public List<Transaction> getTransactionsByType(Transaction.TransactionType type) {
        return transactionRepository.findByType(type);
    }

    public List<Transaction> getTransactionsByResponseCode(String responseCode) {
        return transactionRepository.findByResponseCode(responseCode);
    }

    public List<Transaction> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByCreatedBetween(startDate, endDate);
    }

    public List<Transaction> getTransactionsByAmountRange(BigDecimal minAmount, BigDecimal maxAmount) {
        return transactionRepository.findByAmountBetween(minAmount, maxAmount);
    }

    public List<Transaction> getTransactionsByCardId(String cardId) {
        return transactionRepository.findByCardId(cardId);
    }

    public Long getSuccessfulTransactionCount() {
        return transactionRepository.countSuccessfulTransactions();
    }

    public Long getFailedTransactionCount() {
        return transactionRepository.countFailedTransactions();
    }

    private boolean isValidTransaction(Transaction transaction) {
        // Basic validation logic
        if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        
        if (transaction.getCard() == null || transaction.getCard().getId() == null) {
            return false;
        }
        
        // Check card expiry
        if (transaction.getCard().getExpiry() != null && 
            transaction.getCard().getExpiry().isBefore(LocalDateTime.now().toLocalDate())) {
            return false;
        }
        
        return true;
    }

    public TransactionResponse submit(TransactionRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiToken);

        HttpEntity<TransactionRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<TransactionResponse> response = restTemplate.exchange(
                baseUrl + "/api/transaction",
                HttpMethod.POST,
                entity,
                TransactionResponse.class
        );

        return response.getBody();
    }

}

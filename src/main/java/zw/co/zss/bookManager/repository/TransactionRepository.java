package zw.co.zss.bookManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zw.co.zss.bookManager.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    Optional<Transaction> findByReference(String reference);
    
    List<Transaction> findByType(Transaction.TransactionType type);
    
    List<Transaction> findByResponseCode(String responseCode);
    
    List<Transaction> findByCreatedBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Transaction> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);
    
    @Query("SELECT t FROM Transaction t WHERE t.card.id = :cardId")
    List<Transaction> findByCardId(@Param("cardId") String cardId);
    
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.responseCode = '000'")
    Long countSuccessfulTransactions();
    
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.responseCode != '000'")
    Long countFailedTransactions();
}

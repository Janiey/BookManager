package zw.co.zss.bookManager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "extended_type")
    private ExtendedType extendedType;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    @Column(nullable = false)
    private LocalDateTime created;
    
    @Column(unique = true, nullable = false)
    private String reference;
    
    @Column(columnDefinition = "TEXT")
    private String narration;
    
    @ElementCollection
    @CollectionTable(name = "transaction_additional_data", 
                    joinColumns = @JoinColumn(name = "transaction_id"))
    @MapKeyColumn(name = "data_key")
    @Column(name = "data_value")
    private Map<String, String> additionalData;
    
    @Embedded
    private Card card;
    
    @Column(name = "response_code", length = 10)
    private String responseCode;
    
    @Column(name = "response_description")
    private String responseDescription;
    
    @Column(name = "debit_reference")
    private String debitReference;
    
    @UpdateTimestamp
    @Column(name = "updated")
    private LocalDateTime updated;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    public enum TransactionType {
        PURCHASE,
        REFUND,
        REVERSAL
    }
    
    public enum ExtendedType {
        NONE,
        PARTIAL,
        FULL
    }
}

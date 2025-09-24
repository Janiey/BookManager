package zw.co.zss.bookManager.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Data
@Builder
public class TransactionRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String type;
    private String extendedType;
    private BigDecimal amount;
    private LocalDateTime created;//" : "2025-08-08T08:58:12.088Z",
    private Card card;
    private String reference;
    private String narration;
    private Map<String, Object> additionalData;

}

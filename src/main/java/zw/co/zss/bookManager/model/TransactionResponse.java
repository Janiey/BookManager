package zw.co.zss.bookManager.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class TransactionResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date updated;
    private String responseCode;
    private String responseDescription;
    private String reference;
    private String debitReference;
}

package zw.co.zss.bookManager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(name = "card_id", length = 20)
    private String id;
    
    @Column(name = "card_expiry")
    private LocalDate expiry;
}

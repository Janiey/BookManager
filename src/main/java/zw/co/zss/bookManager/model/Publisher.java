package zw.co.zss.bookManager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "publishers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publisher implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 200)
    private String name;
    
    @Column(length = 500)
    private String address;
    
    @Column(length = 100)
    private String city;
    
    @Column(length = 100)
    private String country;
    
    @Column(length = 20)
    private String phone;
    
    @Column(length = 200)
    private String email;
    
    @Column(length = 500)
    private String website;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Book> books;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

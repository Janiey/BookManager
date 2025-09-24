package zw.co.zss.bookManager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"authors", "category", "publisher"})
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(unique = true, length = 20)
    private String isbn;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "page_count")
    private Integer pageCount;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum BookStatus {
        AVAILABLE,
        OUT_OF_STOCK,
        DISCONTINUED
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book other = (Book) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

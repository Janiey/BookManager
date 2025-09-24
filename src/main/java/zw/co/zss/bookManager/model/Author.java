package zw.co.zss.bookManager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"books"})
public class Author implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(unique = true, length = 200)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String biography;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(length = 100)
    private String nationality;

    @Column(length = 500)
    private String website;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private Set<Book> books = new LinkedHashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public String getFullName() {
        return (firstName != null ? firstName : "") +
                (lastName != null && !lastName.isBlank() ? " " + lastName : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author other = (Author) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

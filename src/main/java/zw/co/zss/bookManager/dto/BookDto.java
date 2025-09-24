package zw.co.zss.bookManager.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
@Data
public class BookDto {
    private Long id;
    private String title;
    private String isbn;
    private String description;
    private LocalDate publicationDate;
    private BigDecimal price;
    private Integer pageCount;
    private String status;          // enum converted to String
    private Integer stockQuantity;

    // Flattened relations
    private Set<AuthorDto> authors; // Nested DTO instead of entities
    private Long categoryId;
    private String categoryName;
    private Long publisherId;
    private String publisherName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
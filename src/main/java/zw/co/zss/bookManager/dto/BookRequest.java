package zw.co.zss.bookManager.dto;

import lombok.Data;
import zw.co.zss.bookManager.model.Book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class BookRequest {
    private String title;
    private String isbn;
    private String description;

    // maps JSON "publishedDate"
    private LocalDate publishedDate;

    private BigDecimal price;
    private Integer pageCount;   // optional

    private Book.BookStatus status;

    // maps JSON "stock"
    private Integer stock;

    private Long categoryId;
    private Long publisherId;
    private List<Long> authorIds;

}

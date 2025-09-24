package zw.co.zss.bookManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zw.co.zss.bookManager.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Find by title (case-insensitive)
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    // Find by ISBN
    Optional<Book> findByIsbn(String isbn);
    
    // Find by category
    List<Book> findByCategoryId(Long categoryId);
    
    // Find by publisher
    List<Book> findByPublisherId(Long publisherId);
    
    // Find by author
    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId")
    List<Book> findByAuthorId(@Param("authorId") Long authorId);
    
    // Find by price range
    List<Book> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    // Find by status
    List<Book> findByStatus(Book.BookStatus status);
    
    // Find available books (in stock)
    @Query("SELECT b FROM Book b WHERE b.status = 'AVAILABLE' AND b.stockQuantity > 0")
    List<Book> findAvailableBooks();
    
    // Search books by title, description, or author name
    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN b.authors a " +
           "WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(b.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(CONCAT(a.firstName, ' ', a.lastName)) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Book> searchBooks(@Param("searchTerm") String searchTerm);
    
    // Count books by category
    @Query("SELECT COUNT(b) FROM Book b WHERE b.category.id = :categoryId")
    Long countBooksByCategory(@Param("categoryId") Long categoryId);
    
    // Find books with low stock
    @Query("SELECT b FROM Book b WHERE b.stockQuantity <= :threshold AND b.status = 'AVAILABLE'")
    List<Book> findBooksWithLowStock(@Param("threshold") Integer threshold);
}

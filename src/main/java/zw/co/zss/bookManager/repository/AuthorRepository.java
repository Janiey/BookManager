package zw.co.zss.bookManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zw.co.zss.bookManager.model.Author;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    
    // Find by first name (case-insensitive)
    List<Author> findByFirstNameContainingIgnoreCase(String firstName);
    
    // Find by last name (case-insensitive)
    List<Author> findByLastNameContainingIgnoreCase(String lastName);
    
    // Find by full name (case-insensitive)
    @Query("SELECT a FROM Author a WHERE LOWER(CONCAT(a.firstName, ' ', a.lastName)) LIKE LOWER(CONCAT('%', :fullName, '%'))")
    List<Author> findByFullNameContainingIgnoreCase(@Param("fullName") String fullName);
    
    // Find by email
    Optional<Author> findByEmail(String email);
    
    // Find by nationality
    List<Author> findByNationalityIgnoreCase(String nationality);
    
    // Search authors by name, email, or nationality
    @Query("SELECT a FROM Author a WHERE " +
           "LOWER(a.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(a.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(a.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(a.nationality) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Author> searchAuthors(@Param("searchTerm") String searchTerm);
    
    // Find authors with books
    @Query("SELECT DISTINCT a FROM Author a JOIN a.books b")
    List<Author> findAuthorsWithBooks();
    
    // Find authors without books
    @Query("SELECT a FROM Author a WHERE a.books IS EMPTY")
    List<Author> findAuthorsWithoutBooks();
    
    // Count books by author
    @Query("SELECT COUNT(b) FROM Book b JOIN b.authors a WHERE a.id = :authorId")
    Long countBooksByAuthor(@Param("authorId") Long authorId);
}

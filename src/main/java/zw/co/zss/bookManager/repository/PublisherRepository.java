package zw.co.zss.bookManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zw.co.zss.bookManager.model.Publisher;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    
    // Find by name (case-insensitive)
    Optional<Publisher> findByNameIgnoreCase(String name);
    
    // Find publishers containing name (case-insensitive)
    List<Publisher> findByNameContainingIgnoreCase(String name);
    
    // Find by email
    Optional<Publisher> findByEmail(String email);
    
    // Find by city (case-insensitive)
    List<Publisher> findByCityIgnoreCase(String city);
    
    // Find by country (case-insensitive)
    List<Publisher> findByCountryIgnoreCase(String country);
    
    // Search publishers by name, city, country, or email
    @Query("SELECT p FROM Publisher p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(p.city) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(p.country) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(p.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Publisher> searchPublishers(@Param("searchTerm") String searchTerm);
    
    // Find publishers with books
    @Query("SELECT DISTINCT p FROM Publisher p WHERE p.books IS NOT EMPTY")
    List<Publisher> findPublishersWithBooks();
    
    // Find publishers without books
    @Query("SELECT p FROM Publisher p WHERE p.books IS EMPTY")
    List<Publisher> findPublishersWithoutBooks();
    
    // Count books by publisher
    @Query("SELECT COUNT(b) FROM Book b WHERE b.publisher.id = :publisherId")
    Long countBooksByPublisher(@Param("publisherId") Long publisherId);
    
    // Get publisher statistics (publisher with book count)
    @Query("SELECT p.name, COUNT(b) FROM Publisher p LEFT JOIN p.books b GROUP BY p.id, p.name")
    List<Object[]> getPublisherStatistics();
}

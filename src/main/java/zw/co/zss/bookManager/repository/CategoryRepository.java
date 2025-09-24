package zw.co.zss.bookManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zw.co.zss.bookManager.model.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // Find by name (case-insensitive)
    Optional<Category> findByNameIgnoreCase(String name);
    
    // Find by code
    Optional<Category> findByCode(String code);
    
    // Find categories containing name (case-insensitive)
    List<Category> findByNameContainingIgnoreCase(String name);
    
    // Search categories by name, description, or code
    @Query("SELECT c FROM Category c WHERE " +
           "LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(c.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(c.code) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Category> searchCategories(@Param("searchTerm") String searchTerm);
    
    // Find categories with books
    @Query("SELECT DISTINCT c FROM Category c WHERE c.books IS NOT EMPTY")
    List<Category> findCategoriesWithBooks();
    
    // Find categories without books
    @Query("SELECT c FROM Category c WHERE c.books IS EMPTY")
    List<Category> findCategoriesWithoutBooks();
    
    // Get category statistics (category with book count)
    @Query("SELECT c.name, COUNT(b) FROM Category c LEFT JOIN c.books b GROUP BY c.id, c.name")
    List<Object[]> getCategoryStatistics();
}

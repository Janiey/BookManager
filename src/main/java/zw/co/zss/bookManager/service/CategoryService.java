package zw.co.zss.bookManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.co.zss.bookManager.model.Category;
import zw.co.zss.bookManager.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name);
    }

    public Optional<Category> getCategoryByCode(String code) {
        return categoryRepository.findByCode(code);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(categoryDetails.getName());
                    category.setDescription(categoryDetails.getDescription());
                    category.setCode(categoryDetails.getCode());
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    public List<Category> searchCategoriesByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Category> searchCategories(String searchTerm) {
        return categoryRepository.searchCategories(searchTerm);
    }

    public List<Category> getCategoriesWithBooks() {
        return categoryRepository.findCategoriesWithBooks();
    }

    public List<Category> getCategoriesWithoutBooks() {
        return categoryRepository.findCategoriesWithoutBooks();
    }

    public List<Object[]> getCategoryStatistics() {
        return categoryRepository.getCategoryStatistics();
    }

    public boolean existsByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name).isPresent();
    }

    public boolean existsByCode(String code) {
        return categoryRepository.findByCode(code).isPresent();
    }
}

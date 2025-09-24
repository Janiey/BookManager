package zw.co.zss.bookManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.zss.bookManager.model.Category;
import zw.co.zss.bookManager.service.CategoryService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Category>> getAllCategories(Pageable pageable) {
        Page<Category> categories = categoryService.getAllCategories(pageable);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(category -> ResponseEntity.ok().body(category))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name)
                .map(category -> ResponseEntity.ok().body(category))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Category> getCategoryByCode(@PathVariable String code) {
        return categoryService.getCategoryByCode(code)
                .map(category -> ResponseEntity.ok().body(category))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        if (categoryService.existsByName(category.getName()) || 
            categoryService.existsByCode(category.getCode())) {
            return ResponseEntity.badRequest().build();
        }
        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category categoryDetails) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
            return ResponseEntity.ok(updatedCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Category>> searchCategories(@RequestParam String query) {
        List<Category> categories = categoryService.searchCategories(query);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Category>> searchCategoriesByName(@RequestParam String name) {
        List<Category> categories = categoryService.searchCategoriesByName(name);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/with-books")
    public ResponseEntity<List<Category>> getCategoriesWithBooks() {
        List<Category> categories = categoryService.getCategoriesWithBooks();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/without-books")
    public ResponseEntity<List<Category>> getCategoriesWithoutBooks() {
        List<Category> categories = categoryService.getCategoriesWithoutBooks();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<Object[]>> getCategoryStatistics() {
        List<Object[]> statistics = categoryService.getCategoryStatistics();
        return ResponseEntity.ok(statistics);
    }
}

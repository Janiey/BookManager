package zw.co.zss.bookManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.zss.bookManager.dto.BookDto;
import zw.co.zss.bookManager.dto.BookRequest;
import zw.co.zss.bookManager.model.Book;
import zw.co.zss.bookManager.service.BookService;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<BookDto>> getAllBooks(Pageable pageable) {
        return ResponseEntity.ok(bookService.getAllBooks(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDto> getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookRequest book) {
        if (bookService.existsByIsbn(book.getIsbn())) {
            return ResponseEntity.badRequest().build();
        }
        BookDto savedBook = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @Valid @RequestBody Book bookDetails) {
        try {
            BookDto updatedBook = bookService.updateBook(id, bookDetails);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> searchBooks(@RequestParam String query) {
        return ResponseEntity.ok(bookService.searchBooks(query));
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<BookDto>> searchBooksByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.searchBooksByTitle(title));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<BookDto>> getBooksByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(bookService.getBooksByCategory(categoryId));
    }

    @GetMapping("/publisher/{publisherId}")
    public ResponseEntity<List<BookDto>> getBooksByPublisher(@PathVariable Long publisherId) {
        return ResponseEntity.ok(bookService.getBooksByPublisher(publisherId));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<BookDto>> getBooksByAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(authorId));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<BookDto>> getBooksByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(bookService.getBooksByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BookDto>> getBooksByStatus(@PathVariable Book.BookStatus status) {
        return ResponseEntity.ok(bookService.getBooksByStatus(status));
    }

    @GetMapping("/available")
    public ResponseEntity<List<BookDto>> getAvailableBooks() {
        return ResponseEntity.ok(bookService.getAvailableBooks());
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<BookDto>> getBooksWithLowStock(
            @RequestParam(defaultValue = "5") Integer threshold) {
        return ResponseEntity.ok(bookService.getBooksWithLowStock(threshold));
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<BookDto> updateBookStock(@PathVariable Long id, @RequestParam Integer stock) {
        try {
            BookDto updatedBook = bookService.updateBookStock(id, stock);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{categoryId}/count")
    public ResponseEntity<Long> countBooksByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(bookService.countBooksByCategory(categoryId));
    }
}

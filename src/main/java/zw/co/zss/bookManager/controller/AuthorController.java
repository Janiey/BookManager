package zw.co.zss.bookManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.zss.bookManager.model.Author;
import zw.co.zss.bookManager.service.AuthorService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "*")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Author>> getAllAuthors(Pageable pageable) {
        Page<Author> authors = authorService.getAllAuthors(pageable);
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id)
                .map(author -> ResponseEntity.ok().body(author))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Author> getAuthorByEmail(@PathVariable String email) {
        return authorService.getAuthorByEmail(email)
                .map(author -> ResponseEntity.ok().body(author))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
        if (authorService.existsByEmail(author.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        Author savedAuthor = authorService.saveAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @Valid @RequestBody Author authorDetails) {
        try {
            Author updatedAuthor = authorService.updateAuthor(id, authorDetails);
            return ResponseEntity.ok(updatedAuthor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Author>> searchAuthors(@RequestParam String query) {
        List<Author> authors = authorService.searchAuthors(query);
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/search/firstname")
    public ResponseEntity<List<Author>> searchAuthorsByFirstName(@RequestParam String firstName) {
        List<Author> authors = authorService.searchAuthorsByFirstName(firstName);
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/search/lastname")
    public ResponseEntity<List<Author>> searchAuthorsByLastName(@RequestParam String lastName) {
        List<Author> authors = authorService.searchAuthorsByLastName(lastName);
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/search/fullname")
    public ResponseEntity<List<Author>> searchAuthorsByFullName(@RequestParam String fullName) {
        List<Author> authors = authorService.searchAuthorsByFullName(fullName);
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/nationality/{nationality}")
    public ResponseEntity<List<Author>> getAuthorsByNationality(@PathVariable String nationality) {
        List<Author> authors = authorService.getAuthorsByNationality(nationality);
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/with-books")
    public ResponseEntity<List<Author>> getAuthorsWithBooks() {
        List<Author> authors = authorService.getAuthorsWithBooks();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/without-books")
    public ResponseEntity<List<Author>> getAuthorsWithoutBooks() {
        List<Author> authors = authorService.getAuthorsWithoutBooks();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}/books/count")
    public ResponseEntity<Long> countBooksByAuthor(@PathVariable Long id) {
        Long count = authorService.countBooksByAuthor(id);
        return ResponseEntity.ok(count);
    }
}

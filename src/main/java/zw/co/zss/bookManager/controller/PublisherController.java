package zw.co.zss.bookManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.zss.bookManager.model.Publisher;
import zw.co.zss.bookManager.service.PublisherService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/publishers")
@CrossOrigin(origins = "*")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        List<Publisher> publishers = publisherService.getAllPublishers();
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Publisher>> getAllPublishers(Pageable pageable) {
        Page<Publisher> publishers = publisherService.getAllPublishers(pageable);
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable Long id) {
        return publisherService.getPublisherById(id)
                .map(publisher -> ResponseEntity.ok().body(publisher))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Publisher> getPublisherByName(@PathVariable String name) {
        return publisherService.getPublisherByName(name)
                .map(publisher -> ResponseEntity.ok().body(publisher))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Publisher> getPublisherByEmail(@PathVariable String email) {
        return publisherService.getPublisherByEmail(email)
                .map(publisher -> ResponseEntity.ok().body(publisher))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@Valid @RequestBody Publisher publisher) {
        if (publisherService.existsByName(publisher.getName()) || 
            publisherService.existsByEmail(publisher.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        Publisher savedPublisher = publisherService.savePublisher(publisher);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPublisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable Long id, @Valid @RequestBody Publisher publisherDetails) {
        try {
            Publisher updatedPublisher = publisherService.updatePublisher(id, publisherDetails);
            return ResponseEntity.ok(updatedPublisher);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable Long id) {
        try {
            publisherService.deletePublisher(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Publisher>> searchPublishers(@RequestParam String query) {
        List<Publisher> publishers = publisherService.searchPublishers(query);
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Publisher>> searchPublishersByName(@RequestParam String name) {
        List<Publisher> publishers = publisherService.searchPublishersByName(name);
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Publisher>> getPublishersByCity(@PathVariable String city) {
        List<Publisher> publishers = publisherService.getPublishersByCity(city);
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<Publisher>> getPublishersByCountry(@PathVariable String country) {
        List<Publisher> publishers = publisherService.getPublishersByCountry(country);
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/with-books")
    public ResponseEntity<List<Publisher>> getPublishersWithBooks() {
        List<Publisher> publishers = publisherService.getPublishersWithBooks();
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/without-books")
    public ResponseEntity<List<Publisher>> getPublishersWithoutBooks() {
        List<Publisher> publishers = publisherService.getPublishersWithoutBooks();
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/{id}/books/count")
    public ResponseEntity<Long> countBooksByPublisher(@PathVariable Long id) {
        Long count = publisherService.countBooksByPublisher(id);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<Object[]>> getPublisherStatistics() {
        List<Object[]> statistics = publisherService.getPublisherStatistics();
        return ResponseEntity.ok(statistics);
    }
}

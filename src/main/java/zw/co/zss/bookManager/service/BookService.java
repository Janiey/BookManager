package zw.co.zss.bookManager.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.co.zss.bookManager.dto.AuthorDto;
import zw.co.zss.bookManager.dto.BookDto;
import zw.co.zss.bookManager.dto.BookRequest;
import zw.co.zss.bookManager.model.Author;
import zw.co.zss.bookManager.model.Book;
import zw.co.zss.bookManager.model.Category;
import zw.co.zss.bookManager.model.Publisher;
import zw.co.zss.bookManager.repository.BookRepository;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @PersistenceContext
    private EntityManager em;



    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<BookDto> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(this::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<BookDto> getBookById(Long id) {
        return bookRepository.findById(id).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<BookDto> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).map(this::toDto);
    }


    @Transactional
    public BookDto saveBook(BookRequest req) {
        Book b = new Book();
        b.setId(null);
        b.setTitle(req.getTitle());
        b.setIsbn(req.getIsbn());
        b.setDescription(req.getDescription());
        b.setPublicationDate(req.getPublishedDate());
        b.setPrice(req.getPrice());
        b.setPageCount(req.getPageCount());
        b.setStatus(req.getStatus());
        b.setStockQuantity(req.getStock());

        if (req.getCategoryId() != null) {
            b.setCategory(em.getReference(Category.class, req.getCategoryId()));
        }
        if (req.getPublisherId() != null) {
            b.setPublisher(em.getReference(Publisher.class, req.getPublisherId()));
        }
        if (req.getAuthorIds() != null && !req.getAuthorIds().isEmpty()) {
            b.setAuthors(req.getAuthorIds().stream()
                    .map(id -> em.getReference(Author.class, id))
                    .collect(Collectors.toCollection(LinkedHashSet::new)));
        }

        Book saved = bookRepository.save(b);
        return toDto(saved);
    }

    private BookDto toDtos(Book b) {
        BookDto dto = new BookDto();
        dto.setId(b.getId());
        dto.setTitle(b.getTitle());
        dto.setIsbn(b.getIsbn());
        dto.setDescription(b.getDescription());
        dto.setPublicationDate(b.getPublicationDate());
        dto.setPrice(b.getPrice());
        dto.setPageCount(b.getPageCount());
        dto.setStatus(b.getStatus().name());
        dto.setStockQuantity(b.getStockQuantity());

        if (b.getCategory() != null) {
            dto.setCategoryId(b.getCategory().getId());
            dto.setCategoryName(b.getCategory().getName());
        }
        if (b.getPublisher() != null) {
            dto.setPublisherId(b.getPublisher().getId());
            dto.setPublisherName(b.getPublisher().getName());
        }
        if (b.getAuthors() != null) {
            dto.setAuthors(
                    (Set<AuthorDto>) b.getAuthors().stream()
                            .map(a -> new AuthorDto(a.getId(), a.getFirstName(), a.getLastName()))
                            .toList()
            );
        }
        dto.setCreatedAt(b.getCreatedAt());
        dto.setUpdatedAt(b.getUpdatedAt());
        return dto;
    }

    public BookDto updateBook(Long id, Book bookDetails) {
        Book updated = bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(bookDetails.getTitle());
                    book.setIsbn(bookDetails.getIsbn());
                    book.setDescription(bookDetails.getDescription());
                    book.setPublicationDate(bookDetails.getPublicationDate());
                    book.setPrice(bookDetails.getPrice());
                    book.setPageCount(bookDetails.getPageCount());
                    book.setStatus(bookDetails.getStatus());
                    book.setStockQuantity(bookDetails.getStockQuantity());
                    book.setCategory(bookDetails.getCategory());
                    book.setPublisher(bookDetails.getPublisher());
                    book.setAuthors(bookDetails.getAuthors());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return toDto(updated);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public List<BookDto> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<BookDto> searchBooks(String searchTerm) {
        return bookRepository.searchBooks(searchTerm)
                .stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<BookDto> getBooksByCategory(Long categoryId) {
        return bookRepository.findByCategoryId(categoryId)
                .stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<BookDto> getBooksByPublisher(Long publisherId) {
        return bookRepository.findByPublisherId(publisherId)
                .stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<BookDto> getBooksByAuthor(Long authorId) {
        return bookRepository.findByAuthorId(authorId)
                .stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<BookDto> getBooksByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return bookRepository.findByPriceBetween(minPrice, maxPrice)
                .stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<BookDto> getBooksByStatus(Book.BookStatus status) {
        return bookRepository.findByStatus(status)
                .stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAvailableBooks() {
        return bookRepository.findAvailableBooks()
                .stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<BookDto> getBooksWithLowStock(Integer threshold) {
        return bookRepository.findBooksWithLowStock(threshold)
                .stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Long countBooksByCategory(Long categoryId) {
        return bookRepository.countBooksByCategory(categoryId);
    }

    @Transactional(readOnly = true)
    public boolean existsByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).isPresent();
    }

    public BookDto updateBookStock(Long id, Integer newStock) {
        Book updated = bookRepository.findById(id)
                .map(book -> {
                    book.setStockQuantity(newStock);
                    if (newStock != null) {
                        if (newStock > 0 && book.getStatus() == Book.BookStatus.OUT_OF_STOCK) {
                            book.setStatus(Book.BookStatus.AVAILABLE);
                        } else if (newStock == 0) {
                            book.setStatus(Book.BookStatus.OUT_OF_STOCK);
                        }
                    }
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return toDto(updated);
    }


    private BookDto toDto(Book b) {
        if (b == null) return null;

        Long categoryId = null;
        String categoryName = null;
        Category cat = b.getCategory();
        if (cat != null) {
            categoryId = cat.getId();
            categoryName = cat.getName();
        }

        Long publisherId = null;
        String publisherName = null;
        Publisher pub = b.getPublisher();
        if (pub != null) {
            publisherId = pub.getId();
            publisherName = pub.getName();
        }

        Set<AuthorDto> authorDtos = null;
        Set<Author> authors = b.getAuthors();
        if (authors != null) {
            authorDtos = authors.stream()
                    .map(a -> new AuthorDto(a.getId(), a.getFullName()))
                    .collect(Collectors.toSet());
        }

        BookDto dto = new BookDto();
        dto.setId(b.getId());
        dto.setTitle(b.getTitle());
        dto.setIsbn(b.getIsbn());
        dto.setDescription(b.getDescription());
        dto.setPublicationDate(b.getPublicationDate());
        dto.setPrice(b.getPrice());
        dto.setPageCount(b.getPageCount());
        dto.setStatus(b.getStatus() != null ? b.getStatus().name() : null);
        dto.setStockQuantity(b.getStockQuantity());
        dto.setAuthors(authorDtos);
        dto.setCategoryId(categoryId);
        dto.setCategoryName(categoryName);
        dto.setPublisherId(publisherId);
        dto.setPublisherName(publisherName);
        dto.setCreatedAt(b.getCreatedAt());
        dto.setUpdatedAt(b.getUpdatedAt());
        return dto;
    }
}

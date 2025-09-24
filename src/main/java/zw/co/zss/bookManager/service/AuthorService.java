package zw.co.zss.bookManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.co.zss.bookManager.model.Author;
import zw.co.zss.bookManager.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Page<Author> getAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Optional<Author> getAuthorByEmail(String email) {
        return authorRepository.findByEmail(email);
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author authorDetails) {
        return authorRepository.findById(id)
                .map(author -> {
                    author.setFirstName(authorDetails.getFirstName());
                    author.setLastName(authorDetails.getLastName());
                    author.setEmail(authorDetails.getEmail());
                    author.setBiography(authorDetails.getBiography());
                    author.setBirthDate(authorDetails.getBirthDate());
                    author.setNationality(authorDetails.getNationality());
                    author.setWebsite(authorDetails.getWebsite());
                    return authorRepository.save(author);
                })
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }

    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }

    public List<Author> searchAuthorsByFirstName(String firstName) {
        return authorRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    public List<Author> searchAuthorsByLastName(String lastName) {
        return authorRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    public List<Author> searchAuthorsByFullName(String fullName) {
        return authorRepository.findByFullNameContainingIgnoreCase(fullName);
    }

    public List<Author> searchAuthors(String searchTerm) {
        return authorRepository.searchAuthors(searchTerm);
    }

    public List<Author> getAuthorsByNationality(String nationality) {
        return authorRepository.findByNationalityIgnoreCase(nationality);
    }

    public List<Author> getAuthorsWithBooks() {
        return authorRepository.findAuthorsWithBooks();
    }

    public List<Author> getAuthorsWithoutBooks() {
        return authorRepository.findAuthorsWithoutBooks();
    }

    public Long countBooksByAuthor(Long authorId) {
        return authorRepository.countBooksByAuthor(authorId);
    }

    public boolean existsByEmail(String email) {
        return authorRepository.findByEmail(email).isPresent();
    }
}

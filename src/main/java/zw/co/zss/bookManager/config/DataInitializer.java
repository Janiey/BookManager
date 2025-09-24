package zw.co.zss.bookManager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import zw.co.zss.bookManager.model.Author;
import zw.co.zss.bookManager.model.Book;
import zw.co.zss.bookManager.model.Category;
import zw.co.zss.bookManager.model.Publisher;
import zw.co.zss.bookManager.repository.AuthorRepository;
import zw.co.zss.bookManager.repository.BookRepository;
import zw.co.zss.bookManager.repository.CategoryRepository;
import zw.co.zss.bookManager.repository.PublisherRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create Categories
        Category fiction = new Category();
        fiction.setName("Fiction");
        fiction.setDescription("Fictional literature including novels and short stories");
        fiction.setCode("FIC");
        categoryRepository.save(fiction);

        Category nonFiction = new Category();
        nonFiction.setName("Non-Fiction");
        nonFiction.setDescription("Factual books including biographies, history, and educational content");
        nonFiction.setCode("NF");
        categoryRepository.save(nonFiction);

        Category technology = new Category();
        technology.setName("Technology");
        technology.setDescription("Books about programming, software development, and technology");
        technology.setCode("TECH");
        categoryRepository.save(technology);

        // Create Publishers
        Publisher penguinRandom = new Publisher();
        penguinRandom.setName("Penguin Random House");
        penguinRandom.setCity("New York");
        penguinRandom.setCountry("USA");
        penguinRandom.setEmail("info@penguinrandomhouse.com");
        penguinRandom.setWebsite("https://www.penguinrandomhouse.com");
        publisherRepository.save(penguinRandom);

        Publisher oreilly = new Publisher();
        oreilly.setName("O'Reilly Media");
        oreilly.setCity("Sebastopol");
        oreilly.setCountry("USA");
        oreilly.setEmail("info@oreilly.com");
        oreilly.setWebsite("https://www.oreilly.com");
        publisherRepository.save(oreilly);

        // Create Authors
        Author jkRowling = new Author();
        jkRowling.setFirstName("J.K.");
        jkRowling.setLastName("Rowling");
        jkRowling.setEmail("jk.rowling@example.com");
        jkRowling.setBirthDate(LocalDate.of(1965, 7, 31));
        jkRowling.setNationality("British");
        jkRowling.setBiography("British author, best known for the Harry Potter series");
        authorRepository.save(jkRowling);

        Author martinFowler = new Author();
        martinFowler.setFirstName("Martin");
        martinFowler.setLastName("Fowler");
        martinFowler.setEmail("martin.fowler@example.com");
        martinFowler.setBirthDate(LocalDate.of(1963, 12, 18));
        martinFowler.setNationality("British");
        martinFowler.setBiography("British software developer, author and international public speaker on software development");
        martinFowler.setWebsite("https://martinfowler.com");
        authorRepository.save(martinFowler);

        Author georgeOrwell = new Author();
        georgeOrwell.setFirstName("George");
        georgeOrwell.setLastName("Orwell");
        georgeOrwell.setBirthDate(LocalDate.of(1903, 6, 25));
        georgeOrwell.setNationality("British");
        georgeOrwell.setBiography("English novelist, essayist, journalist and critic");
        authorRepository.save(georgeOrwell);

        // Create Books
        Book harryPotter = new Book();
        harryPotter.setTitle("Harry Potter and the Philosopher's Stone");
        harryPotter.setIsbn("978-0747532699");
        harryPotter.setDescription("The first novel in the Harry Potter series");
        harryPotter.setPublicationDate(LocalDate.of(1997, 6, 26));
        harryPotter.setPrice(new BigDecimal("12.99"));
        harryPotter.setPageCount(223);
        harryPotter.setStatus(Book.BookStatus.AVAILABLE);
        harryPotter.setStockQuantity(50);
        harryPotter.setCategory(fiction);
        harryPotter.setPublisher(penguinRandom);
        harryPotter.setAuthors(Set.of(jkRowling));
        bookRepository.save(harryPotter);

        Book refactoring = new Book();
        refactoring.setTitle("Refactoring: Improving the Design of Existing Code");
        refactoring.setIsbn("978-0134757599");
        refactoring.setDescription("A comprehensive guide to refactoring techniques");
        refactoring.setPublicationDate(LocalDate.of(2018, 11, 20));
        refactoring.setPrice(new BigDecimal("54.99"));
        refactoring.setPageCount(448);
        refactoring.setStatus(Book.BookStatus.AVAILABLE);
        refactoring.setStockQuantity(25);
        refactoring.setCategory(technology);
        refactoring.setPublisher(oreilly);
        refactoring.setAuthors(Set.of(martinFowler));
        bookRepository.save(refactoring);

        Book nineteenEightyFour = new Book();
        nineteenEightyFour.setTitle("1984");
        nineteenEightyFour.setIsbn("978-0451524935");
        nineteenEightyFour.setDescription("A dystopian social science fiction novel");
        nineteenEightyFour.setPublicationDate(LocalDate.of(1949, 6, 8));
        nineteenEightyFour.setPrice(new BigDecimal("13.99"));
        nineteenEightyFour.setPageCount(328);
        nineteenEightyFour.setStatus(Book.BookStatus.AVAILABLE);
        nineteenEightyFour.setStockQuantity(30);
        nineteenEightyFour.setCategory(fiction);
        nineteenEightyFour.setPublisher(penguinRandom);
        nineteenEightyFour.setAuthors(Set.of(georgeOrwell));
        bookRepository.save(nineteenEightyFour);

        System.out.println("Sample data initialized successfully!");
        System.out.println("Created " + categoryRepository.count() + " categories");
        System.out.println("Created " + publisherRepository.count() + " publishers");
        System.out.println("Created " + authorRepository.count() + " authors");
        System.out.println("Created " + bookRepository.count() + " books");
    }
}

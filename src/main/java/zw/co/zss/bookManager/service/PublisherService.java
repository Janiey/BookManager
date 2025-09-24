package zw.co.zss.bookManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.co.zss.bookManager.model.Publisher;
import zw.co.zss.bookManager.repository.PublisherRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Page<Publisher> getAllPublishers(Pageable pageable) {
        return publisherRepository.findAll(pageable);
    }

    public Optional<Publisher> getPublisherById(Long id) {
        return publisherRepository.findById(id);
    }

    public Optional<Publisher> getPublisherByName(String name) {
        return publisherRepository.findByNameIgnoreCase(name);
    }

    public Optional<Publisher> getPublisherByEmail(String email) {
        return publisherRepository.findByEmail(email);
    }

    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Publisher updatePublisher(Long id, Publisher publisherDetails) {
        return publisherRepository.findById(id)
                .map(publisher -> {
                    publisher.setName(publisherDetails.getName());
                    publisher.setAddress(publisherDetails.getAddress());
                    publisher.setCity(publisherDetails.getCity());
                    publisher.setCountry(publisherDetails.getCountry());
                    publisher.setPhone(publisherDetails.getPhone());
                    publisher.setEmail(publisherDetails.getEmail());
                    publisher.setWebsite(publisherDetails.getWebsite());
                    publisher.setDescription(publisherDetails.getDescription());
                    return publisherRepository.save(publisher);
                })
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + id));
    }

    public void deletePublisher(Long id) {
        if (!publisherRepository.existsById(id)) {
            throw new RuntimeException("Publisher not found with id: " + id);
        }
        publisherRepository.deleteById(id);
    }

    public List<Publisher> searchPublishersByName(String name) {
        return publisherRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Publisher> searchPublishers(String searchTerm) {
        return publisherRepository.searchPublishers(searchTerm);
    }

    public List<Publisher> getPublishersByCity(String city) {
        return publisherRepository.findByCityIgnoreCase(city);
    }

    public List<Publisher> getPublishersByCountry(String country) {
        return publisherRepository.findByCountryIgnoreCase(country);
    }

    public List<Publisher> getPublishersWithBooks() {
        return publisherRepository.findPublishersWithBooks();
    }

    public List<Publisher> getPublishersWithoutBooks() {
        return publisherRepository.findPublishersWithoutBooks();
    }

    public Long countBooksByPublisher(Long publisherId) {
        return publisherRepository.countBooksByPublisher(publisherId);
    }

    public List<Object[]> getPublisherStatistics() {
        return publisherRepository.getPublisherStatistics();
    }

    public boolean existsByName(String name) {
        return publisherRepository.findByNameIgnoreCase(name).isPresent();
    }

    public boolean existsByEmail(String email) {
        return publisherRepository.findByEmail(email).isPresent();
    }
}

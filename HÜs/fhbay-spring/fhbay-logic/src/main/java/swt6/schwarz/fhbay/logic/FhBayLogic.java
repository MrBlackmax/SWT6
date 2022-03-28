package swt6.schwarz.fhbay.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.schwarz.fhbay.dao.domain.Article;
import swt6.schwarz.fhbay.dao.domain.Bid;
import swt6.schwarz.fhbay.dao.domain.Customer;
import swt6.schwarz.fhbay.dao.repositories.ArticleRepository;
import swt6.schwarz.fhbay.dao.repositories.BidRepository;
import swt6.schwarz.fhbay.dao.repositories.CustomerRepository;
import swt6.schwarz.fhbay.exceptions.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service("fhbay")
@Transactional
public class FhBayLogic implements Logic {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private BidRepository bidRepository;

    @Override
    public Customer updateOrCreateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Article updateOrCreateArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Optional<Article> findArticleById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Article setBuyer(Long articleId, Customer buyer) {
        var foundArticle = articleRepository.findById(articleId);
        if (foundArticle.isPresent()) {
            var article = foundArticle.get();
            article.setBuyer(buyer);
            return articleRepository.save(article);
        } else {
            throw new EntityNotFoundException(articleId, Article.class);
        }
    }

    @Override
    public Article setSeller(Long articleId, Customer seller) {
        var foundArticle = articleRepository.findById(articleId);
        if (foundArticle.isPresent()) {
            var article = foundArticle.get();
            article.setSeller(seller);
            return articleRepository.save(article);
        } else {
            throw new EntityNotFoundException(articleId, Article.class);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Article> findByTerm(String searchTerm) {
        return articleRepository.findByTerm(searchTerm);
    }

    @Override
    public Bid updateOrCreateBid(Bid bid) {
        return bidRepository.save(bid);
    }

    @Override
    public Bid addBid(Long customerId, Long articleId, double price) {
        var article = findArticleById(articleId);
        if (article.get().getState() != 1) throw new RuntimeException("Can't add bid for article because its not for auction");
        var customer = findCustomerById(customerId);
        if (article.isPresent() && customer.isPresent()) {
            var bid = new Bid(article.get(), customer.get(), price, LocalDateTime.now());
            return bidRepository.save(bid);
        } else {
            throw new EntityNotFoundException(articleId, Article.class);
        }
    }

    @Override
    public List<Bid> getBidsForArticle(Long articleId) {
        var article = findArticleById(articleId);
        if (article.isPresent()) {
            return bidRepository.getAllByArticle(article.get());
        } else {
            throw new EntityNotFoundException(articleId, Article.class);
        }
    }


}
